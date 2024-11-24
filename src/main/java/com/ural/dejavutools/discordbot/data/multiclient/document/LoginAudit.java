package com.ural.dejavutools.discordbot.data.multiclient.document;

import com.ural.dejavutools.discordbot.data.multiclient.document.enums.LoginState;
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
public class LoginAudit {

  @Id
  @Field("_id")
  private ObjectId id;


  private LoginState status;
  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private String discordUserId;
  private String discordUserName;
  private String loginMessage;
  private LocalDateTime logDate;
  private String ip;
  private ActiveAccountDocument alreadyOnlineDocument;

}
