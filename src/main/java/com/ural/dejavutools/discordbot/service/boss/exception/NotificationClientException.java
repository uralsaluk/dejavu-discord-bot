package com.ural.dejavutools.discordbot.service.boss.exception;

import lombok.Getter;

@Getter
public class NotificationClientException extends RuntimeException {


  private String errorCode;
  private String errorMessage;

  private String key;


  public NotificationClientException(String errorCode, String message, String key) {
    super(message);
    this.errorCode = errorCode;
    this.errorMessage = message;
    this.key = key;
  }

}
