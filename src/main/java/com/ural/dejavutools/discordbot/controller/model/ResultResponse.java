package com.ural.dejavutools.discordbot.controller.model;

import lombok.Data;

@Data
public class ResultResponse {

  private Boolean success;
  private String errorCode;
  private String errorMessage;


  public ResultResponse() {
    this.success = true;
    this.errorCode = "1";
    this.errorMessage = "";
  }

  public ResultResponse(Boolean success, String errorCode, String errorMessage) {
    this.success = success;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
