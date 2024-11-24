package com.ural.dejavutools.discordbot.service.boss.bosshandler.message;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MessageHandler {


  void handleMessage(MessageReceivedEvent event);

  void bossLog(MessageReceivedEvent event);


  void gmLog(MessageReceivedEvent event);
}
