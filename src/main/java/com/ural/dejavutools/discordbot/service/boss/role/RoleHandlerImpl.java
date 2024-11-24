package com.ural.dejavutools.discordbot.service.boss.role;

import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoleHandlerImpl implements RoleHandler {

  private JDA jda;
  private DejavuBossConfiguration dejavuBossConfiguration;


  public RoleHandlerImpl(@Qualifier(value = "DejaBoss") JDA jda,
      DejavuBossConfiguration dejavuBossConfiguration) {
    this.jda = jda;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
  }

  @Override
  public void addNotificationRoleToUser(SlashCommandInteractionEvent event) {

    Role roleById = event.getGuild().getRoleById(dejavuBossConfiguration.getNotificationRoleId());

    event.getGuild().addRoleToMember(event.getUser(), roleById)
        .complete();

    event.reply(
        DiscordMessageUtils.getRoleAnnotationText(dejavuBossConfiguration.getNotificationRoleId())
            + " role added").setEphemeral(true).queue();


  }

  @Override
  public void removeNotificationRoleFromUser(SlashCommandInteractionEvent event) {

    Role roleById = event.getGuild().getRoleById(dejavuBossConfiguration.getNotificationRoleId());

    event.getGuild().removeRoleFromMember(event.getUser(), roleById)
        .complete();

    event.reply(
        DiscordMessageUtils.getRoleAnnotationText(dejavuBossConfiguration.getNotificationRoleId())
            + " role removed").setEphemeral(true).queue();

    //event.getHook().sendMessage("asdasd").setEphemeral(true).queue();
  }
}
