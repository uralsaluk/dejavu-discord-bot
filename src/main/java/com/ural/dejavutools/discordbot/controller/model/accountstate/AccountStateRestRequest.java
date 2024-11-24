package com.ural.dejavutools.discordbot.controller.model.accountstate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountStateRestRequest {

  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private String action;


}
