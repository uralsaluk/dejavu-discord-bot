package com.ural.dejavutools.discordbot.service.boss.constant;

public class NotificationClientErrorMessages {


  public static final String NO_KEY_FOUND = "Notification client key is not found";
  public static final String NO_KEY_FOUND_CODE = "ERR001";

  public static final String USER_IS_NOT_DEJAVU = "Owner of the key is not Dejavu user.";
  public static final String USER_IS_NOT_DEJAVU_CODE = "ERR002";

  public static final String USER_BLOCKED = "User is blocked";

  public static final String USER_BLOCKED_CODE = "ERR003";

  public static final String SPAM_FOR_SAME_BOSS_EXCEPTION = "Cannot trigger notification with in 900 secs for same boss";

  public static final String SPAM_FOR_SAME_BOSS_EXCEPTION_CODE = "ERR004";


  public static final String USER_SPAM = "Cannot trigger notification more than 10 times in 60 secs";

  public static final String USER_SPAM_CODE = "ERR005";
}
