package com.ural.dejavutools.discordbot.data.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.LoginAudit;

public interface LoginAuditDao {


  void error(LoginAudit loginAudit);

  void success(LoginAudit loginAudit);

  void alreadyOnline(LoginAudit loginAudit);

  void logOut(LoginAudit loginAudit);

}
