package com.ural.dejavutools.discordbot.service.boss.model.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckNotificationClientKeyRequestDTO {

  private String key;
  private String userIp;

}
