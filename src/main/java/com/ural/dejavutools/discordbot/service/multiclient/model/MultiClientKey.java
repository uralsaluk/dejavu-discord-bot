package com.ural.dejavutools.discordbot.service.multiclient.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultiClientKey {

  private String key;
  private String discordUserName;
  private String asTag;
  private String discordUserId;
  private List<IpContext> ips;
  private List<String> ids;
  private Boolean isActive;
  private LocalDateTime creatingTime;
  private Boolean isBlocked;

  private String userLocale;


}
