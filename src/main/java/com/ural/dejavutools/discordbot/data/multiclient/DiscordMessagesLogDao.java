package com.ural.dejavutools.discordbot.data.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.DiscordMessagesLogDocument;

public interface DiscordMessagesLogDao {

  void logMessages(DiscordMessagesLogDocument document);

}
