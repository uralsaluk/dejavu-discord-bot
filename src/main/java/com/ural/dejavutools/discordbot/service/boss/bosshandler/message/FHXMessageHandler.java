package com.ural.dejavutools.discordbot.service.boss.bosshandler.message;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.data.message.MessageLogDao;
import com.ural.dejavutools.discordbot.data.message.document.FHXDiscordMessage;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.BossHandler;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.constant.FHXBossAlertConstants;
import com.ural.dejavutools.discordbot.service.boss.constant.FHXBossAlertType;
import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FHXMessageHandler implements MessageHandler {


  private final MessageLogDao messageLogDao;

  private Map<String, BossHandler> optionHandlerStringMap;

  private JDA jda;

  private DejavuBossConfiguration dejavuBossConfiguration;

  public FHXMessageHandler(MessageLogDao messageLogDao,
      Map<String, BossHandler> optionHandlerStringMap, @Qualifier(value = "DejaBoss") JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration) {
    this.messageLogDao = messageLogDao;
    this.optionHandlerStringMap = optionHandlerStringMap;
    this.jda = jda;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
  }

  @Override
  public void handleMessage(MessageReceivedEvent event) {

    FHXDiscordMessage fhxDiscordMessage = new FHXDiscordMessage();
    fhxDiscordMessage.setUserId(event.getAuthor().getId());
    fhxDiscordMessage.setUserName(event.getAuthor().getName());
    fhxDiscordMessage.setNickName(event.getMember().getNickname());
    fhxDiscordMessage.setGuildId(event.getGuild().getId());
    fhxDiscordMessage.setGuildName(event.getGuild().getName());
    fhxDiscordMessage.setChannelId(event.getChannel().getId());
    fhxDiscordMessage.setChannelName(event.getChannel().getName());
    fhxDiscordMessage.setMessage(event.getMessage().getContentDisplay());
    fhxDiscordMessage.setMessageRaw(event.getMessage().getContentRaw());

    messageLogDao.save(fhxDiscordMessage);

  }

  @Override
  public void bossLog(MessageReceivedEvent event) {

    FHXBossAlertType alertTypeByMessage = getAlertTypeByMessage(event.getMessage());
    BossType bossType = getBossTypeByMessage(event.getMessage());

    if (alertTypeByMessage == null || bossType == null) {

      return;
    }

    switch (alertTypeByMessage) {

      case BEFORE_5_MIN -> notifyAnnouncement(bossType, alertTypeByMessage);

      case DIED, SPAWNED -> {

        BossHandler bossHandler = optionHandlerStringMap.get(bossType.toString());

        bossHandler.messageEventHandler(event, alertTypeByMessage);
        notifyAnnouncement(bossType, alertTypeByMessage);


      }


    }


  }

  @Override
  public void gmLog(MessageReceivedEvent event) {

  }

  private void notifyAnnouncement(BossType bossType, FHXBossAlertType alertTypeByMessage) {

    if (alertTypeByMessage.equals(FHXBossAlertType.BEFORE_5_MIN)) {

      StringBuilder fiveMinMessageBuilder = new StringBuilder();
      fiveMinMessageBuilder.append(
          DiscordMessageUtils.getRoleAnnotationText(
              dejavuBossConfiguration.getNotificationRoleId()));
      fiveMinMessageBuilder.append(" " + bossType.toString());
      fiveMinMessageBuilder.append(" 5 min");

      TextChannel textChannelById = jda.getTextChannelById(
          dejavuBossConfiguration.getBossChannelId());
      textChannelById.sendMessage(fiveMinMessageBuilder.toString()).complete();
    }

    if (alertTypeByMessage.equals(FHXBossAlertType.SPAWNED)) {

      StringBuilder fiveMinMessageBuilder = new StringBuilder();
      fiveMinMessageBuilder.append(bossType.toString());
      fiveMinMessageBuilder.append(" spawned ");

      TextChannel textChannelById = jda.getTextChannelById(
          dejavuBossConfiguration.getBossChannelId());
      textChannelById.sendMessage(fiveMinMessageBuilder.toString()).complete();
    }


  }

  private BossType getBossTypeByMessage(Message message) {

    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(), FHXBossAlertConstants.CULT_DIED,
        FHXBossAlertConstants.CULT_FIVE_MINUTES, FHXBossAlertConstants.CULT_SPAWNED)) {
      return BossType.CULT;
    }
    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(), FHXBossAlertConstants.LABORC_DIED,
        FHXBossAlertConstants.LABORC_FIVE_MINUTES, FHXBossAlertConstants.LABORC_SPAWNED)) {
      return BossType.LABORC;
    }
    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(), FHXBossAlertConstants.GHOUL_DIED,
        FHXBossAlertConstants.GHOUL_FIVE_MINUTES, FHXBossAlertConstants.GHOUL_SPAWNED)) {
      return BossType.GHOUL;
    }
    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(),
        FHXBossAlertConstants.OBSIDIAN_DIED,
        FHXBossAlertConstants.OBSIDIAN_FIVE_MINUTES, FHXBossAlertConstants.OBSIDIAN_SPAWNED)) {
      return BossType.OBSIDIAN;
    }

    return null;
  }

  private FHXBossAlertType getAlertTypeByMessage(Message message) {

    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(),
        FHXBossAlertConstants.LABORC_DIED, FHXBossAlertConstants.CULT_DIED,
        FHXBossAlertConstants.GHOUL_DIED, FHXBossAlertConstants.OBSIDIAN_DIED)) {

      return FHXBossAlertType.DIED;
    }

    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(),
        FHXBossAlertConstants.LABORC_FIVE_MINUTES, FHXBossAlertConstants.CULT_FIVE_MINUTES,
        FHXBossAlertConstants.GHOUL_FIVE_MINUTES, FHXBossAlertConstants.OBSIDIAN_FIVE_MINUTES)) {

      return FHXBossAlertType.BEFORE_5_MIN;
    }

    if (StringUtils.equalsAnyIgnoreCase(message.getContentRaw(),
        FHXBossAlertConstants.LABORC_SPAWNED, FHXBossAlertConstants.CULT_SPAWNED,
        FHXBossAlertConstants.GHOUL_SPAWNED, FHXBossAlertConstants.OBSIDIAN_SPAWNED)) {

      return FHXBossAlertType.SPAWNED;
    }

    return null;

  }

}
