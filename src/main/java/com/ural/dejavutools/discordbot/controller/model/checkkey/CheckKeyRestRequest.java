package com.ural.dejavutools.discordbot.controller.model.checkkey;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckKeyRestRequest {

  private String multiClientKey;
  private String fhxId;
  private String gameVersion;
  private Boolean isGameVersionValid;
  private String multiClientVersion;
  private String transactionId;

}
