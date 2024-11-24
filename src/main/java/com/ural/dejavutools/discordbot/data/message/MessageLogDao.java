package com.ural.dejavutools.discordbot.data.message;

import com.ural.dejavutools.discordbot.data.message.document.FHXDiscordMessage;

public interface MessageLogDao {

  void save(FHXDiscordMessage fhxDiscordMessage);

}
