package com.ural.dejavutools.discordbot.service.util;

import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;

public class DiscordTİmeUtil {

  public static String getRelativeTime(OffsetDateTime offsetDateTime) {

    return "<t:" + offsetDateTime.toEpochSecond() + ":R>";
  }


  public static String getRelativeTimesOnMessage(String message, OffsetDateTime deadTime,
      Long windowEndAtSeconds, Long windowStartAtSeconds) {

    String deadTimeReplaced = StringUtils.replace(message, "${deadTime}",
        getRelativeTime(deadTime.plusSeconds(windowStartAtSeconds)));
    String finalMessage = StringUtils.replace(deadTimeReplaced, "${windowTime}",
        getRelativeTime(deadTime.plusSeconds(windowEndAtSeconds)));

    return finalMessage;
  }


}
