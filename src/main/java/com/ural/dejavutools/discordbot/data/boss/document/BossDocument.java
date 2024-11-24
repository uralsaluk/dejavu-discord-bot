package com.ural.dejavutools.discordbot.data.boss.document;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "BossEvents")
@NoArgsConstructor
public class BossDocument {

  @Id
  @Field("_id")
  private ObjectId id;

  private BossType bossType;

  private Boolean isNotificationEnable;

  private String deadTime;

  private Long windowStartAtSeconds;
  private Long windowEndAtSeconds;

  private Integer totalNotificationCount;

  private Integer remainedNotificationCount;


  private List<AlertPeriod> alertPeriods;

  private String authorOfLog;
  private String userId;

  private String updatedTime;

}
