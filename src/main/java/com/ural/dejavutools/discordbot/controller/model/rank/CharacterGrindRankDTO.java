package com.ural.dejavutools.discordbot.controller.model.rank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CharacterGrindRankDTO {

  private String name;
  private Integer level;
  private Long experience;
  private String className;
  private String guildName;


}
