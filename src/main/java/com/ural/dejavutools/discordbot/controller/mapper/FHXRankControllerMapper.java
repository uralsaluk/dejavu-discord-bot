package com.ural.dejavutools.discordbot.controller.mapper;

import com.ural.dejavutools.discordbot.controller.model.rank.CharacterGrindRankDTO;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterGrindRank;
import java.time.LocalDate;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring", imports = {
    LocalDate.class})
public interface FHXRankControllerMapper {

  FHXRankControllerMapper MAPPER = Mappers.getMapper(FHXRankControllerMapper.class);


  CharacterGrindRankDTO toCharacterGrindRankDTO(CharacterGrindRank characterGrindRank);

  List<CharacterGrindRankDTO> toCharacterGrindRankDTOList(
      List<CharacterGrindRank> characterGrindRankList);


}
