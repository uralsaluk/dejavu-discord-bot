package com.ural.dejavutools.discordbot.service.boss.bosshandler.client;

import com.ural.dejavutools.discordbot.data.boss.NotificationClientKeyDao;
import com.ural.dejavutools.discordbot.data.boss.NotificationClientLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.LogType;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationClientLogDocument;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationKeyDocument;
import com.ural.dejavutools.discordbot.data.multiclient.document.IpContextDocument;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.constant.NotificationClientErrorMessages;
import com.ural.dejavutools.discordbot.service.boss.exception.NotificationClientException;
import com.ural.dejavutools.discordbot.service.boss.mapper.NotificationClientApplicationServiceMapper;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.NotificationKey;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerResponseDTO;
import com.ural.dejavutools.discordbot.service.boss.util.DejaBossUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ClientApplicationServiceImpl implements ClientApplicationService {

  private static final NotificationClientApplicationServiceMapper mapper = NotificationClientApplicationServiceMapper.MAPPER;

  private final NotificationClientKeyDao notificationClientKeyDao;
  private final NotificationClientLogDao notificationClientLogDao;

  private JDA jda;
  private DejavuBossConfiguration dejavuBossConfiguration;


  public ClientApplicationServiceImpl(@Qualifier(value = "DejaBoss") JDA jda,
      NotificationClientKeyDao notificationClientKeyDao,
      NotificationClientLogDao notificationClientLogDao,
      DejavuBossConfiguration dejavuBossConfiguration) {
    this.jda = jda;
    this.notificationClientKeyDao = notificationClientKeyDao;
    this.notificationClientLogDao = notificationClientLogDao;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
  }

  @Override
  public void getNotificationClientKey(SlashCommandInteractionEvent event) {

    if (!isUserDejavuMember(event.getUser().getId())) {

      event.reply("You are not Dejavu member.").setEphemeral(true).queue();
      return;
    }

    NotificationKeyDocument keyByUserId = notificationClientKeyDao.findKeyByUserId(
        event.getUser().getId());
    if (!Objects.isNull(keyByUserId)) {

      if (isUserBlocked(keyByUserId)) {

        event.reply("You are blocked").setEphemeral(true).queue();
        return;
      }

      event.reply(DejaBossUtil.keyEventResponseBuilder(keyByUserId)).setEphemeral(true)
          .queue();

      return;
    } else {

      NotificationKey notificationKey = DejaBossUtil.keyBuilder(event);

      NotificationKeyDocument notificationKeyDocument = mapper.toNotificationKeyDocument(
          notificationKey);

      NotificationKeyDocument savedDocument = notificationClientKeyDao.save(
          notificationKeyDocument);

      event.reply(DejaBossUtil.keyEventResponseBuilder(savedDocument)).setEphemeral(true)
          .queue();


    }


  }

  @Override
  public CheckNotificationClientKeyResponseDTO checkKey(
      CheckNotificationClientKeyRequestDTO requestDTO) {
    NotificationKeyDocument notificationKeyDocument = notificationClientKeyDao.findByKey(
        requestDTO.getKey());

    if (Objects.isNull(notificationKeyDocument)) {

      throw new NotificationClientException(NotificationClientErrorMessages.NO_KEY_FOUND_CODE,
          NotificationClientErrorMessages.NO_KEY_FOUND, requestDTO.getKey());
    }

    if (!isUserDejavuMember(notificationKeyDocument.getDiscordUserId())) {
      throw new NotificationClientException(NotificationClientErrorMessages.USER_IS_NOT_DEJAVU_CODE,
          NotificationClientErrorMessages.USER_IS_NOT_DEJAVU, requestDTO.getKey());
    }

    if (notificationKeyDocument.getIsBlocked()) {
      throw new NotificationClientException(NotificationClientErrorMessages.USER_BLOCKED_CODE,
          NotificationClientErrorMessages.USER_BLOCKED, requestDTO.getKey());
    }

    if (!CollectionUtils.isEmpty(notificationKeyDocument.getIps())
        && notificationKeyDocument.getIps().stream()
        .filter(x -> StringUtils.equalsIgnoreCase(x.getIp(), requestDTO.getUserIp()))
        .findFirst().isPresent()) {
      IpContextDocument ipContextDocument = notificationKeyDocument.getIps().stream()
          .filter(x -> StringUtils.equalsIgnoreCase(x.getIp(), requestDTO.getUserIp()))
          .findFirst().get();
      ipContextDocument.setLastTimeUsing(LocalDateTime.now());
    } else {
      notificationKeyDocument.getIps()
          .add(new IpContextDocument(requestDTO.getUserIp(), LocalDateTime.now(), null));
    }

    notificationClientKeyDao.save(notificationKeyDocument);

    return new CheckNotificationClientKeyResponseDTO(true);

  }

  @Override
  public SpawnHandlerResponseDTO spawnHandler(SpawnHandlerRequestDTO requestDTO) {

    NotificationKeyDocument notificationKeyDocument = notificationClientKeyDao.findByKey(
        requestDTO.getKey());

    if (Objects.isNull(notificationKeyDocument)) {

      throw new NotificationClientException(NotificationClientErrorMessages.NO_KEY_FOUND_CODE,
          NotificationClientErrorMessages.NO_KEY_FOUND, requestDTO.getKey());
    }

    if (notificationClientLogDao.findTotalRecordsInSecondsByKey(60,
        notificationKeyDocument.getKey()) >= 10) {
      throw new NotificationClientException(
          NotificationClientErrorMessages.USER_SPAM_CODE,
          NotificationClientErrorMessages.USER_SPAM, requestDTO.getKey());

    }

    if (requestDTO.getIsTest()) {

      handleTestRequest(requestDTO);
      NotificationClientLogDocument notificationClientLogDocument = DejaBossUtil.buildNotificationClientLogDocument(
          LogType.SPAWN, "Successful test notification",
          notificationKeyDocument.getDiscordUserId(), notificationKeyDocument.getDiscordUserName(),
          requestDTO);
      notificationClientLogDao.save(notificationClientLogDocument);

      return new SpawnHandlerResponseDTO(true);

    }

    if (notificationClientLogDao.isExistInSecondsByBossType(900,
        requestDTO.getBossType())) {
      throw new NotificationClientException(
          NotificationClientErrorMessages.SPAM_FOR_SAME_BOSS_EXCEPTION_CODE,
          NotificationClientErrorMessages.SPAM_FOR_SAME_BOSS_EXCEPTION, requestDTO.getKey());

    }

    String spawnNotificationMessage = DejaBossUtil.buildSpawnMessage(requestDTO.getBossType(),
        dejavuBossConfiguration.getNotifyAllRoleId());

    TextChannel textChannelById = jda.getTextChannelById(
        dejavuBossConfiguration.getBossChannelId());

    textChannelById.sendMessage(spawnNotificationMessage).complete();

    NotificationClientLogDocument notificationClientLogDocument = DejaBossUtil.buildNotificationClientLogDocument(
        LogType.SPAWN, "Successful",
        notificationKeyDocument.getDiscordUserId(), notificationKeyDocument.getDiscordUserName(),
        requestDTO);

    notificationClientLogDao.save(notificationClientLogDocument);

    return new SpawnHandlerResponseDTO(true);

  }

  private Boolean isUserDejavuMember(String userId) {
    Guild dejaGuild = jda.getGuildById(dejavuBossConfiguration.getAllowedGuildId());
    Role roleById = dejaGuild.getRoleById(dejavuBossConfiguration.getAllowedRoleId());
    List<Member> membersWithRoles = dejaGuild.getMembersWithRoles(roleById);

    return membersWithRoles.stream()
        .anyMatch(x -> StringUtils.equalsIgnoreCase(x.getUser().getId(), userId));
  }

  private Boolean isUserBlocked(NotificationKeyDocument document) {

    return document.getIsBlocked();

  }

  private void handleTestRequest(SpawnHandlerRequestDTO requestDTO) {

    String spawnNotificationMessage = DejaBossUtil.buildSpawnMessage(requestDTO.getBossType(),
        dejavuBossConfiguration.getNotifyAllRoleId());

    TextChannel textChannelById = jda.getTextChannelById(
        dejavuBossConfiguration.getTestPlaygroundChannelId());

    textChannelById.sendMessage(spawnNotificationMessage).complete();

  }
}
