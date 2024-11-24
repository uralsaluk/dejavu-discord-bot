package com.ural.dejavutools.discordbot.service.rank.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class CharacterInfo implements Comparable<CharacterInfo> {

  private String name;
  private Integer level;
  private Long experience;
  private String className;
  private String guildName;

  @Override
  public int compareTo(@NotNull CharacterInfo o) {
    return Long.compare(this.experience, o.getExperience());
  }
}
