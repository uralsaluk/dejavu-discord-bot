package com.ural.dejavutools.discordbot.controller.mapper;

import com.ural.dejavutools.discordbot.controller.model.boss.checkkey.BossCheckKeyRestRequest;
import com.ural.dejavutools.discordbot.controller.model.boss.checkkey.BossCheckKeyRestResponse;
import com.ural.dejavutools.discordbot.controller.model.boss.spawnhandler.SpawnHandlerRestRequest;
import com.ural.dejavutools.discordbot.controller.model.boss.spawnhandler.SpawnHandlerRestResponse;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface FHXBossControllerMapper {


  FHXBossControllerMapper MAPPER = Mappers.getMapper(FHXBossControllerMapper.class);

  CheckNotificationClientKeyRequestDTO toCheckNotificationClientKeyRequestDTO(
      BossCheckKeyRestRequest bossCheckKeyRestRequest);

  BossCheckKeyRestResponse toBossCheckKeyRestResponse(
      CheckNotificationClientKeyResponseDTO responseDTO);

  SpawnHandlerRequestDTO toSpawnHandlerRequestDTO(SpawnHandlerRestRequest spawnHandlerRestRequest);

  SpawnHandlerRestResponse toSpawnHandlerRestResponse(
      SpawnHandlerResponseDTO spawnHandlerResponseDTO);

}
