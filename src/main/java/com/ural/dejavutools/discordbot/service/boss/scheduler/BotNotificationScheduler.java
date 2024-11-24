package com.ural.dejavutools.discordbot.service.boss.scheduler;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.BossHandler;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.GlobalBossService;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BotNotificationScheduler {

  private JDA jda;
  private final BossLogDao bossLogDao;

  private Map<String, BossHandler> optionHandlerStringMap;

  private final GlobalBossService globalBossService;


  @Autowired
  public BotNotificationScheduler(@Qualifier(value = "DejaBoss") JDA jda, BossLogDao bossLogDao,
      Map<String, BossHandler> optionHandlerStringMap, GlobalBossService globalBossService) {
    this.jda = jda;
    this.bossLogDao = bossLogDao;
    this.optionHandlerStringMap = optionHandlerStringMap;
    this.globalBossService = globalBossService;
  }

  @Scheduled(initialDelay = 10000, fixedDelay = 30000)
  public void bossCheck() {

    List<BossDocument> activeRecord = bossLogDao.getActiveRecord();

    activeRecord.forEach(
        record -> optionHandlerStringMap.get(record.getBossType().toString())
            .handleScheduler(record));


  }

  @Scheduled(cron = "0 0 * * * ?")
  public void notifyGuildForAllBosses() {
    globalBossService.notifyForAllBosses();
  }

}
