package com.ural.dejavutools.discordbot.data.message.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@NoArgsConstructor
public class FHXDiscordMessage {

  @Id
  @Field("_id")
  private ObjectId id;

  private String channelId;

  private String channelName;

  private String guildId;

  private String guildName;

  private String userId;

  private String userName;

  private String nickName;

  private String message;

  private String messageRaw;

  private String logTime;

}
