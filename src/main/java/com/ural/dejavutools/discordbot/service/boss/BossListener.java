package com.ural.dejavutools.discordbot.service.boss;

import com.ural.dejavutools.discordbot.data.boss.BossLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.BossHandler;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.client.ClientApplicationService;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.message.MessageHandler;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.boss.model.CommandEnum;
import com.ural.dejavutools.discordbot.service.boss.role.RoleHandler;
import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BossListener extends ListenerAdapter {


  private BossLogDao bossLogDao;

  private Map<String, BossHandler> optionHandlerStringMap;

  private DejavuBossConfiguration dejavuBossConfiguration;

  private RoleHandler roleHandler;

  private MessageHandler messageHandler;

  private ClientApplicationService clientApplicationService;


  @Autowired
  @Lazy
  public BossListener(BossLogDao bossLogDao,
      Map<String, BossHandler> optionHandlerStringMap,
      RoleHandler roleHandler, MessageHandler messageHandler,
      DejavuBossConfiguration dejavuBossConfiguration,
      ClientApplicationService clientApplicationService) {
    this.bossLogDao = bossLogDao;
    this.optionHandlerStringMap = optionHandlerStringMap;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
    this.roleHandler = roleHandler;
    this.messageHandler = messageHandler;
    this.clientApplicationService = clientApplicationService;
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    String command = event.getName();

    if (!event.getChannel().getId().equalsIgnoreCase(dejavuBossConfiguration.getBossChannelId())) {

      event.reply(
              "Only works in " + DiscordMessageUtils.getChannelAnnotationText(
                  dejavuBossConfiguration.getBossChannelId())
                  + " channel").setEphemeral(true)
          .queue();
      return;
    }

    CommandEnum commandEnum = CommandEnum.getCommandFromString(command);

    switch (commandEnum) {

      case LABARK:

        BossHandler labarkHandler = optionHandlerStringMap.get(BossType.LABARK.toString());

        labarkHandler.commandEventHandler(event);
        break;

      case ELCAVEN:

        BossHandler elcavenHandler = optionHandlerStringMap.get(BossType.ELCAVEN.toString());

        elcavenHandler.commandEventHandler(event);
        break;

      case RENEGADE:

        BossHandler renegadeHandler = optionHandlerStringMap.get(BossType.RENEGADE.toString());

        renegadeHandler.commandEventHandler(event);

        break;

      case OBSIDIAN:

        BossHandler obsidianHandler = optionHandlerStringMap.get(BossType.OBSIDIAN.toString());

        obsidianHandler.commandEventHandler(event);
        break;

      case GHOUL:

        BossHandler ghoulHandler = optionHandlerStringMap.get(BossType.GHOUL.toString());

        ghoulHandler.commandEventHandler(event);

        break;
      case ENABLE_NOTIFICATION:

        roleHandler.addNotificationRoleToUser(event);

        break;

      case DISABLE_NOTIFICATION:

        roleHandler.removeNotificationRoleFromUser(event);
        break;

      case GET_NOTIFICATION_CLIENT_KEY:

        clientApplicationService.getNotificationClientKey(event);
        break;
   /*     case GOLEM:

          OptionsHandler golemBossHandler = new GolemOptionsHandler();
          golemBossHandler.commandEventHandler(event);
          break;*/

      default:
        event.reply(
                "Unknown command").setEphemeral(true)
            .queue();

        break;


    }




/*
        if(command.equalsIgnoreCase("really"))
        {
            String userTag=event.getUser().getName();
            event.reply("I'm rly"+userTag).queue();
        }*/

    // super.onSlashCommandInteraction(event);
  }

  @Override
  public void onGuildReady(@NotNull GuildReadyEvent event) {

    List<CommandData> commandData = new ArrayList<>();

    OptionData optionBoss = new OptionData(OptionType.STRING, "option", "What do you want to do?",
        true)
        .addChoice("dead", "dead")
        .addChoice("info", "info")
        .addChoice("remove", "remove");

    OptionData minutesBefore = new OptionData(OptionType.INTEGER, "minutesbefore",
        "If you warn the bot late, write how many minutes ago boss died. ",
        false);

    // OptionData dropOption = new OptionData(OptionType.ATTACHMENT,
    //    "drop", "Do you want to add drop image ?", false);

    commandData.add(
        Commands.slash("labark", "LABARK Commands").addOptions(optionBoss, minutesBefore));
    commandData.add(
        Commands.slash("elcaven", "ELCAVEN Commands").addOptions(optionBoss, minutesBefore));
    commandData.add(
        Commands.slash("renegade", "RENEGADE Commands").addOptions(optionBoss, minutesBefore));
    commandData.add(
        Commands.slash("obsidian", "Obsidian Commands").addOptions(optionBoss, minutesBefore));
    commandData.add(
        Commands.slash("ghoul", "Ghoul Commands").addOptions(optionBoss, minutesBefore));

    commandData.add(
        Commands.slash("enablenotification",
            "Add you boss notification role to get notifications").addOptions());

    commandData.add(
        Commands.slash("disablenotification",
                "Removes boss notification role from user")
            .addOptions());

    commandData.add(
        Commands.slash("getnotificationclientkey",
            "This command gives your private key to use notification client application."));

    //commandData.add(Commands.slash("golem", "Golem Commands").addOptions(optionBoss, dropOption));
    //  commandData.add(Commands.slash("cult", "Cult Commands").addOptions(optionBoss, dropOption));

    //      commandData.add(Commands.slash("roles", "Display all roles on the server"));

    if (StringUtils.equalsIgnoreCase(dejavuBossConfiguration.getAllowedGuildId(),
        event.getGuild().getId())) {
      event.getGuild().updateCommands().addCommands(commandData).queue();
    }

  }


  @Override
  public void onMessageReceived(MessageReceivedEvent event) {

    if (!event.isFromGuild()) {
      return;
    }
    if (!StringUtils.equalsIgnoreCase(
        dejavuBossConfiguration.getAllowedGuildId(),
        event.getGuild().getId())) {
      return;
    }

    if (StringUtils.equalsIgnoreCase(event.getChannel().getId(),
        dejavuBossConfiguration.getFhxBossTimerChannelId()) && StringUtils.equalsIgnoreCase(
        event.getAuthor().getId(), dejavuBossConfiguration.getFhxBossTimerPublisherUserId())) {

      messageHandler.bossLog(event);
    }

    if (StringUtils.equalsIgnoreCase(event.getChannel().getId(),
        dejavuBossConfiguration.getFhxGmLogChannelId()) && StringUtils.equalsIgnoreCase(
        event.getAuthor().getId(), dejavuBossConfiguration.getFhxGmLogPublisherUserId())) {

      messageHandler.gmLog(event);
    }

    //messageHandler.handleMessage(event);

    super.onMessageReceived(event);
  }
}
