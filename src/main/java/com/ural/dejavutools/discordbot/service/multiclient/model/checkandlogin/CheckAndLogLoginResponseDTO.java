package com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAndLogLoginResponseDTO {

  private Boolean success;

  private String message;

  private String errorCode;

}
