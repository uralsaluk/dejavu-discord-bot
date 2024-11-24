package com.ural.dejavutools.discordbot.service.multiclient.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "dejavu.discord.multiclient")
@Component
@NoArgsConstructor
@Data
public class DejavuMultiClientConfiguration {

  private String token;

  private String allowedGuildId;

  private String allowedRoleId;

  private Long ipActiveTTLHours;

  private String multiClientVersion;

  private Boolean checkUserOnline;

  private String downloadFileName;

  private String patcherName;

  private String clientName;

  private String lastUpdates;

}
