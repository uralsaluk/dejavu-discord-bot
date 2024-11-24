package com.ural.dejavutools.discordbot.service.rank.scheduler;

import com.ural.dejavutools.discordbot.service.rank.RankServiceImpl;
import com.ural.dejavutools.discordbot.service.rank.constant.RankType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RankScheduler {


  private RankServiceImpl rankService;

  public RankScheduler(RankServiceImpl rankService) {
    this.rankService = rankService;
  }

  @Scheduled(cron = "0 0 3 * * ?")
  public void dailyRank() {

    rankService.publishRank(RankType.DAILY);

  }

  @Scheduled(cron = "0 05 3 * * ?")
  public void weeklyRank() {

    rankService.publishRank(RankType.WEEKLY);

  }

  @Scheduled(cron = "0 10 3 * * ?")
  public void notifyDiscord() {

    rankService.notifyChannel();

  }

}
