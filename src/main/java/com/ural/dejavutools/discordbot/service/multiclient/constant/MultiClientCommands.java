package com.ural.dejavutools.discordbot.service.multiclient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MultiClientCommands {

  GET_MULTI_CLIENT_KEY("getmulticlientkey"),
  UNKNOWN("UNKNOWN");


  public final String label;


  public static MultiClientCommands getCommandFromString(String command) {

    for (MultiClientCommands multiClientCommands : values()) {

      if (multiClientCommands.label.equalsIgnoreCase(command)) {
        return multiClientCommands;
      }


    }
    return UNKNOWN;


  }
}
