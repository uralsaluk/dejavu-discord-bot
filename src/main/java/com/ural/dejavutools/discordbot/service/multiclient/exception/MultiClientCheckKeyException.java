package com.ural.dejavutools.discordbot.service.multiclient.exception;

import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyRequestDTO;
import lombok.Getter;

@Getter
public class MultiClientCheckKeyException extends RuntimeException {

  private Boolean sendDiscordNotification;
  private String discordMessage;
  private String userId;

  private CheckKeyRequestDTO checkKeyRequestDTO;

  public MultiClientCheckKeyException(String message, String userId, String discordMessage,
      CheckKeyRequestDTO checkKeyRequestDTO) {
    super(message);
    this.discordMessage = discordMessage;
    this.userId = userId;
    this.sendDiscordNotification = true;
    this.checkKeyRequestDTO = checkKeyRequestDTO;
  }

  public MultiClientCheckKeyException(String message, CheckKeyRequestDTO checkKeyRequestDTO) {
    super(message);
    this.sendDiscordNotification = false;
    this.checkKeyRequestDTO = checkKeyRequestDTO;
  }
}
