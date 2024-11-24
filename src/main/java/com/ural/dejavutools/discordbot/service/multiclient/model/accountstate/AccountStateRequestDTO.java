package com.ural.dejavutools.discordbot.service.multiclient.model.accountstate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountStateRequestDTO {

  private String transactionId;
  private String ip;
  private String multiClientKey;
  private String fhxId;
  private FHXAction action;

}
