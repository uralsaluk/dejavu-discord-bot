package com.ural.dejavutools.discordbot.service.rank.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "dejavu.discord.rank")
@Component
@NoArgsConstructor
@Data
public class DejavuRankConfiguration {

  private String channelId;

  private String token;

  private Integer rankSize;

}
