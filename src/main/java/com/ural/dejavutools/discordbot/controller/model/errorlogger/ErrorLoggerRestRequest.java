package com.ural.dejavutools.discordbot.controller.model.errorlogger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorLoggerRestRequest {

  private String transactionId;
  private String multiClientKey;
  private String fhxId;
  private String errorMessage;
  private String source;
}
