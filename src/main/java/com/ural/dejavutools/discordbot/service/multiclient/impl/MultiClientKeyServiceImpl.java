package com.ural.dejavutools.discordbot.service.multiclient.impl;

import com.ural.dejavutools.discordbot.data.multiclient.MultiClientKeyDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.IpContextDocument;
import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientKeyService;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientKeyUtil;
import com.ural.dejavutools.discordbot.service.multiclient.configuration.DejavuMultiClientConfiguration;
import com.ural.dejavutools.discordbot.service.multiclient.constant.DiscordErrorMessages;
import com.ural.dejavutools.discordbot.service.multiclient.constant.ErrorMessages;
import com.ural.dejavutools.discordbot.service.multiclient.exception.MultiClientCheckKeyException;
import com.ural.dejavutools.discordbot.service.multiclient.mapper.MultiClientServiceMapper;
import com.ural.dejavutools.discordbot.service.multiclient.model.MultiClientKey;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.util.TimeRangeUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MultiClientKeyServiceImpl implements MultiClientKeyService {

  private JDA jda;

  private MultiClientKeyDao multiClientKeyDao;

  private DejavuMultiClientConfiguration dejavuMultiClientConfiguration;
  private static final MultiClientServiceMapper MAPPER = MultiClientServiceMapper.MAPPER;

  public MultiClientKeyServiceImpl(@Qualifier(value = "DejaMultiClient") JDA jda,
      MultiClientKeyDao multiClientKeyDao,
      DejavuMultiClientConfiguration dejavuMultiClientConfiguration) {
    this.jda = jda;
    this.multiClientKeyDao = multiClientKeyDao;
    this.dejavuMultiClientConfiguration = dejavuMultiClientConfiguration;
  }

  @Override
  public void getMultiClientKey(SlashCommandInteractionEvent event) {

    if (!isUserDejavuMember(event.getUser().getId())) {

      event.reply("You are not Dejavu member.").setEphemeral(true).queue();
      return;
    }

    MultiClientKeyDocument keyByUserId = multiClientKeyDao.findKeyByUserId(event.getUser().getId());

    if (!Objects.isNull(keyByUserId)) {

      if (isUserBlocked(keyByUserId)) {

        event.reply("You are blocked").setEphemeral(true).queue();
        return;
      }

      event.reply(MultiClientKeyUtil.keyEventResponseBuilder(keyByUserId)).setEphemeral(true)
          .queue();

      return;
    } else {

      MultiClientKey multiClientKey = MultiClientKeyUtil.keyBuilder(event);

      MultiClientKeyDocument multiClientKeyDocument = MAPPER.toMultiClientKeyDocument(
          multiClientKey);

      MultiClientKeyDocument savedDocument = multiClientKeyDao.saveMultiClientKey(
          multiClientKeyDocument);

      event.reply(MultiClientKeyUtil.keyEventResponseBuilder(savedDocument)).setEphemeral(true)
          .queue();


    }


  }

  @Override
  public CheckKeyResponseDTO checkKey(CheckKeyRequestDTO checkKeyRequestDTO) {

    MultiClientKeyDocument keyByKeyId = multiClientKeyDao.findKeyByKeyId(
        checkKeyRequestDTO.getMultiClientKey());

    if (Objects.isNull(keyByKeyId)) {

      throw new MultiClientCheckKeyException(ErrorMessages.NO_KEY_FOUND, checkKeyRequestDTO);
    }

    //TODO check game version on client side and pass here true or not
    checkGameVersion(keyByKeyId, checkKeyRequestDTO);

    checkUserIsDejavu(keyByKeyId, checkKeyRequestDTO);

    checkUserBlocked(keyByKeyId, checkKeyRequestDTO);

    checkIpLimit(keyByKeyId, checkKeyRequestDTO);

    checkMultiClientVersion(keyByKeyId, checkKeyRequestDTO);

    if (!CollectionUtils.isEmpty(keyByKeyId.getIps()) && keyByKeyId.getIps().stream()
        .filter(x -> StringUtils.equalsIgnoreCase(x.getIp(), checkKeyRequestDTO.getUserIp()))
        .findFirst().isPresent()) {
      IpContextDocument ipContextDocument = keyByKeyId.getIps().stream()
          .filter(x -> StringUtils.equalsIgnoreCase(x.getIp(), checkKeyRequestDTO.getUserIp()))
          .findFirst().get();
      ipContextDocument.setLastTimeUsing(LocalDateTime.now());
    } else {
      keyByKeyId.getIps()
          .add(new IpContextDocument(checkKeyRequestDTO.getUserIp(), LocalDateTime.now(), null));
    }

    multiClientKeyDao.saveMultiClientKey(keyByKeyId);
    return new CheckKeyResponseDTO(true);


  }


  private Boolean isUserDejavuMember(String userId) {
    Guild dejaGuild = jda.getGuildById(dejavuMultiClientConfiguration.getAllowedGuildId());
    Role roleById = dejaGuild.getRoleById(dejavuMultiClientConfiguration.getAllowedRoleId());
    List<Member> membersWithRoles = dejaGuild.getMembersWithRoles(roleById);

    return membersWithRoles.stream()
        .anyMatch(x -> StringUtils.equalsIgnoreCase(x.getUser().getId(), userId));
  }

  private Boolean isUserBlocked(MultiClientKeyDocument document) {

    return document.getIsBlocked();

  }


  private void checkGameVersion(MultiClientKeyDocument keyByKeyId,
      CheckKeyRequestDTO checkKeyRequestDTO) {

    if (!checkKeyRequestDTO.getIsGameVersionValid()) {

      throw new MultiClientCheckKeyException(ErrorMessages.WRONG_GAME_CLIENT_VERSION,
          keyByKeyId.getDiscordUserId(), DiscordErrorMessages.WRONG_GAME_CLIENT_VERSION,
          checkKeyRequestDTO);

    }


  }

  private void checkUserIsDejavu(MultiClientKeyDocument keyByKeyId,
      CheckKeyRequestDTO checkKeyRequestDTO) {
    if (!isUserDejavuMember(keyByKeyId.getDiscordUserId())) {

      throw new MultiClientCheckKeyException(ErrorMessages.USER_NOT_DEJAVU_MEMBER,
          keyByKeyId.getDiscordUserId(), DiscordErrorMessages.USER_NOT_DEJAVU_MEMBER,
          checkKeyRequestDTO);
    }

  }

  private void checkUserBlocked(MultiClientKeyDocument keyByKeyId,
      CheckKeyRequestDTO checkKeyRequestDTO) {
    if (keyByKeyId.getIsBlocked()) {
      throw new MultiClientCheckKeyException(ErrorMessages.USER_BLOCKED,
          keyByKeyId.getDiscordUserId(), DiscordErrorMessages.USER_BLOCKED, checkKeyRequestDTO);
    }

  }

  private void checkIpLimit(MultiClientKeyDocument keyByKeyId,
      CheckKeyRequestDTO checkKeyRequestDTO) {

    if (!CollectionUtils.isEmpty(keyByKeyId.getIps())) {
      List<IpContextDocument> activeIps = getActiveIps(keyByKeyId.getIps());

      if (!CollectionUtils.isEmpty(keyByKeyId.getIps())
          && !activeIps.stream()
          .anyMatch(x -> StringUtils.equalsIgnoreCase(x.getIp(), checkKeyRequestDTO.getUserIp()))
          && activeIps.size() >= 5) {
        throw new MultiClientCheckKeyException(ErrorMessages.USER_IP_LIMIT,
            keyByKeyId.getDiscordUserId(), DiscordErrorMessages.USER_IP_LIMIT, checkKeyRequestDTO);

      }
    }
  }

  private void checkMultiClientVersion(MultiClientKeyDocument keyByKeyId,
      CheckKeyRequestDTO checkKeyRequestDTO) {

    if (!StringUtils.equalsIgnoreCase(checkKeyRequestDTO.getMultiClientVersion(),
        dejavuMultiClientConfiguration.getMultiClientVersion())) {
      throw new MultiClientCheckKeyException(ErrorMessages.WRONG_MULTI_CLIENT_VERSION,
          keyByKeyId.getDiscordUserId(), DiscordErrorMessages.WRONG_MULTI_CLIENT_VERSION,
          checkKeyRequestDTO);
    }
  }

  private List<IpContextDocument> getActiveIps(List<IpContextDocument> ipContextDocumentList) {

    return ipContextDocumentList.stream().filter(
        x -> TimeRangeUtils.checkTimeInHoursRange(x.getLastTimeUsing(),
            dejavuMultiClientConfiguration.getIpActiveTTLHours())).collect(Collectors.toList());

  }

}
