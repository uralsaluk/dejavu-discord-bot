package com.ural.dejavutools.discordbot.service.multiclient.constant;

import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import org.apache.commons.lang3.StringUtils;

public class DiscordErrorMessages {

  public static final String USER_NOT_DEJAVU_MEMBER =
      "MultiClient application available for Dejavu players. "
          + "You are no longer dejavu member";

  public static final String USER_BLOCKED = "You are blocked contact with Fatality";

  public static final String USER_IP_LIMIT = "You reached ip limit to use multiclient";

  public static final String WRONG_GAME_CLIENT_VERSION = "FHX Launcher version error. Update your game";

  public static final String WRONG_MULTI_CLIENT_VERSION =
      "New Version 1.3.0 Available MultiClient version is wrong"
          + " \n Download new version from : https://uralsaluk.com/api/v0/multiClient/downloadClient \n"
          + " \n NEW!! Download Patcher to get new versions with single click(Put it inside fhx folder... Don't forget all this exe files requires jre folder to work)"
          + "\n https://uralsaluk.com/api/v0/multiClient/downloadPatcher"
          + "\n Last Version 1.3.0 notes :"
          + " \n * Force Login available (You will disconnect the user if account is already online) "
          + " \n MultiClient will ask you if you want to disconnect account or not"
          + " \n * MultiClient will check if gameserver is online (Prevent logining on server meintenance state)"
          + " \n * fhxMulti.exe will not declare version. So it will rewrite over old file if it's already exist.(Patcher)";
  public static final String LOGIN_ERROR = "FHX login error, check your userId and password";

  public static final String LOGIN_ACCOUNT_ALREADY_ONLINE = "FHX login error, fhx account already online";

  public static final String LOGIN_SUCCESS_WITH_FORCE_LOGIN = "You disconnected the ${discordUserId} ";

  public static final String DISCONNECTED_BY_OTHER_PLAYER = "You have been disconnected by ${discordUserId} ";


  public static String getMessageWithUserId(String message, String discordUserId) {

    return StringUtils.replace(message, "${discordUserId}",
        DiscordMessageUtils.getUserAnnotationText(discordUserId));


  }


}
