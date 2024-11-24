package com.ural.dejavutools.discordbot.service.boss.util;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.data.boss.document.LogType;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationClientLogDocument;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationKeyDocument;
import com.ural.dejavutools.discordbot.service.boss.model.client.NotificationKey;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerRequestDTO;
import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DejaBossUtil {

  public static String keyEventResponseBuilder(NotificationKeyDocument notificationKeyDocument) {

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("--------------------------------------\n");
    stringBuilder.append("**" + notificationKeyDocument.getKey() + "**");
    stringBuilder.append("\n");

    return stringBuilder.toString();


  }

  public static NotificationKey keyBuilder(SlashCommandInteractionEvent event) {

    NotificationKey notificationKey = new NotificationKey();
    notificationKey.setKey(UUID.randomUUID().toString());
    notificationKey.setCreatingTime(LocalDateTime.now());
    notificationKey.setIsActive(true);
    notificationKey.setIsBlocked(false);
    notificationKey.setDiscordUserId(event.getUser().getId());
    notificationKey.setDiscordUserName(event.getUser().getName());
    notificationKey.setIps(new ArrayList<>());
    notificationKey.setUserLocale(event.getUserLocale().name());

    return notificationKey;


  }

  public static NotificationClientLogDocument buildNotificationClientLogDocument(LogType logType,
      String detail,
      String discordUserId,
      String discordUserName,
      SpawnHandlerRequestDTO requestDTO) {

    NotificationClientLogDocument notificationClientLogDocument = new NotificationClientLogDocument();
    notificationClientLogDocument.setLogType(logType);
    notificationClientLogDocument.setDetail(detail);
    notificationClientLogDocument.setIsTest(requestDTO.getIsTest());
    notificationClientLogDocument.setKey(requestDTO.getKey());
    notificationClientLogDocument.setUserIp(requestDTO.getUserIp());
    notificationClientLogDocument.setDiscordUserId(discordUserId);
    notificationClientLogDocument.setDiscordUserName(discordUserName);
    notificationClientLogDocument.setNotificationTime(LocalDateTime.now());
    notificationClientLogDocument.setBossType(requestDTO.getBossType());

    return notificationClientLogDocument;
  }

  public static String buildSpawnMessage(BossType bossType, String tagRoleId) {

    StringBuilder spawnNotificationMessageBuilder = new StringBuilder();
    spawnNotificationMessageBuilder.append(
        DiscordMessageUtils.getRoleAnnotationText(tagRoleId));
    spawnNotificationMessageBuilder.append(" " + bossType.toString());
    spawnNotificationMessageBuilder.append(" spawned.");

    return spawnNotificationMessageBuilder.toString();
  }

}
