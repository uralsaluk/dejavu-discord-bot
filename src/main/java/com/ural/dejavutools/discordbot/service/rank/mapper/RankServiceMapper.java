package com.ural.dejavutools.discordbot.service.rank.mapper;

import com.ural.dejavutools.discordbot.data.rank.document.RankDocument;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterGrindRank;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterInfo;
import java.time.LocalDate;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring", imports = {
    LocalDate.class})
public interface RankServiceMapper {

  RankServiceMapper MAPPER = Mappers.getMapper(RankServiceMapper.class);

  @Mapping(target = "dateOfRecord", expression = "java(LocalDate.now())")
  RankDocument toRankDocument(CharacterInfo characterInfo);

  List<RankDocument> toRankDocumentList(List<CharacterInfo> characterInfoList);

  CharacterInfo toCharacterInfo(RankDocument characterInfo);

  List<CharacterInfo> toCharacterInfoList(List<RankDocument> characterInfoList);

  CharacterGrindRank toCharacterGrindRank(RankDocument document);

  List<CharacterGrindRank> toCharacterGrindRankList(List<RankDocument> documentList);

  @Mapping(target = "dateOfRecord", expression = "java(LocalDate.now())")
  RankDocument grindRankToRankDocument(CharacterGrindRank characterGrindRank);

  List<RankDocument> grindRankToRankDocumentList(List<CharacterGrindRank> characterGrindRank);
}
