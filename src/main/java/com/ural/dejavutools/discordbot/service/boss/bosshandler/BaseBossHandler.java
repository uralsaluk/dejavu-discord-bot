package com.ural.dejavutools.discordbot.service.boss.bosshandler;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.AlertPeriod;
import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.constant.BossInteraction;
import com.ural.dejavutools.discordbot.service.boss.constant.BossMessages;
import com.ural.dejavutools.discordbot.service.boss.constant.FHXBossAlertType;
import com.ural.dejavutools.discordbot.service.boss.model.BossEventDTO;
import com.ural.dejavutools.discordbot.service.util.DiscordTİmeUtil;
import com.ural.dejavutools.discordbot.service.util.SchedulerUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

public abstract class BaseBossHandler implements BossHandler {


  private static final Integer bossSpawnRangePercentConstant = 20;

  private BossLogDao bossLogDao;
  private JDA jda;

  private BossType bossType;

  private Long respawnTimeSeconds;

  protected Long windowStartAtSeconds;
  protected Long windowEndAtSeconds;

  private DejavuBossConfiguration dejavuBossConfiguration;

  @Autowired
  public BaseBossHandler(BossLogDao bossLogDao, JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration, BossType bossType, Long respawnTimeSeconds) {
    this.bossLogDao = bossLogDao;
    this.jda = jda;
    this.bossType = bossType;
    this.respawnTimeSeconds = respawnTimeSeconds;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
    double range = respawnTimeSeconds * (bossSpawnRangePercentConstant / 100.0);
    BigDecimal bd = new BigDecimal(range).setScale(0, RoundingMode.CEILING);
    Long roundedRange = bd.longValue();

    this.windowStartAtSeconds =
        respawnTimeSeconds - roundedRange;
    this.windowEndAtSeconds =
        respawnTimeSeconds + roundedRange;


  }

  public abstract String getInfoMessage(BossDocument bossDocument);

  public abstract void setPeriods(BossDocument bossDocument);

  @Override
  public void commandEventHandler(SlashCommandInteractionEvent event) {

    BossEventDTO bossEventDTO = new BossEventDTO();
    bossEventDTO.setUserId(event.getUser().getId());
    bossEventDTO.setUserName(event.getUser().getName());
    bossEventDTO.setOption(event.getOption("option").getAsString());
    OptionMapping minutesBefore = event.getOption("minutesbefore");
    if (minutesBefore != null) {
      bossEventDTO.setMinutesBeforeOption(minutesBefore.getAsInt());
    }
    bossEventDTO.setSlashCommandInteractionEvent(event);
    bossEventDTO.setBossInteraction(BossInteraction.COMMAND);

    eventHandler(bossEventDTO);

  }

  @Override
  public void messageEventHandler(MessageReceivedEvent event, FHXBossAlertType fhxBossAlertType) {

    BossEventDTO bossEventDTO = new BossEventDTO();
    bossEventDTO.setUserId(event.getAuthor().getId());
    bossEventDTO.setUserName(event.getAuthor().getName());
    if (fhxBossAlertType.equals(FHXBossAlertType.DIED)) {
      bossEventDTO.setOption("dead");
    }
    if (fhxBossAlertType.equals(FHXBossAlertType.SPAWNED)) {
      bossEventDTO.setOption("remove");
    }

    bossEventDTO.setBossInteraction(BossInteraction.MESSAGE);
    eventHandler(bossEventDTO);

  }


  @Override
  public void handleScheduler(BossDocument record) {
    if (record.getIsNotificationEnable() && record.getRemainedNotificationCount() > 0) {

      for (AlertPeriod alertPeriod : record.getAlertPeriods()) {
        if (alertPeriod.getEnabled() && SchedulerUtils.checkTimeInRange(
            OffsetDateTime.parse(record.getDeadTime())
                .plusSeconds(alertPeriod.getPeriodSeconds()))) {

          alertPeriod.setEnabled(false);
          record.setRemainedNotificationCount(record.getRemainedNotificationCount() - 1);

          TextChannel textChannelById = jda.getTextChannelById(
              dejavuBossConfiguration.getBossChannelId());
          String relativeTimesOnMessage = DiscordTİmeUtil.getRelativeTimesOnMessage(
              alertPeriod.getAlertMessage(), OffsetDateTime.parse(record.getDeadTime()),
              record.getWindowEndAtSeconds(), this.windowStartAtSeconds);

          textChannelById.sendMessage(BossMessages.updateRoleTag(relativeTimesOnMessage,
              dejavuBossConfiguration.getNotificationRoleId())).queue();
         /* if (record.getRemainedNotificationCount() == 0) {
            record.setIsNotificationEnable(false);
            textChannelById.sendMessage(BossMessages.getMessageByBossType(this.bossType,
                BossMessages.LAST_NOTIFICATION_SENT)).queue();

          }*/
          record.setUpdatedTime(OffsetDateTime.now().toString());
          bossLogDao.saveBossRecord(record);

        }

      }


    }

    if (record.getIsNotificationEnable() && OffsetDateTime.now()
        .isAfter(OffsetDateTime.parse(record.getDeadTime()).plusSeconds(
            record.getWindowEndAtSeconds()))) {

      record.setIsNotificationEnable(false);
      bossLogDao.saveBossRecord(record);
    }

  }


