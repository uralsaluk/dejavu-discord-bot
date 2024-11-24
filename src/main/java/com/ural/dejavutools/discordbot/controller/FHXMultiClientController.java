package com.ural.dejavutools.discordbot.controller;

import com.ural.dejavutools.discordbot.controller.mapper.FHXMultiClientControllerMapper;
import com.ural.dejavutools.discordbot.controller.model.accountstate.AccountStateRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkandloglogin.CheckAndLogLoginRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkandloglogin.CheckAndLogLoginRestResponse;
import com.ural.dejavutools.discordbot.controller.model.checkkey.CheckKeyRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkkey.CheckKeyRestResponse;
import com.ural.dejavutools.discordbot.controller.model.currentVersion.CurrentVersionRestResponse;
import com.ural.dejavutools.discordbot.controller.model.errorlogger.ErrorLoggerRestRequest;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientKeyService;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientService;
import com.ural.dejavutools.discordbot.service.multiclient.model.DownloadDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.accountstate.AccountStateRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger.ErrorLoggerRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.version.MultiClientVersionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/multiClient")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false", exposedHeaders = "X-CSRF-TOKEN")
public class FHXMultiClientController {


  private static final FHXMultiClientControllerMapper mapper = FHXMultiClientControllerMapper.MAPPER;
  private MultiClientKeyService multiClientKeyService;

  private MultiClientService multiClientService;

  public FHXMultiClientController(MultiClientKeyService multiClientKeyService,
      MultiClientService multiClientService) {
    this.multiClientKeyService = multiClientKeyService;
    this.multiClientService = multiClientService;
  }

  @RequestMapping(value = "checkKey", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public ResponseEntity<CheckKeyRestResponse> checkKey(HttpServletRequest servletRequest,
      @RequestBody CheckKeyRestRequest checkKeyRestRequest) {

    CheckKeyRequestDTO checkKeyRequestDTO = mapper.toCheckKeyRequestDTO(checkKeyRestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    checkKeyRequestDTO.setUserIp(ipAddress);

    CheckKeyResponseDTO checkKeyResponseDTO = multiClientKeyService.checkKey(checkKeyRequestDTO);

    CheckKeyRestResponse checkKeyRestResponse = mapper.toCheckKeyRestResponse(checkKeyResponseDTO);

    return new ResponseEntity<>(checkKeyRestResponse, HttpStatus.OK);
  }


  @RequestMapping(value = "checkAndLogLogin", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public ResponseEntity<CheckAndLogLoginRestResponse> checkAndLogLogin(
      HttpServletRequest servletRequest,
      @RequestBody CheckAndLogLoginRestRequest checkAndLogLoginRestRequest) {

    CheckAndLogLoginRequestDTO checkAndLogLoginRequestDTO = mapper.toCheckAndLogLoginRequestDTO(
        checkAndLogLoginRestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");

    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    checkAndLogLoginRequestDTO.setUserIp(ipAddress);

    CheckAndLogLoginResponseDTO checkAndLogLoginResponseDTO = multiClientService.checkAndLogLogin(
        checkAndLogLoginRequestDTO);

    CheckAndLogLoginRestResponse checkAndLogLoginRestResponse = mapper.toCheckAndLogLoginRestResponse(
        checkAndLogLoginResponseDTO);

    return new ResponseEntity<>(checkAndLogLoginRestResponse, HttpStatus.OK);
  }


  @RequestMapping(value = "accountState", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public void accountState(
      HttpServletRequest servletRequest,
      @RequestBody AccountStateRestRequest accountStateRestRequest) {

    AccountStateRequestDTO accountStateRequestDTO = mapper.toAccountStateRequestDTO(
        accountStateRestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    accountStateRequestDTO.setIp(ipAddress);

    multiClientService.accountState(
        accountStateRequestDTO);

  }

  @RequestMapping(value = "errorLogger", method = {RequestMethod.POST,
      RequestMethod.OPTIONS})
  public void errorLogger(
      HttpServletRequest servletRequest,
      @RequestBody ErrorLoggerRestRequest errorLoggerRestRequest) {

    ErrorLoggerRequestDTO errorLoggerRequestDTO = mapper.toErrorLoggerRequestDTO(
        errorLoggerRestRequest);

    String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = servletRequest.getRemoteAddr();
    }
    errorLoggerRequestDTO.setIp(ipAddress);

    multiClientService.errorLogger(
        errorLoggerRequestDTO);

  }


  @GetMapping("download")
  public ResponseEntity<Resource> download() {

    DownloadDTO downloadFile = multiClientService.getDownloadFile();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + downloadFile.getResource().getFilename() + "\"")
        .header(HttpHeaders.CONTENT_TYPE,
            downloadFile.getContentType() != null ? downloadFile.getContentType()
                : "application/octet-stream")
        .body(downloadFile.getResource());

  }

  @GetMapping("downloadClient")
  public ResponseEntity<Resource> downloadClient() {

    DownloadDTO downloadFile = multiClientService.downloadClient();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + downloadFile.getResource().getFilename() + "\"")
        .header(HttpHeaders.CONTENT_TYPE,
            downloadFile.getContentType() != null ? downloadFile.getContentType()
                : "application/octet-stream")
        .body(downloadFile.getResource());

  }

  @GetMapping("downloadPatcher")
  public ResponseEntity<Resource> downloadPatcher() {

    DownloadDTO downloadFile = multiClientService.downloadPatcher();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + downloadFile.getResource().getFilename() + "\"")
        .header(HttpHeaders.CONTENT_TYPE,
            downloadFile.getContentType() != null ? downloadFile.getContentType()
                : "application/octet-stream")
        .body(downloadFile.getResource());

  }

  @GetMapping("currentVersion")
  public ResponseEntity<CurrentVersionRestResponse> currentVersion() {

    MultiClientVersionDTO currentVersion = multiClientService.getCurrentVersion();

    CurrentVersionRestResponse currentVersionRestResponse = mapper.toCurrentVersionRestResponse(
        currentVersion);

    return new ResponseEntity<>(currentVersionRestResponse, HttpStatus.OK);

  }
}
