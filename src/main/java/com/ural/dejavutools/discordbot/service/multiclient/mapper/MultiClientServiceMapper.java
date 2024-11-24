package com.ural.dejavutools.discordbot.service.multiclient.mapper;

import com.ural.dejavutools.discordbot.data.multiclient.document.ErrorLogDocument;
import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;
import com.ural.dejavutools.discordbot.service.multiclient.model.MultiClientKey;
import com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger.ErrorLoggerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MultiClientServiceMapper {


  MultiClientServiceMapper MAPPER = Mappers.getMapper(MultiClientServiceMapper.class);


  MultiClientKeyDocument toMultiClientKeyDocument(MultiClientKey multiClientKey);

  ErrorLogDocument toErrorLogDocument(ErrorLoggerRequestDTO requestDTO);

}
