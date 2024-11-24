package com.ural.dejavutools.discordbot.controller.model.boss.spawnhandler;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import javax.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpawnHandlerRestRequest {

  @Valid
  private BossType bossType;
  private String key;
  private Boolean isTest;

}
