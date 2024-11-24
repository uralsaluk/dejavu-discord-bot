package com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckAndLogLoginRequestDTO {

  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private Boolean loginSuccess;
  private String loginMessage;
  private String userIp;
  private Boolean forceLogin;

}
