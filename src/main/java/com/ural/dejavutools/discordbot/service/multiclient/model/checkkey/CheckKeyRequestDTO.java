package com.ural.dejavutools.discordbot.service.multiclient.model.checkkey;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckKeyRequestDTO {

  private String multiClientKey;
  private String userIp;
  private String fhxId;
  private String gameVersion;
  private Boolean isGameVersionValid;
  private String multiClientVersion;
  private String transactionId;
}
