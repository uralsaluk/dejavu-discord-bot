package com.ural.dejavutools.discordbot.service.multiclient.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IpContext {

  private String ip;
  private LocalDateTime lastTimeUsing;
  private String geo;

}
