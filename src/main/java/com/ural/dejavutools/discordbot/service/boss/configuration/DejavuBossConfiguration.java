package com.ural.dejavutools.discordbot.service.boss.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "dejavu.discord.boss")
@Component
@NoArgsConstructor
@Data
public class DejavuBossConfiguration {

  private String bossChannelId;

  private String token;

  private String notificationRoleId;

  private String notifyAllRoleId;

  private String allowedGuildId;

  private String allowedRoleId;

  private String fhxBossTimerChannelId;

  private String fhxBossTimerPublisherUserId;

  private String fhxGmLogChannelId;
  private String fhxGmLogPublisherUserId;

  private String testPlaygroundChannelId;

}
