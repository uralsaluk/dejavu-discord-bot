package com.ural.dejavutools.discordbot.service.multiclient.scheduler;

import com.ural.dejavutools.discordbot.data.multiclient.ActiveAccountDao;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActiveAccountScheduler {


  private ActiveAccountDao activeAccountDao;

  public ActiveAccountScheduler(ActiveAccountDao activeAccountDao) {
    this.activeAccountDao = activeAccountDao;
  }

  @Scheduled(initialDelay = 20000, fixedRate = 30000)
  public void clearOldRecords() {

    Query query = new Query();
    query.addCriteria(Criteria.where("lastUpdate").lt(LocalDateTime.now().minusMinutes(3)));
    activeAccountDao.delete(query);


  }

}
