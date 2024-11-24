package com.ural.dejavutools.discordbot.controller.model.checkandloglogin;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckAndLogLoginRestResponse {

  private Boolean success;

  private String message;

  private String errorCode;

}
