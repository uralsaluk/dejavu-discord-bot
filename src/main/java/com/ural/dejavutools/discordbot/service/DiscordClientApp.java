package com.ural.dejavutools.discordbot.service;

import com.ural.dejavutools.discordbot.service.boss.BossListener;
import com.ural.dejavutools.discordbot.service.boss.configuration.DejavuBossConfiguration;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientListener;
import com.ural.dejavutools.discordbot.service.multiclient.configuration.DejavuMultiClientConfiguration;
import com.ural.dejavutools.discordbot.service.rank.configuration.DejavuRankConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */


@Configuration
public class DiscordClientApp {


  private BossListener bossListener;

  private MultiClientListener multiClientListener;

  private DejavuBossConfiguration dejavuBossConfiguration;
  private DejavuRankConfiguration dejavuRankConfiguration;

  private DejavuMultiClientConfiguration dejavuMultiClientConfiguration;

  @Autowired
  public DiscordClientApp(BossListener bossListener, MultiClientListener multiClientListener,
      DejavuBossConfiguration dejavuBossConfiguration,
      DejavuRankConfiguration dejavuRankConfiguration,
      DejavuMultiClientConfiguration dejavuMultiClientConfiguration) {
    this.bossListener = bossListener;
    this.multiClientListener = multiClientListener;
    this.dejavuBossConfiguration = dejavuBossConfiguration;
    this.dejavuRankConfiguration = dejavuRankConfiguration;
    this.dejavuMultiClientConfiguration = dejavuMultiClientConfiguration;
  }

  @Bean(name = "DejaBoss")
  public JDA startDejaBoss() {

    JDA jda = JDABuilder.createDefault(dejavuBossConfiguration.getToken())
        .setActivity(Activity.of(ActivityType.LISTENING, "Dejavu"))
        // .addEventListeners(new CommandManager())
        .addEventListeners(bossListener)
        .setChunkingFilter(ChunkingFilter.ALL)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES)
        .build()
        //.awaitReady()
        ;
    return jda;
  }

  @Bean(name = "DejaRank")
  public JDA startDejaRank() {

    JDA jda = JDABuilder.createDefault(dejavuRankConfiguration.getToken())
        .setActivity(Activity.of(ActivityType.LISTENING, "DejaRank"))
        // .addEventListeners(new CommandManager())
        //   .addEventListeners(bossListener/*, multiClientListener*/)
        .setChunkingFilter(ChunkingFilter.ALL)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES)
        .build()
        //.awaitReady()
        ;
    return jda;
  }

  @Bean(name = "DejaMultiClient")
  public JDA startDejaMultiClient() {

    JDA jda = JDABuilder.createDefault(dejavuMultiClientConfiguration.getToken())
        .setActivity(Activity.of(ActivityType.LISTENING, "Dejavu"))
        .addEventListeners(multiClientListener)
        .setChunkingFilter(ChunkingFilter.ALL)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES)
        .build();
    return jda;
  }

}
