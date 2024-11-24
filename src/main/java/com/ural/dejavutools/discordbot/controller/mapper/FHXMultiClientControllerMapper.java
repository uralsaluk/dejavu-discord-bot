package com.ural.dejavutools.discordbot.controller.mapper;

import com.ural.dejavutools.discordbot.controller.model.accountstate.AccountStateRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkandloglogin.CheckAndLogLoginRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkandloglogin.CheckAndLogLoginRestResponse;
import com.ural.dejavutools.discordbot.controller.model.checkkey.CheckKeyRestRequest;
import com.ural.dejavutools.discordbot.controller.model.checkkey.CheckKeyRestResponse;
import com.ural.dejavutools.discordbot.controller.model.currentVersion.CurrentVersionRestResponse;
import com.ural.dejavutools.discordbot.controller.model.errorlogger.ErrorLoggerRestRequest;
import com.ural.dejavutools.discordbot.service.multiclient.model.accountstate.AccountStateRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkkey.CheckKeyResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger.ErrorLoggerRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.version.MultiClientVersionDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface FHXMultiClientControllerMapper {

  FHXMultiClientControllerMapper MAPPER = Mappers.getMapper(FHXMultiClientControllerMapper.class);


  CheckKeyRestResponse toCheckKeyRestResponse(CheckKeyResponseDTO checkKeyResponseDTO);

  CheckKeyRequestDTO toCheckKeyRequestDTO(CheckKeyRestRequest request);


  CheckAndLogLoginRequestDTO toCheckAndLogLoginRequestDTO(
      CheckAndLogLoginRestRequest checkAndLogLoginRestRequest);


  CheckAndLogLoginRestResponse toCheckAndLogLoginRestResponse(
      CheckAndLogLoginResponseDTO checkAndLogLoginResponseDTO);

  AccountStateRequestDTO toAccountStateRequestDTO(AccountStateRestRequest accountStateRestRequest);

  CurrentVersionRestResponse toCurrentVersionRestResponse(
      MultiClientVersionDTO multiClientVersionDTO);

  ErrorLoggerRequestDTO toErrorLoggerRequestDTO(ErrorLoggerRestRequest errorLoggerRestRequest);

}
