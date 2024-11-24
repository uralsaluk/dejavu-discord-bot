package com.ural.dejavutools.discordbot.controller;


import com.ural.dejavutools.discordbot.controller.mapper.FHXBossControllerMapper;
import com.ural.dejavutools.discordbot.controller.model.boss.checkkey.BossCheckKeyRestRequest;
import com.ural.dejavutools.discordbot.controller.model.boss.checkkey.BossCheckKeyRestResponse;
import com.ural.dejavutools.discordbot.controller.model.boss.spawnhandler.SpawnHandlerRestRequest;
import com.ural.dejavutools.discordbot.controller.model.boss.spawnhandler.SpawnHandlerRestResponse;
import com.ural.dejavutools.discordbot.service.boss.bosshandler.client.ClientApplicationService;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.CheckNotificationClientKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerRequestDTO;
import com.ural.dejavutools.discordbot.service.boss.model.client.SpawnHandlerResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/bossNotification")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false", exposedHeaders = "X-CSRF-TOKEN")
public class FHXBossController {


  private final ClientApplicationService clientApplicationService;

  public FHXBossController(ClientApplicationService clientApplicationService) {
    this.clientApplicationService = clientApplicationService;
  }

  private static final FHXBossControllerMapper mapper = FHXBossControllerMapper.MAPPER;


  @RequestMapping(value = "checkKey", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public ResponseEntity<BossCheckKeyRestResponse> checkKey(HttpServletRequest servletRequest,
      @RequestBody BossCheckKeyRestRequest bossCheckKeyRestRequestRequest) {

    CheckNotificationClientKeyRequestDTO checkNotificationClientKeyRequestDTO = mapper.toCheckNotificationClientKeyRequestDTO(
        bossCheckKeyRestRequestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    checkNotificationClientKeyRequestDTO.setUserIp(ipAddress);

    CheckNotificationClientKeyResponseDTO responseDTO = clientApplicationService.checkKey(
        checkNotificationClientKeyRequestDTO);

    BossCheckKeyRestResponse bossCheckKeyRestResponse = mapper.toBossCheckKeyRestResponse(
        responseDTO);

    return new ResponseEntity<>(bossCheckKeyRestResponse, HttpStatus.OK);
  }


  @RequestMapping(value = "spawnHandler", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public ResponseEntity<SpawnHandlerRestResponse> spawnHandler(HttpServletRequest servletRequest,
      @RequestBody SpawnHandlerRestRequest spawnHandlerRestRequest) {

    SpawnHandlerRequestDTO spawnHandlerRequestDTO = mapper.toSpawnHandlerRequestDTO(
        spawnHandlerRestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    spawnHandlerRequestDTO.setUserIp(ipAddress);

    SpawnHandlerResponseDTO spawnHandlerResponseDTO = clientApplicationService.spawnHandler(
        spawnHandlerRequestDTO);

    SpawnHandlerRestResponse spawnHandlerRestResponse = mapper.toSpawnHandlerRestResponse(
        spawnHandlerResponseDTO);

    return new ResponseEntity<>(spawnHandlerRestResponse, HttpStatus.OK);
  }


}
