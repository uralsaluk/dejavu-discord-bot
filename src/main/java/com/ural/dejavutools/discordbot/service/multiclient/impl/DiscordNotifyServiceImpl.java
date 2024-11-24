package com.ural.dejavutools.discordbot.service.multiclient.impl;


import com.ural.dejavutools.discordbot.data.multiclient.DiscordMessagesLogDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.DiscordMessagesLogDocument;
import com.ural.dejavutools.discordbot.service.multiclient.DiscordNotifyService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DiscordNotifyServiceImpl implements DiscordNotifyService {

  private JDA jda;

  private DiscordMessagesLogDao discordMessagesLogDao;

  public DiscordNotifyServiceImpl(@Qualifier(value = "DejaMultiClient") JDA jda,
      DiscordMessagesLogDao discordMessagesLogDao) {
    this.jda = jda;
    this.discordMessagesLogDao = discordMessagesLogDao;
  }

  @Override
  public <T> void sendMessage(String transactionId, String userId, String message, T request) {
    User userById = jda.getUserById(userId);

    userById.openPrivateChannel().complete().sendMessage(message).complete();

    DiscordMessagesLogDocument document = new DiscordMessagesLogDocument(transactionId, userId,
        userById.getName(),
        message, request);
    discordMessagesLogDao.logMessages(document);
  }
}
