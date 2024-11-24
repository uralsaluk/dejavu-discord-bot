package com.ural.dejavutools.discordbot.data.boss.document;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document
@NoArgsConstructor
public class NotificationClientLogDocument {

  @Id
  @Field("_id")
  private ObjectId id;

  private String key;

  private String discordUserId;

  private String discordUserName;

  private String userIp;

  private BossType bossType;

  private LogType logType;

  private Boolean isTest;

  private String detail;

  private LocalDateTime notificationTime;


}
