package com.ural.dejavutools.discordbot.service.rank.restclient.model;

import com.ural.dejavutools.discordbot.service.rank.model.CharacterInfo;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FHXRankClientResponseDTO {

  private List<CharacterInfo> data;

}
