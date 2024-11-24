package com.ural.dejavutools.discordbot.service.multiclient.model.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiClientVersionDTO {

  private String version;

  private String lastUpdates;
}
