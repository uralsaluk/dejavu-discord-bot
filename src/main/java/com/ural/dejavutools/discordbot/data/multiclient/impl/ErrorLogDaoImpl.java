package com.ural.dejavutools.discordbot.data.multiclient.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.multiclient.ErrorLogDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.ErrorLogDocument;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ErrorLogDaoImpl implements ErrorLogDao {

  private MongoTemplate mongoTemplate;

  public ErrorLogDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public ErrorLogDocument saveErrorLog(ErrorLogDocument document) {
    document.setLogDate(LocalDateTime.now());

    return mongoTemplate.save(document, CollectionConstants.ERROR_LOG);
  }
}
