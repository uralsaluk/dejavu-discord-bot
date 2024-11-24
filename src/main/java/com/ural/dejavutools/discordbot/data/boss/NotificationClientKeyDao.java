package com.ural.dejavutools.discordbot.data.boss;

import com.ural.dejavutools.discordbot.data.boss.document.NotificationKeyDocument;

public interface NotificationClientKeyDao {

  NotificationKeyDocument save(NotificationKeyDocument document);

  NotificationKeyDocument findByKey(String key);

  NotificationKeyDocument findKeyByUserId(String discordUserId);

}
