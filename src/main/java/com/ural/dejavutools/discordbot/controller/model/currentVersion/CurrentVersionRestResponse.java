package com.ural.dejavutools.discordbot.controller.model.currentVersion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentVersionRestResponse {


  private String version;

  private String lastUpdates;

}
