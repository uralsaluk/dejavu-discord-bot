package com.ural.dejavutools.discordbot.service.boss.model.client;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpawnHandlerRequestDTO {

  private BossType bossType;
  private String key;
  private String userIp;
  private Boolean isTest;

}
