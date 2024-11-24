package com.ural.dejavutools.discordbot.data.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.ErrorLogDocument;

public interface ErrorLogDao {

  ErrorLogDocument saveErrorLog(ErrorLogDocument document);

}
