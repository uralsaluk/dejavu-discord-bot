package com.ural.dejavutools.discordbot.service.boss.model;

public enum CommandEnum {

  LABARK("labark"),

  ELCAVEN("elcaven"),


  RENEGADE("renegade"),

  GHOUL("ghoul"),


  OBSIDIAN("obsidian"),


  ENABLE_NOTIFICATION("enablenotification"),

  DISABLE_NOTIFICATION("disablenotification"),

  GET_NOTIFICATION_CLIENT_KEY("getnotificationclientkey");

  public final String label;


  CommandEnum(String label) {

    this.label = label;
  }


  public static CommandEnum getCommandFromString(String command) {

    for (CommandEnum commandEnum : values()) {

      if (commandEnum.label.equalsIgnoreCase(command)) {
        return commandEnum;
      }


    }
    return null;


  }
}
