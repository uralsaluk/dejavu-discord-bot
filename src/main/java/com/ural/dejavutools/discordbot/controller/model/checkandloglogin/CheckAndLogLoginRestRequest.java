package com.ural.dejavutools.discordbot.controller.model.checkandloglogin;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckAndLogLoginRestRequest {

  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private Boolean loginSuccess;
  private String loginMessage;
  private Boolean forceLogin;

}
