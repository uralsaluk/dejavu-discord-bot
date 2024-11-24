package com.ural.dejavutools.discordbot.service.rank.restclient;

import com.ural.dejavutools.discordbot.service.rank.restclient.model.FHXRankClientRequestDTO;
import com.ural.dejavutools.discordbot.service.rank.restclient.model.FHXRankClientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FHXRankClient {


  public FHXRankClientResponseDTO getRank(FHXRankClientRequestDTO requestDTO) {

    RestClient restClient = RestClient.builder().baseUrl("https://api.fhxrestoration.com/v1/")
        .build();

    ResponseEntity<FHXRankClientResponseDTO> fhxRankClientResponseDTOResponseEntity = restClient.get()
        .uri(uriBuilder -> uriBuilder.path("characterRankings/")
            .queryParam("limit", 100)
            .queryParam("className", requestDTO.getClassName()).build())
        .retrieve()
        .toEntity(FHXRankClientResponseDTO.class);

    return fhxRankClientResponseDTOResponseEntity.getBody();


  }

}
