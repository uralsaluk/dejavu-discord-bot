package com.ural.dejavutools.discordbot.service.boss.bosshandler.client;

import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerResponseDTO;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ClientApplicationService {


  void getNotificationClientKey(SlashCommandInteractionEvent event);

  CheckNotificationClientKeyResponseDTO checkKey(CheckNotificationClientKeyRequestDTO requestDTO);


  SpawnHandlerResponseDTO spawnHandler(SpawnHandlerRequestDTO requestDTO);
  

}
