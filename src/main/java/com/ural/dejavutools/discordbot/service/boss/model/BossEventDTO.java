package com.ural.dejavutools.discordbot.service.boss.model;

import com.ural.dejavutools.discordbot.service.boss.constant.BossInteraction;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Data
@NoArgsConstructor
public class BossEventDTO {

  private String option;
  private Integer minutesBeforeOption;
  private String userId;
  private String userName;
  private BossInteraction bossInteraction;
  private SlashCommandInteractionEvent slashCommandInteractionEvent;

}
