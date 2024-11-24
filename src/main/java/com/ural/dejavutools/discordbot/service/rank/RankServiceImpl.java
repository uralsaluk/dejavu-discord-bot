package com.ural.dejavutools.discordbot.service.rank;

import com.ural.dejavutools.discordbot.data.rank.RankDao;
import com.ural.dejavutools.discordbot.data.rank.document.RankDocument;
import com.ural.dejavutools.discordbot.service.rank.configuration.DejavuRankConfiguration;
import com.ural.dejavutools.discordbot.service.rank.constant.FHXClass;
import com.ural.dejavutools.discordbot.service.rank.constant.RankMessageConstant;
import com.ural.dejavutools.discordbot.service.rank.constant.RankType;
import com.ural.dejavutools.discordbot.service.rank.mapper.RankServiceMapper;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterGrindRank;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterInfo;
import com.ural.dejavutools.discordbot.service.rank.restclient.FHXRankClient;
import com.ural.dejavutools.discordbot.service.rank.restclient.model.FHXRankClientRequestDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RankServiceImpl implements RankService {


  private static final RankServiceMapper MAPPER = RankServiceMapper.MAPPER;
  private final FHXRankClient fhxRankClient;

  private final RankDao rankDao;

  private final JDA jda;

  private final DejavuRankConfiguration dejavuRankConfiguration;

  public RankServiceImpl(FHXRankClient fhxRankClient, RankDao rankDao,
      @Qualifier(value = "DejaRank") JDA jda, DejavuRankConfiguration dejavuRankConfiguration) {
    this.fhxRankClient = fhxRankClient;
    this.rankDao = rankDao;
    this.jda = jda;
    this.dejavuRankConfiguration = dejavuRankConfiguration;
  }

  @Override
  public void publishRank(RankType rankType) {

    List<CharacterInfo> rankList = new ArrayList<>();

    Arrays.stream(FHXClass.values()).toList().forEach(fhxClass -> {
      rankList.addAll(
          fhxRankClient.getRank(new FHXRankClientRequestDTO(fhxClass.toString())).getData());
    });
    if (!CollectionUtils.isEmpty(rankList)) {

      List<CharacterInfo> characterInfoList = rankList.stream().sorted().toList();

      List<RankDocument> rankDocuments = MAPPER.toRankDocumentList(characterInfoList);

      rankDao.saveRankHistory(rankDocuments);

      List<RankDocument> previousDayRecords =
          rankType == RankType.DAILY ? rankDao.findPreviousRecordsByDay(1)
              : rankDao.findPreviousRecordsByDay(7);

      if (!CollectionUtils.isEmpty(rankDocuments) && !CollectionUtils.isEmpty(previousDayRecords)) {

        List<CharacterGrindRank> grindRank = getGrindRank(previousDayRecords, rankDocuments);

        if (!CollectionUtils.isEmpty(grindRank)) {

          handleGrindRank(grindRank, rankType);


        }

      }
    }
  }

  @Override
  public List<CharacterGrindRank> getDailyRank() {

    List<RankDocument> dailyRank = rankDao.getDailyRank();
    List<CharacterGrindRank> characterGrindRankList = MAPPER.toCharacterGrindRankList(dailyRank);
    Collections.sort(characterGrindRankList);
    return characterGrindRankList;
  }

  @Override
  public List<CharacterGrindRank> getWeeklyRank() {
    List<RankDocument> weeklyRank = rankDao.getWeeklyRank();
    List<CharacterGrindRank> characterGrindRankList = MAPPER.toCharacterGrindRankList(weeklyRank);
    Collections.sort(characterGrindRankList);
    return characterGrindRankList;
  }

  @Override
  public void notifyChannel() {

    TextChannel textChannelById = jda.getTextChannelById(
        dejavuRankConfiguration.getChannelId());
    textChannelById.sendMessage(RankMessageConstant.RANK_NOTIFY_MESSAGE).queue();

  }

  public void handleGrindRank(List<CharacterGrindRank> grindRank, RankType rankType) {

    //String rankText = getRankText(grindRank, rankType);

    if (rankType == RankType.DAILY) {
      rankDao.saveDailyRank(
          MAPPER.grindRankToRankDocumentList(grindRank));

    } else {
      rankDao.saveWeeklyRank(
          MAPPER.grindRankToRankDocumentList(grindRank));
    }
  }


  private List<CharacterGrindRank> getGrindRank(List<RankDocument> oldDocuments,
      List<RankDocument> rankDocuments) {

    Map<String, CharacterInfo> oldRankMap = MAPPER.toCharacterInfoList(oldDocuments).stream()
        .collect(
            Collectors.toMap(CharacterInfo::getName, characterInfo -> characterInfo, (o, n) -> n));
    Map<String, CharacterInfo> newRank = MAPPER.toCharacterInfoList(rankDocuments).stream().collect(
        Collectors.toMap(CharacterInfo::getName, characterInfo -> characterInfo, (o, n) -> n));

    List<CharacterGrindRank> characterGrindRankList = new ArrayList<>();

    newRank.forEach((s, characterInfo) -> {
      if (oldRankMap.containsKey(s)) {
        CharacterGrindRank characterGrindRank = new CharacterGrindRank();
        characterGrindRank.setName(s);
        characterGrindRank.setLevel(characterInfo.getLevel());
        characterGrindRank.setClassName(characterInfo.getClassName());
        characterGrindRank.setGuildName(characterInfo.getGuildName());
        characterGrindRank.setExperience(
            Math.abs(characterInfo.getExperience() - oldRankMap.get(s).getExperience()));
        characterGrindRankList.add(characterGrindRank);

      }

    });

    return characterGrindRankList.stream().sorted().collect(Collectors.toList())
        .subList(0, Math.min(characterGrindRankList.size(), dejavuRankConfiguration.getRankSize()));

  }

  private String getRankText(List<CharacterGrindRank> grindRank, RankType rankType) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(rankType.toString() + " Rank");
    stringBuilder.append(
        String.format("| %-15s | %-15s | %-15s | %-15s |\n", "Nick ", "Experience", "Class",
            "Guild"));

    grindRank.forEach(x -> {

      stringBuilder.append(
          String.format("| %-15s | %-15s | %-15s | %-15s |\n", x.getName(),
              x.getExperience(),
              x.getClassName(), x.getGuildName()));

    });

    return stringBuilder.toString();

  }
}
