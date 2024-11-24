package com.ural.dejavutools.discordbot.service.boss.bosshandler;

import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import com.ural.dejavutools.discordbot.service.boss.constant.FHXBossAlertType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface BossHandler {

  void commandEventHandler(SlashCommandInteractionEvent event);

  void messageEventHandler(MessageReceivedEvent event, FHXBossAlertType fhxBossAlertType);

  void handleScheduler(BossDocument record);


}
