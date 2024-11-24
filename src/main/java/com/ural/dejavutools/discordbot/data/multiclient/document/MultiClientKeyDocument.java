package com.ural.dejavutools.discordbot.data.multiclient.document;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@NoArgsConstructor
public class MultiClientKeyDocument {

  @Id
  @Field("_id")
  private ObjectId id;

  private String key;
  private String discordUserName;
  private String asTag;
  private String discordUserId;
  private List<IpContextDocument> ips;
  private List<String> ids;
  private Boolean isActive;
  private LocalDateTime creatingTime;
  private Boolean isBlocked;
  private String userLocale;

}
