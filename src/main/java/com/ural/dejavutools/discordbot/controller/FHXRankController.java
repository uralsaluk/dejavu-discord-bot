package com.ural.dejavutools.discordbot.controller;

import com.ural.dejavutools.discordbot.controller.mapper.FHXRankControllerMapper;
import com.ural.dejavutools.discordbot.controller.model.rank.CharacterGrindRankDTO;
import com.ural.dejavutools.discordbot.controller.model.rank.RankResponseDTO;
import com.ural.dejavutools.discordbot.service.rank.RankService;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterGrindRank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/fhxRank")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false", exposedHeaders = "X-CSRF-TOKEN")
public class FHXRankController {

  private RankService rankService;

  private static final FHXRankControllerMapper mapper = FHXRankControllerMapper.MAPPER;

  public FHXRankController(RankService rankService) {
    this.rankService = rankService;
  }

  @RequestMapping(value = "getDailyRank", method = {RequestMethod.GET,
      RequestMethod.OPTIONS})
  public ResponseEntity<RankResponseDTO> getDailyRank() {

    List<CharacterGrindRank> dailyRank = rankService.getDailyRank();

    List<CharacterGrindRankDTO> characterGrindRankDTOList = mapper.toCharacterGrindRankDTOList(
        dailyRank);

    return new ResponseEntity<>(new RankResponseDTO(characterGrindRankDTOList), HttpStatus.OK);
  }

  @RequestMapping(value = "getWeeklyRank", method = {RequestMethod.GET,
      RequestMethod.OPTIONS})
  public ResponseEntity<RankResponseDTO> getWeeklyRank() {

    List<CharacterGrindRank> dailyRank = rankService.getWeeklyRank();

    List<CharacterGrindRankDTO> characterGrindRankDTOList = mapper.toCharacterGrindRankDTOList(
        dailyRank);

    return new ResponseEntity<>(new RankResponseDTO(characterGrindRankDTOList), HttpStatus.OK);
  }
  /*@RequestMapping(value = "getWeeklyRank", method = {RequestMethod.GET,
      RequestMethod.OPTIONS})
  public ResponseEntity<RankResponseDTO> getWeeklyRank() {

    List<CharacterGrindRank> dailyRank = rankService.getWeeklyRank();

    List<CharacterGrindRankDTO> characterGrindRankDTOList = mapper.toCharacterGrindRankDTOList(
        dailyRank);

    return new ResponseEntity<>(new RankResponseDTO(characterGrindRankDTOList), HttpStatus.OK);
  }*/
}
