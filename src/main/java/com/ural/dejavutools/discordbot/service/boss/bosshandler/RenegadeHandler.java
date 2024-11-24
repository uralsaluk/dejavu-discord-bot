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

@Component("RENEGADE")
public class RenegadeHandler extends BaseBossHandler {


  public RenegadeHandler(BossLogDao bossLogDao, @Qualifier(value = "DejaBoss") JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration) {
    
    super(bossLogDao, jda, dejavuBossConfiguration, BossType.RENEGADE, 90000L);
  }

  @Override
  public String getInfoMessage(BossDocument bossDocument) {
    String relativeTimesOnMessage = DiscordTİmeUtil.getRelativeTimesOnMessage(
        BossMessages.RENEGADE_INFO_NTF_MESSAGE, OffsetDateTime.parse(bossDocument.getDeadTime()),
        bossDocument.getWindowEndAtSeconds(), bossDocument.getWindowStartAtSeconds());

    return relativeTimesOnMessage;

  }

  @Override
  public void setPeriods(BossDocument bossDocument) {
    /**
     * ,
     *             new AlertPeriod(1440, true, BossMessages.RENEGADE_FIRST_NTF_MESSAGE),
     *             new AlertPeriod(1800, true, BossMessages.RENEGADE_LAST_NTF_MESSAGE)
     * */

    bossDocument.setAlertPeriods(
        Arrays.asList(new AlertPeriod(super.windowStartAtSeconds, true,
            BossMessages.RENEGADE_FIRST_NTF_MESSAGE)));


  }
}
