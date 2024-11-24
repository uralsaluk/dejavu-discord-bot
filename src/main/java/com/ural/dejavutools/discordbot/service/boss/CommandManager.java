package com.ural.dejavutools.discordbot.service.boss;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class CommandManager extends ListenerAdapter {
//   List<String> TIME_ZONE_LIST = Arrays.asList("PST","EST","GMT","CET","EET","TRT");
  //sdf.settimezone falan
//  @Override
//  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//    String command = event.getName();
//
//    if (!event.getChannel().getId().equalsIgnoreCase("1071510690717777941")) {
//
//      CommandEnum commandEnum = CommandEnum.getCommandFromString(command);
//
//      switch (commandEnum) {
//
//        case ELCAVEN:
//
//          OptionMapping messageOption = event.getOption("option");
//
//          if (messageOption != null) {
//
//            OptionsHandler elcavenBossHandler = new ElcavenOptionsHandler();
//
//            elcavenBossHandler.commandEventHandler(event);
//          }
//          break;
//
//        case CULT:
//
//          OptionsHandler cultBossHandler = new CultOptionsHandler();
//          cultBossHandler.commandEventHandler(event);
//          break;
//
//        case LABORC:
//
//          OptionsHandler laborcBossHandler = new LaborcOptionsHandler();
//          laborcBossHandler.commandEventHandler(event);
//          break;
//     /*   case GOLEM:
//
//          OptionsHandler golemBossHandler = new GolemOptionsHandler();
//          golemBossHandler.commandEventHandler(event);
//          break;*/
//
//        case UPDATE_SPAWN_TIME:
//
//          SpawnOptionsHandler spawnOptionHandler = new SpawnTimeOptionsHandler();
//          spawnOptionHandler.commandEventHandler(event);
//
//          break;
//
//        case GET_SPAWN_TIME:
//
//          SpawnOptionsHandler getSpawnHandler = new SpawnTimeOptionsHandler();
//          getSpawnHandler.printSpawnTimes(event);
//          break;
//        case GET_TIME_ALL:
//
//          SpawnOptionsHandler spawnHandler = new SpawnTimeOptionsHandler();
//          spawnHandler.printDeadTimes(event);
//          break;
//
//        case TSTEST:
//
//          OffsetDateTime offsetDateTimes = OffsetDateTime.now();
//          OffsetDateTime offsetDateTimesPlusTen = offsetDateTimes.plusHours(8);
//
//          String ehe = "<t:" + offsetDateTimesPlusTen.toEpochSecond() + ":R>";
//
//          event.reply("Test plus 8 hours ts : " +
//              ehe).queue();
//
//          break;
//
//        default:
//          StringBuilder stringBuilder = new StringBuilder();
//
//          OffsetDateTime offsetDateTime = OffsetDateTime.now();
//
//          LocalDateTime dateTime = LocalDateTime.now();
//
//          event.reply("Current command options are :" +
//              Arrays.toString(CommandEnum.values()));
//
//          String plustenString = "<t:" + offsetDateTime.toEpochSecond() + ":R>";
//
//          event.reply("Test ts : " +
//              plustenString).queue();
//
//          break;
//
//
//      }
//
//
//    } else {
//      event.deferReply().queue();
//      event.getHook().sendMessage("Only works in dejavu-boss-fights channel").setEphemeral(true)
//          .queue();
//
//    }
//
//
//
//
//
//
//
//
///*
//        if(command.equalsIgnoreCase("really"))
//        {
//            String userTag=event.getUser().getName();
//            event.reply("I'm rly"+userTag).queue();
//        }*/
//
//    // super.onSlashCommandInteraction(event);
//  }
//
//  @Override
//  public void onGuildReady(@NotNull GuildReadyEvent event) {
//    //  if(event.getGuild().getId().equals("1071510690717777940")){
//
//    List<CommandData> commandData = new ArrayList<>();
//
//    OptionData optionBoss = new OptionData(OptionType.STRING, "option", "What do you want to do?",
//        true)
//        .addChoice("dead", "dead")
//        .addChoice("info", "info")
//        .addChoice("remove last", "remove last");
//
//    OptionData bossOptions = new OptionData(OptionType.STRING, "boss", "Which boss ?", true)
//        .addChoice("elcaven", "elcaven")
//        .addChoice("cult", "cult")
//        .addChoice("golem", "golem")
//        .addChoice("laborc", "laborc");
//
//    OptionData hourRequest = new OptionData(OptionType.INTEGER, "hours", "Hours", true);
//    OptionData minuteRequest = new OptionData(OptionType.INTEGER, "minutes", "Minutes", true);
//
//    OptionData dropOption = new OptionData(OptionType.ATTACHMENT,
//        "drop", "Do you want to add drop image ?", false);
//
//    commandData.add(Commands.slash("help", "DejaBoss Commands"));
//    commandData.add(Commands.slash("tstest", "8 hours later timestamp test"));
//    commandData.add(
//        Commands.slash("elcaven", "Elcaven Commands").addOptions(optionBoss, dropOption));
//    commandData.add(Commands.slash("cult", "Cult Commands").addOptions(optionBoss, dropOption));
//    commandData.add(Commands.slash("laborc", "Laborc Commands").addOptions(optionBoss, dropOption));
//    // commandData.add(Commands.slash("golem", "Golem Commands").addOptions(optionBoss, dropOption));
//    commandData.add(Commands.slash("update_spawn_time", "Update boss spawn time")
//        .addOptions(bossOptions, hourRequest, minuteRequest));
//    commandData.add(Commands.slash("get_time_all", "Shows how long ago bosses died."));
//
//    commandData.add(Commands.slash("get_spawn_time", "Get Boss Spawn Durations"));
//    //      commandData.add(Commands.slash("roles", "Display all roles on the server"));
//    event.getGuild().updateCommands().addCommands(commandData).queue();
//
//    //   event.getGuild().deleteCommandById("1071427798838825111").queue();
//
//    /*    event.getJDA().getGuildChannelById("1071425479657148519").getGuild()
//                .updateCommands().addCommands(commandData).queue(); */
//    //  }
//
//  }
////   @Override
////    public void onReady(@NotNull ReadyEvent event) {
////
////
////
////            List<CommandData> commandData = new ArrayList<>();
////
////            OptionData optionBoss = new OptionData(OptionType.STRING, "option", "What do you want to do?", true)
////                .addChoice("dead", "dead")
////                .addChoice("info", "info")
////                .addChoice("remove last", "remove last");
////
////            OptionData bossOptions = new OptionData(OptionType.STRING, "boss", "Which boss ?", true)
////                .addChoice("elcaven", "elcaven")
////                .addChoice("cult", "cult")
////                .addChoice("golem", "golem")
////                .addChoice("laborc", "laborc");
////
////            OptionData hourRequest = new OptionData(OptionType.INTEGER, "hours", "Hours", true);
////            OptionData minuteRequest = new OptionData(OptionType.INTEGER, "minutes", "Minutes", true);
////
////            OptionData dropOption = new OptionData(OptionType.ATTACHMENT,
////                "drop", "Do you want to add drop image ?", false);
////
////            commandData.add(Commands.slash("help", "DejaBoss Commands"));
////            commandData.add(Commands.slash("tstest", "8 hours later timestamp test"));
////            commandData.add(Commands.slash("elcaven", "Elcaven Commands").addOptions(optionBoss,dropOption));
////            commandData.add(Commands.slash("cult", "Cult Commands").addOptions(optionBoss,dropOption));
////            commandData.add(Commands.slash("laborc", "Laborc Commands").addOptions(optionBoss,dropOption));
////            commandData.add(Commands.slash("golem", "Golem Commands").addOptions(optionBoss,dropOption));
////            commandData.add(Commands.slash("update_spawn_time", "Update boss spawn time")
////                .addOptions(bossOptions,hourRequest,minuteRequest));
////            commandData.add(Commands.slash("get_time_all", "Shows how long ago bosses died."));
////
////            commandData.add(Commands.slash("get_spawn_time", "Get Boss Spawn Durations"));
////            //      commandData.add(Commands.slash("roles", "Display all roles on the server"));
////            event.getJDA().updateCommands().addCommands(commandData).queue();
////
////            //   event.getGuild().deleteCommandById("1071427798838825111").queue();
////
////    /*    event.getJDA().getGuildChannelById("1071425479657148519").getGuild()
////                .updateCommands().addCommands(commandData).queue(); */
////
////
////    }
//
//
//
//
//
//    /* @Override
//    public void onReady(@NotNull ReadyEvent event) {
//        List<CommandData> commandData = new ArrayList<>();
//        commandData.add(Commands.slash("really", "Get welcomed by the bot"));
//        commandData.add(Commands.slash("roles", "Display all roles on the server"));
//
//       RestAction<List<Command>> listRestAction =
//                event.getJDA().retrieveCommands();
//
//
//        //     event.getJDA().updateCommands().addCommands(commandData).queue();
//    }
//*/
}
