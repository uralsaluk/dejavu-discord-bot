package com.ural.dejavutools.discordbot.data.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;

public interface MultiClientKeyDao {

  MultiClientKeyDocument saveMultiClientKey(MultiClientKeyDocument document);

  MultiClientKeyDocument findKeyByUserId(String discordUserId);


  MultiClientKeyDocument findKeyByKeyId(String keyId);

}
