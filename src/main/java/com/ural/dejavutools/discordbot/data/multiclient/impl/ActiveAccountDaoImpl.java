package com.ural.dejavutools.discordbot.data.multiclient.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.multiclient.ActiveAccountDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.ActiveAccountDocument;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveAccountDaoImpl implements ActiveAccountDao {


  private final MongoTemplate mongoTemplate;

  public ActiveAccountDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public ActiveAccountDocument findOneByFHXId(String fhxId) {

    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("fhxId").is(fhxId));

    return mongoTemplate.findOne(query, ActiveAccountDocument.class,
        CollectionConstants.ACTIVE_FHX_ACCOUNTS);
  }

  @Override
  public void save(ActiveAccountDocument document) {

    document.setLastUpdate(LocalDateTime.now());
    mongoTemplate.save(document, CollectionConstants.ACTIVE_FHX_ACCOUNTS);
  }

  @Override
  public void delete(String fhxId) {
    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("fhxId").is(fhxId));

    mongoTemplate.remove(query,
        CollectionConstants.ACTIVE_FHX_ACCOUNTS);

  }

  @Override
  public void delete(Query query) {
    mongoTemplate.remove(query,
        CollectionConstants.ACTIVE_FHX_ACCOUNTS);
  }
}
