package com.ural.dejavutools.discordbot.data.boss.impl;


import com.ural.dejavutools.discordbot.data.boss.NotificationClientKeyDao;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationKeyDocument;
import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationClientKeyDaoImpl implements NotificationClientKeyDao {


  private MongoTemplate mongoTemplate;

  public NotificationClientKeyDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public NotificationKeyDocument save(NotificationKeyDocument document) {

    return mongoTemplate.save(document,
        CollectionConstants.NOTIFICATION_CLIENT_KEY);

  }

  @Override
  public NotificationKeyDocument findByKey(String key) {
    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("key").is(key));

    return mongoTemplate.findOne(query, NotificationKeyDocument.class,
        CollectionConstants.NOTIFICATION_CLIENT_KEY);
  }

  @Override
  public NotificationKeyDocument findKeyByUserId(String discordUserId) {
    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("discordUserId").is(discordUserId));

    return mongoTemplate.findOne(query, NotificationKeyDocument.class,
        CollectionConstants.NOTIFICATION_CLIENT_KEY);
  }
}
