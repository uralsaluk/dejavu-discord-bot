package com.ural.dejavutools.discordbot.data.multiclient.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.multiclient.LoginAuditDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.LoginAudit;
import com.ural.dejavutools.discordbot.data.multiclient.document.enums.LoginState;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginAuditDaoImpl implements LoginAuditDao {


  private MongoTemplate mongoTemplate;

  public LoginAuditDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void error(LoginAudit loginAudit) {

    loginAudit.setStatus(LoginState.FAIL);
    loginAudit.setLogDate(LocalDateTime.now());

    mongoTemplate.save(loginAudit, CollectionConstants.LOGIN_AUDIT);

  }

  @Override
  public void success(LoginAudit loginAudit) {
    loginAudit.setStatus(LoginState.SUCCESS);
    loginAudit.setLogDate(LocalDateTime.now());

    mongoTemplate.save(loginAudit, CollectionConstants.LOGIN_AUDIT);

  }

  @Override
  public void alreadyOnline(LoginAudit loginAudit) {

    loginAudit.setStatus(LoginState.ALREADY_ONLINE);
    loginAudit.setLogDate(LocalDateTime.now());

    mongoTemplate.save(loginAudit, CollectionConstants.LOGIN_AUDIT);
  }

  @Override
  public void logOut(LoginAudit loginAudit) {

    loginAudit.setStatus(LoginState.LOGOUT);
    loginAudit.setLogDate(LocalDateTime.now());

    mongoTemplate.save(loginAudit, CollectionConstants.LOGIN_AUDIT);

  }
}
