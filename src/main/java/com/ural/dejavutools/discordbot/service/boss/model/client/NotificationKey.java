package com.ural.dejavutools.discordbot.service.boss.model.client;

import com.ural.dejavutools.discordbot.service.multiclient.model.IpContext;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationKey {


  private String key;
  private String discordUserName;
  private String discordUserId;
  private List<IpContext> ips;
  private Boolean isActive;
  private LocalDateTime creatingTime;
  private Boolean isBlocked;
  private String userLocale;


}
