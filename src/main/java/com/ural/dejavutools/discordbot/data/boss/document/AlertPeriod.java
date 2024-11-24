package com.ural.dejavutools.discordbot.data.boss.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertPeriod {

  private Long periodSeconds;
  private Boolean enabled;
  private String alertMessage;
}
