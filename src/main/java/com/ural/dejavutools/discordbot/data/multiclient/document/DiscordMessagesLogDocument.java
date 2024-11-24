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
public class DiscordMessagesLogDocument<T> {

  @Id
  @Field("_id")
  private ObjectId id;

  private String transactionId;
  private String discordUserId;
  private String discordUserName;
  private String message;

  private LocalDateTime localDateTime;

  private T request;

  public DiscordMessagesLogDocument(String transactionId, String discordUserId,
      String discordUserName, String message,
      T request) {
    this.transactionId = transactionId;
    this.discordUserId = discordUserId;
    this.discordUserName = discordUserName;
    this.message = message;
    this.localDateTime = LocalDateTime.now().now();
    this.request = request;
  }
}
