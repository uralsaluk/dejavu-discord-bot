package com.ural.dejavutools.discordbot.service.util;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class SchedulerUtils {

  public static Boolean checkTimeInRange(OffsetDateTime offsetDateTime) {
    OffsetDateTime now = OffsetDateTime.now();

    long between = ChronoUnit.SECONDS.between(now, offsetDateTime);

    return Math.abs(between) < 120;
  }

}
