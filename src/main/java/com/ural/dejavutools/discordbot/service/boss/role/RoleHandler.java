package com.ural.dejavutools.discordbot.service.boss.role;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface RoleHandler {

  void addNotificationRoleToUser(SlashCommandInteractionEvent event);

  void removeNotificationRoleFromUser(SlashCommandInteractionEvent event);

}
