package com.ural.dejavutools.discordbot.data.multiclient.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.multiclient.MultiClientKeyDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MultiClientKeyDaoImpl implements MultiClientKeyDao {


  private MongoTemplate mongoTemplate;


  public MultiClientKeyDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public MultiClientKeyDocument saveMultiClientKey(MultiClientKeyDocument document) {

    return mongoTemplate.save(document,
        CollectionConstants.MULTI_CLIENT_KEY);

  }

  @Override
  public MultiClientKeyDocument findKeyByUserId(String discordUserId) {

    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("discordUserId").is(discordUserId));

    return mongoTemplate.findOne(query, MultiClientKeyDocument.class,
        CollectionConstants.MULTI_CLIENT_KEY);

  }

  @Override
  public MultiClientKeyDocument findKeyByKeyId(String keyId) {
    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("key").is(keyId));

    return mongoTemplate.findOne(query, MultiClientKeyDocument.class,
        CollectionConstants.MULTI_CLIENT_KEY);
  }


}
