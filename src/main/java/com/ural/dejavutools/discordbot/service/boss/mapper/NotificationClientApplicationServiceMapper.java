package com.ural.dejavutools.discordbot.service.boss.mapper;

import com.ural.dejavutools.discordbot.data.boss.document.NotificationKeyDocument;
import com.ural.dejavutools.discordbot.service.boss.model.client.NotificationKey;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationClientApplicationServiceMapper {


  NotificationClientApplicationServiceMapper MAPPER = Mappers.getMapper(
      NotificationClientApplicationServiceMapper.class);


  NotificationKeyDocument toNotificationKeyDocument(NotificationKey notificationKey);
  

}
