package com.ural.dejavutools.discordbot.service.boss.bosshandler;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.AlertPeriod;
import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.constant.BossMessages;
import com.ural.dejavutools.discordbot.service.util.DiscordTİmeUtil;
import java.time.OffsetDateTime;
import java.util.Arrays;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("GHOUL")
public class GhoulHandler extends BaseBossHandler {


  public GhoulHandler(BossLogDao bossLogDao, @Qualifier(value = "DejaBoss") JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration) {
    super(bossLogDao, jda, dejavuBossConfiguration, BossType.GHOUL, 86000L);
  }

  @Override
  public String getInfoMessage(BossDocument bossDocument) {
    String relativeTimesOnMessage = DiscordTİmeUtil.getRelativeTimesOnMessage(
        BossMessages.GHOUL_INFO_NTF_MESSAGE, OffsetDateTime.parse(bossDocument.getDeadTime()),
        bossDocument.getWindowEndAtSeconds(), bossDocument.getWindowStartAtSeconds());

    return relativeTimesOnMessage;

  }

  @Override
  public void setPeriods(BossDocument bossDocument) {

    bossDocument.setAlertPeriods(
        Arrays.asList(
            new AlertPeriod(super.windowStartAtSeconds, true, BossMessages.GHOUL_FIRST_NTF_MESSAGE)
        ));


  }
}
