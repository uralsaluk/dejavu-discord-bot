package com.ural.dejavutools.discordbot.service.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class TimeRangeUtils {

  public static Boolean checkTimeInHoursRange(LocalDateTime localDateTime, Long hours) {
    LocalDateTime now = LocalDateTime.now();

    long between = ChronoUnit.HOURS.between(now, localDateTime);

    return Math.abs(between) < hours;
  }


  public static Boolean checkTimeInSecondsRange(LocalDateTime localDateTime, Long seconds) {
    LocalDateTime now = LocalDateTime.now();

    long between = ChronoUnit.SECONDS.between(now, localDateTime);

    return Math.abs(between) < seconds;
  }


  public static Boolean checkNowIsBetween(OffsetDateTime start, OffsetDateTime end) {
    OffsetDateTime now = OffsetDateTime.now();

    return now.isAfter(start) && now.isBefore(end) ? true : false;


  }

}
