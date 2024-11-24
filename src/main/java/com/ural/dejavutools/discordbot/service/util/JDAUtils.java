package com.ural.dejavutools.discordbot.service.util;

import net.dv8tion.jda.api.JDA;

public class JDAUtils {


  public static void deleteGeneralCommands(JDA jda) {

    jda.retrieveCommands().complete().forEach(x -> x.delete().complete());


  }


  public static void deleteGuildCommands(JDA jda) {

    jda.getGuilds()
        .forEach(x -> x.retrieveCommands().complete().forEach(y -> y.delete().complete()));

  }

}
