package com.ural.dejavutools.discordbot.data.boss;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationClientLogDocument;

public interface NotificationClientLogDao {


  void save(NotificationClientLogDocument document);

  Boolean isExistInSecondsByBossType(Integer secs, BossType bossType);


  Long findTotalRecordsInSecondsByDiscordUserId(Integer secs, String discordUserId);


  Long findTotalRecordsInSecondsByKey(Integer secs, String key);


}
