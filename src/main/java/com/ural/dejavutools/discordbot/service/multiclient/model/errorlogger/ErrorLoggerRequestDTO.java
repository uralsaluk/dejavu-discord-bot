package com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorLoggerRequestDTO {

  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private String errorMessage;
  private String source;
  private String ip;
}