  private void eventHandler(BossEventDTO bossEventDTO) {

    String message = bossEventDTO.getOption();

    if (StringUtils.equalsIgnoreCase("dead", message)) {

      checkActiveRecordExist(bossEventDTO.getUserId());

      BossDocument bossDocument = new BossDocument();
      bossDocument.setBossType(this.bossType);
      bossDocument.setAuthorOfLog(bossEventDTO.getUserName());
      bossDocument.setUserId(bossEventDTO.getUserId());
      bossDocument.setIsNotificationEnable(true);
      OffsetDateTime now = OffsetDateTime.now();

      if (bossEventDTO.getMinutesBeforeOption() != null) {
        now = now.minusMinutes(bossEventDTO.getMinutesBeforeOption());
      }
      bossDocument.setDeadTime(now.toString());

      bossDocument.setWindowEndAtSeconds(this.windowEndAtSeconds);
      bossDocument.setWindowStartAtSeconds(this.windowStartAtSeconds);
      setPeriods(bossDocument);
      bossDocument.setTotalNotificationCount(bossDocument.getAlertPeriods().size());
      bossDocument.setRemainedNotificationCount(bossDocument.getAlertPeriods().size());

      bossLogDao.saveBossRecord(bossDocument);

      String relativeTimesOnMessage = getFirstMessage(bossDocument);

      reply(relativeTimesOnMessage, false, bossEventDTO);


    }

    if (StringUtils.equalsIgnoreCase("info", message)) {
      Criteria criteria = new Criteria();
      criteria.and("bossType").is(this.bossType);
      criteria.and("isNotificationEnable").is(true);
      Query query = new Query(criteria);
      query.with(Sort.by(Direction.DESC, "deadTime"));
      BossDocument activeRecord = bossLogDao.findOneByFilter(query);

      if (activeRecord == null) {

        reply(BossMessages.getMessageByBossType(this.bossType, BossMessages.NO_ACTIVE_RECORD), true,
            bossEventDTO);

      } else {
        String relativeTimesOnMessage = getInfoMessage(activeRecord);

        reply(relativeTimesOnMessage, true, bossEventDTO);
      }


    }

    if (StringUtils.equalsIgnoreCase("remove", message)) {
      Criteria criteria = new Criteria();
      criteria.and("bossType").is(this.bossType);
      criteria.and("isNotificationEnable").is(true);
      Query query = new Query(criteria);
      query.with(Sort.by(Direction.DESC, "deadTime"));
      BossDocument activeRecord = bossLogDao.findOneByFilter(query);

      if (activeRecord == null) {

        reply(BossMessages.getMessageByBossType(this.bossType, BossMessages.NO_ACTIVE_RECORD),
            false, bossEventDTO);
      } else {
        bossLogDao.disableNotificationsByQuery(query);
        reply(BossMessages.getMessageByBossType(this.bossType, BossMessages.ACTIVE_RECORD_REMOVED,
            bossEventDTO.getUserId()), false, bossEventDTO);

      }
    }

  }

  private void reply(String message, Boolean ephemeral, BossEventDTO bossEventDTO) {

    if (bossEventDTO.getBossInteraction().equals(BossInteraction.COMMAND)) {

      ReplyCallbackAction reply = bossEventDTO.getSlashCommandInteractionEvent().reply(message);
      if (ephemeral) {
        reply.setEphemeral(true);
      }
      reply.complete();
      return;
    }
    TextChannel textChannelById = jda.getTextChannelById(
        dejavuBossConfiguration.getBossChannelId());
    textChannelById.sendMessage(message).complete();

  }


  private void checkActiveRecordExist(String userId) {
    Criteria criteria = new Criteria();
    criteria.and("bossType").is(this.bossType);
    criteria.and("isNotificationEnable").is(true);
    Query query = new Query(criteria);
    List<BossDocument> activeRecords = bossLogDao.getByFilter(query);

    if (!CollectionUtils.isEmpty(activeRecords)) {
      bossLogDao.disableNotificationsByQuery(query);
      TextChannel textChannelById = jda.getTextChannelById(
          dejavuBossConfiguration.getBossChannelId());
      textChannelById.sendMessage(
              BossMessages.getMessageByBossType(this.bossType, BossMessages.ACTIVE_RECORD_REMOVED,
                  userId))
          .complete();


    }
  }

  private String getFirstMessage(BossDocument bossDocument) {

    return BossMessages.getFirstMessage(this.bossType,
        OffsetDateTime.parse(bossDocument.getDeadTime()), bossDocument.getUserId());
  }


}
