package com.ural.dejavutools.discordbot.service.multiclient;

import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyResponseDTO;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface MultiClientKeyService {


  void getMultiClientKey(SlashCommandInteractionEvent event);

  CheckKeyResponseDTO checkKey(CheckKeyRequestDTO checkKeyRequestDTO);

}
