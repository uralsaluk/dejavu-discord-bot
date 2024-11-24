package com.ural.dejavutools.discordbot.data.multiclient.impl;


import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.multiclient.DiscordMessagesLogDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.DiscordMessagesLogDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DiscordMessagesLogDaoImpl implements DiscordMessagesLogDao {

  private MongoTemplate mongoTemplate;

  public DiscordMessagesLogDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void logMessages(DiscordMessagesLogDocument document) {

    mongoTemplate.save(document, CollectionConstants.DISCORD_MESSAGES_LOG);
  }
}
