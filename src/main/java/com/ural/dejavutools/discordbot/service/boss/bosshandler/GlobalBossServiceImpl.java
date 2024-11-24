package com.ural.dejavutools.discordbot.service.boss.bosshandler;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.constant.BossMessages;
import com.ural.dejavutools.discordbot.service.util.DiscordTİmeUtil;
import com.ural.dejavutools.discordbot.service.util.TimeRangeUtils;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GlobalBossServiceImpl implements GlobalBossService {

  private final BossLogDao bossLogDao;
  private final JDA jda;
  private DejavuBossConfiguration dejavuBossConfiguration;

  public GlobalBossServiceImpl(BossLogDao bossLogDao, @Qualifier(value = "DejaBoss") JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration) {
    this.bossLogDao = bossLogDao;
    this.jda = jda;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
  }

  @Override
  public void notifyForAllBosses() {

    List<BossDocument> activeRecords = bossLogDao.getActiveRecord();

    String notificationMessage = getGlobalBossNotificationMessage(activeRecords);
    TextChannel textChannelById = jda.getTextChannelById(
        dejavuBossConfiguration.getBossChannelId());
    Message sentMessage = textChannelById.sendMessage(
        BossMessages.updateRoleTag(notificationMessage,
            dejavuBossConfiguration.getNotificationRoleId())).complete();

    sentMessage.delete().queueAfter(1, TimeUnit.HOURS);


  }


  private String getGlobalBossNotificationMessage(List<BossDocument> activeRecord) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("**Notification about all bosses** \n");

    activeRecord.forEach(x -> {
      stringBuilder.append(
          "\n----------------------------------------------------------------------------\n");

      StringBuilder lineBuilder = new StringBuilder();
      lineBuilder.append("**").append(x.getBossType().toString()).append("**  -> ");
      if (TimeRangeUtils.checkNowIsBetween(
          OffsetDateTime.parse(x.getDeadTime()).plusSeconds(x.getWindowStartAtSeconds()),
          OffsetDateTime.parse(x.getDeadTime()).plusSeconds(x.getWindowEndAtSeconds()))) {
        lineBuilder.append("**IN WINDOW TIME**");
      } else {
        lineBuilder.append("not in window time");
      }
      lineBuilder.append("----");
      lineBuilder.append(
          " about ${deadTime} from now and can spawn until latest ${windowTime} from now!");

      stringBuilder.append(DiscordTİmeUtil.getRelativeTimesOnMessage(lineBuilder.toString(),
          OffsetDateTime.parse(x.getDeadTime()), x.getWindowEndAtSeconds(),
          x.getWindowStartAtSeconds()));

    });

    return stringBuilder.toString();
  }


}
