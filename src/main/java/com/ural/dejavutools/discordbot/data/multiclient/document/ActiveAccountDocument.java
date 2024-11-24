package com.ural.dejavutools.discordbot.data.multiclient.document;

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
public class ActiveAccountDocument {


  @Id
  @Field("_id")
  private ObjectId id;

  private String multiClientKey;
  private String fhxId;
  private String discordUserId;
  private String discordUserName;
  private LocalDateTime lastUpdate;
  private LocalDateTime createTime;
  private Boolean openToDisconnect;
  private String ip;


}
