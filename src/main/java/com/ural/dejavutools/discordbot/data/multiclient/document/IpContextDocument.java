package com.ural.dejavutools.discordbot.data.multiclient.document;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpContextDocument {

  private String ip;
  private LocalDateTime lastTimeUsing;
  private String geo;

}
