package com.ural.dejavutools.discordbot.service.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;
import com.ural.dejavutools.discordbot.service.multiclient.model.MultiClientKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MultiClientKeyUtil {

  public static MultiClientKey keyBuilder(SlashCommandInteractionEvent event) {

    MultiClientKey multiClientKey = new MultiClientKey();
    multiClientKey.setKey(UUID.randomUUID().toString());
    multiClientKey.setCreatingTime(LocalDateTime.now());
    multiClientKey.setIsActive(true);
    multiClientKey.setIsBlocked(false);
    multiClientKey.setDiscordUserId(event.getUser().getId());
    multiClientKey.setDiscordUserName(event.getUser().getName());
    multiClientKey.setAsTag(event.getUser().getAsTag());
    multiClientKey.setIds(new ArrayList<>());
    multiClientKey.setIps(new ArrayList<>());
    multiClientKey.setUserLocale(event.getUserLocale().name());

    return multiClientKey;


  }

  public static String keyEventResponseBuilder(MultiClientKeyDocument keyByUserId) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("**IMPORTANT !** \n");
    stringBuilder.append("The sensitive datas that storing by this program are   \n");
    stringBuilder.append("Your IP (Max 5 active Ips in last 2 days)  \n");
    stringBuilder.append(
        "Your FHX id stored **encrypted** (to handle prevent account discconect by multiple login )  \n");
    stringBuilder.append(
        "**Your FHX password is not sending to my server (Only using for FHX Login API)**  \n");
    stringBuilder.append("Your discordUserId (To check your Dejavu role by key) \n\n");

    stringBuilder.append("--------------------------------------\n");
    stringBuilder.append("**" + keyByUserId.getKey() + "**");
    stringBuilder.append("\n");
    stringBuilder.append(
        "Your key is above. Please do not share it with anyone. You can use this key for all your game clients \n\n");
    stringBuilder.append(
        " Full Download Link: https://uralsaluk.com/api/v0/multiClient/download   \n\n");
    stringBuilder.append(
        " Only MultiClient.exe Download Link: https://uralsaluk.com/api/v0/multiClient/downloadClient   \n\n");
    stringBuilder.append(
        " Patcher Download Link: https://uralsaluk.com/api/v0/multiClient/downloadPatcher   \n\n");

    stringBuilder.append(
        "Make sure that the bot can send you private messages. In case of errors, it will warn you with private messages.  ");
    stringBuilder.append(
        "For example: Account is already online, Wrong password, Ip limit  etc...");

    return stringBuilder.toString();


  }

}
