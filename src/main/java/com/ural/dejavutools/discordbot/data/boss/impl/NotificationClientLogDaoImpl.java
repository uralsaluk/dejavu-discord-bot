package com.ural.dejavutools.discordbot.data.boss.impl;


import com.ural.dejavutools.discordbot.data.boss.NotificationClientLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationClientLogDocument;
import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationClientLogDaoImpl implements NotificationClientLogDao {

  private MongoTemplate mongoTemplate;

  public NotificationClientLogDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void save(NotificationClientLogDocument document) {

    mongoTemplate.save(document, CollectionConstants.NOTIFICATION_CLIENT_LOG);

  }

  @Override
  public Boolean isExistInSecondsByBossType(Integer secs, BossType bossType) {
    Query query = new Query();
    query.addCriteria(Criteria.where("bossType").is(bossType)
        .and("notificationTime").gte(LocalDateTime.now().minusSeconds(secs)));

    return mongoTemplate.exists(query,
        CollectionConstants.NOTIFICATION_CLIENT_LOG);
  }

  @Override
  public Long findTotalRecordsInSecondsByDiscordUserId(Integer secs, String discordUserId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("discordUserId").is(discordUserId)
        .and("notificationTime").gte(LocalDateTime.now().minusSeconds(secs)));

    return mongoTemplate.count(query,
        CollectionConstants.NOTIFICATION_CLIENT_LOG);
  }

  @Override
  public Long findTotalRecordsInSecondsByKey(Integer secs, String key) {
    Query query = new Query();
    query.addCriteria(Criteria.where("key").is(key)
        .and("notificationTime").gte(LocalDateTime.now().minusSeconds(secs)));

    return mongoTemplate.count(query,
        CollectionConstants.NOTIFICATION_CLIENT_LOG);
  }
}
