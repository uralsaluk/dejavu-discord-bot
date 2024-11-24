package com.ural.dejavutools.discordbot.service.multiclient;

import com.ural.dejavutools.discordbot.service.multiclient.configuration.DejavuMultiClientConfiguration;
import com.ural.dejavutools.discordbot.service.multiclient.constant.MultiClientCommands;
import com.ural.dejavutools.discordbot.service.util.JDAUtils;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MultiClientListener extends ListenerAdapter {


  private DejavuMultiClientConfiguration dejavuMultiClientConfiguration;

  private JDA jda;

  private MultiClientKeyService multiClientKeyService;


  @Lazy
  public MultiClientListener(@Qualifier(value = "DejaMultiClient") JDA jda,
      DejavuMultiClientConfiguration dejavuMultiClientConfiguration,
      MultiClientKeyService multiClientKeyService) {
    this.jda = jda;
    this.dejavuMultiClientConfiguration = dejavuMultiClientConfiguration;
    this.multiClientKeyService = multiClientKeyService;
  }


  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

    if (!isUserDejavuMember(event.getUser().getId())) {

      event.reply("This bot works only for dejavu users").setEphemeral(true).queue();

      return;
    }

    String command = event.getName();

    MultiClientCommands multiClientCommands = MultiClientCommands.getCommandFromString(command);

    switch (multiClientCommands) {

      case GET_MULTI_CLIENT_KEY -> {

        multiClientKeyService.getMultiClientKey(event);

      }
      default -> {

        event.reply("Unknown command").setEphemeral(true).queue();
      }


    }

    super.onSlashCommandInteraction(event);
  }


  @Override
  public void onMessageReceived(MessageReceivedEvent event) {

   /* if (event.isFromGuild()) {
      return;
    }
    if (!isUserDejavuMember(event.getAuthor().getId())) {

      event.getChannel().sendMessage("This bot works only for dejavu users").queue();

      return;
    }

    MessageType type = event.getMessage().getType();*/



  /*  MultiClientCommands multiClientCommands = MultiClientCommands.getCommandFromString(command);

    switch (multiClientCommands) {

      case GET_MULTI_CLIENT_KEY -> {

        multiClientKeyService.getMultiClientKey(event);

      }


    }*/

    //  // event.getUser().openPrivateChannel().complete().sendMessage("role added").queue();
//    List<Role> roles = event.getGuild().getRoles();
    // List<Guild> mutualGuilds = event.getAuthor().getMutualGuilds();

    // System.out.println(roles);

    super.onMessageReceived(event);
  }


  private Boolean isUserDejavuMember(String userId) {
    Guild dejaGuild = jda.getGuildById(dejavuMultiClientConfiguration.getAllowedGuildId());
    Role roleById = dejaGuild.getRoleById(dejavuMultiClientConfiguration.getAllowedRoleId());
    List<Member> membersWithRoles = dejaGuild.getMembersWithRoles(roleById);

    return membersWithRoles.stream()
        .anyMatch(x -> StringUtils.equalsIgnoreCase(x.getUser().getId(), userId));
  }


  @Override
  public void onReady(ReadyEvent event) {
    JDAUtils.deleteGeneralCommands(jda);
    JDAUtils.deleteGuildCommands(jda);

    List<CommandData> commandData = new ArrayList<>();

    commandData.add(
        Commands.slash("getmulticlientkey",
            "This command gives your private key to use multiclient."));
    /*event.getJDA().upsertCommand(Commands.slash("getmulticlientkeys",
        "This command gives you the key to use multiclient.")).complete();*/

    // event.getJDA().updateCommands().addCommands(commandData).queue();
    event.getJDA().getGuildById(dejavuMultiClientConfiguration.getAllowedGuildId()).updateCommands()
        .addCommands(commandData).queue();
    super.onReady(event);
  }
}
