package com.ural.dejavutools.discordbot.service.multiclient;

import com.ural.dejavutools.discordbot.service.multiclient.model.DownloadDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.accountstate.AccountStateRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger.ErrorLoggerRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.version.MultiClientVersionDTO;

public interface MultiClientService {

  CheckAndLogLoginResponseDTO checkAndLogLogin(
      CheckAndLogLoginRequestDTO checkAndLogLoginRequestDTO);


  void accountState(AccountStateRequestDTO requestDTO);

  DownloadDTO getDownloadFile();

  DownloadDTO downloadClient();

  DownloadDTO downloadPatcher();

  MultiClientVersionDTO getCurrentVersion();

  void errorLogger(ErrorLoggerRequestDTO requestDTO);


}
