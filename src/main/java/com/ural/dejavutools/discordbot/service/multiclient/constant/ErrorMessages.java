package com.ural.dejavutools.discordbot.service.multiclient.constant;

public class ErrorMessages {

  public static final String NO_KEY_FOUND = "No multiclient key found";

  public static final String NO_KEY_FOUND_CODE = "ERR001";


  public static final String LOGIN_ERROR = "FHX login error, check your userId and password";

  public static final String LOGIN_ERROR_CODE = "ERR002";


  public static final String USER_NOT_DEJAVU_MEMBER = "User is no longer dejavu member";


  public static final String USER_BLOCKED = "User is blocked";

  public static final String USER_IP_LIMIT = "User has reached ip limit";

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

  public static final String LOGIN_ACCOUNT_ALREADY_ONLINE = "FHX login error, fhx account already online";

  public static final String LOGIN_ACCOUNT_ALREADY_ONLINE_CODE = "ERR003";

}
