package com.ural.dejavutools.discordbot.data.boss.impl;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class BossLogDaoImpl implements BossLogDao {

  private MongoTemplate mongoTemplate;


  @Autowired
  public BossLogDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void saveBossRecord(BossDocument t) {

    mongoTemplate.save(t);

  }

  @Override
  public List<BossDocument> getActiveRecord() {
    Query query = new Query();
    query.addCriteria(Criteria.where("isNotificationEnable").is(true));

    return mongoTemplate.find(query, BossDocument.class);
  }

  @Override
  public List<BossDocument> getByFilter(Query query) {

    return mongoTemplate.find(query, BossDocument.class);
  }

  @Override
  public BossDocument findOneByFilter(Query query) {
    return mongoTemplate.findOne(query, BossDocument.class);
  }

  @Override
  public void disableNotificationsByQuery(Query query) {
    Update update = new Update();
    update.set("isNotificationEnable", false);
    update.set("updatedTime", OffsetDateTime.now().toString());
    mongoTemplate.updateMulti(query, update, BossDocument.class);

  }
}
