package com.ural.dejavutools.discordbot.service.multiclient;

public interface DiscordNotifyService {


  <T> void sendMessage(String transactionId, String userId, String message, T request);

}
