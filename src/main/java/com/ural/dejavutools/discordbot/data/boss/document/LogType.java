package com.ural.dejavutools.discordbot.data.boss.document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum LogType {

  SPAWN,
  DEAD,
  ERROR;
}
