package com.ural.dejavutools.discordbot.data.message.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.message.MessageLogDao;
import com.ural.dejavutools.discordbot.data.message.document.FHXDiscordMessage;
import java.time.OffsetDateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageLogDaoImpl implements MessageLogDao {


  private final MongoTemplate mongoTemplate;


  public MessageLogDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void save(FHXDiscordMessage document) {

    document.setLogTime(OffsetDateTime.now().toString());
    mongoTemplate.save(document, CollectionConstants.FHX_MESSAGES);

  }
}
