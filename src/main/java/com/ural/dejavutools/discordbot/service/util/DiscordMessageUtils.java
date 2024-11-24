package com.ural.dejavutools.discordbot.service.util;

public class DiscordMessageUtils {

  public static String getUserAnnotationText(String userId) {

    //<@423937340591833090> helloagin

    return "<@" + userId + ">";
  }

  public static String getChannelAnnotationText(String channelId) {

    //<@423937340591833090> helloagin

    return "<#" + channelId + ">";
  }

  public static String getRoleAnnotationText(String roleId) {

    return "<@&" + roleId + ">";
  }


}
