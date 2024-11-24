package com.ural.dejavutools.discordbot.service.multiclient.impl;

import com.ural.dejavutools.discordbot.data.multiclient.ActiveAccountDao;
import com.ural.dejavutools.discordbot.data.multiclient.ErrorLogDao;
import com.ural.dejavutools.discordbot.data.multiclient.LoginAuditDao;
import com.ural.dejavutools.discordbot.data.multiclient.MultiClientKeyDao;
import com.ural.dejavutools.discordbot.data.multiclient.document.ActiveAccountDocument;
import com.ural.dejavutools.discordbot.data.multiclient.document.ErrorLogDocument;
import com.ural.dejavutools.discordbot.data.multiclient.document.LoginAudit;
import com.ural.dejavutools.discordbot.data.multiclient.document.MultiClientKeyDocument;
import com.ural.dejavutools.discordbot.service.multiclient.DiscordNotifyService;
import com.ural.dejavutools.discordbot.service.multiclient.MultiClientService;
import com.ural.dejavutools.discordbot.service.multiclient.configuration.DejavuMultiClientConfiguration;
import com.ural.dejavutools.discordbot.service.multiclient.constant.DiscordErrorMessages;
import com.ural.dejavutools.discordbot.service.multiclient.constant.ErrorMessages;
import com.ural.dejavutools.discordbot.service.multiclient.mapper.MultiClientServiceMapper;
import com.ural.dejavutools.discordbot.service.multiclient.model.DownloadDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.accountstate.AccountStateRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.checkandlogin.CheckAndLogLoginResponseDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.errorlogger.ErrorLoggerRequestDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.version.MultiClientVersionDTO;
import com.ural.dejavutools.discordbot.service.util.MultiClientFileUtil;
import com.ural.dejavutools.discordbot.service.util.TimeRangeUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import net.dv8tion.jda.api.JDA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MultiClientServiceImpl implements MultiClientService {

  private static final MultiClientServiceMapper mapper = MultiClientServiceMapper.MAPPER;

  private final MultiClientKeyDao multiClientKeyDao;

  private final DejavuMultiClientConfiguration dejavuMultiClientConfiguration;

  private final ActiveAccountDao activeAccountDao;

  private final LoginAuditDao loginAuditDao;

  private final ErrorLogDao errorLogDao;

  private final DiscordNotifyService discordNotifyService;

  private final JDA jda;

  private final Path fileStorageLocation;


  public MultiClientServiceImpl(@Qualifier(value = "DejaMultiClient") JDA jda,
      MultiClientKeyDao multiClientKeyDao,
      DejavuMultiClientConfiguration dejavuMultiClientConfiguration,
      ActiveAccountDao activeAccountDao, LoginAuditDao loginAuditDao, ErrorLogDao errorLogDao,
      DiscordNotifyService discordNotifyService) {
    this.multiClientKeyDao = multiClientKeyDao;
    this.jda = jda;
    this.dejavuMultiClientConfiguration = dejavuMultiClientConfiguration;
    this.activeAccountDao = activeAccountDao;
    this.loginAuditDao = loginAuditDao;
    this.errorLogDao = errorLogDao;
    this.discordNotifyService = discordNotifyService;
    this.fileStorageLocation = Paths.get(".").toAbsolutePath().normalize();
  }


  public CheckAndLogLoginResponseDTO checkAndLogLogin(
      CheckAndLogLoginRequestDTO checkAndLogLoginRequestDTO) {

    MultiClientKeyDocument keyByKeyId = multiClientKeyDao.findKeyByKeyId(
        checkAndLogLoginRequestDTO.getMultiClientKey());

    if (Objects.isNull(keyByKeyId)) {

      return new CheckAndLogLoginResponseDTO(false, ErrorMessages.NO_KEY_FOUND,
          ErrorMessages.NO_KEY_FOUND_CODE);
    }

    if (!checkAndLogLoginRequestDTO.getLoginSuccess()) {

      loginAuditDao.error(buildLoginAudit(checkAndLogLoginRequestDTO, keyByKeyId));
      discordNotifyService.sendMessage(checkAndLogLoginRequestDTO.getTransactionId(),
          keyByKeyId.getDiscordUserId(), DiscordErrorMessages.LOGIN_ERROR,
          checkAndLogLoginRequestDTO);

      return new CheckAndLogLoginResponseDTO(false, ErrorMessages.LOGIN_ERROR,
          ErrorMessages.LOGIN_ERROR_CODE);
    }

    ActiveAccountDocument oneByFHXId = activeAccountDao.findOneByFHXId(
        checkAndLogLoginRequestDTO.getFhxId());

    if (checkUserAlreadyOnline(oneByFHXId)) {

      if (checkAndLogLoginRequestDTO.getForceLogin()) {

        discordNotifyService.sendMessage(checkAndLogLoginRequestDTO.getTransactionId(),
            keyByKeyId.getDiscordUserId(), DiscordErrorMessages.getMessageWithUserId(
                DiscordErrorMessages.LOGIN_SUCCESS_WITH_FORCE_LOGIN, oneByFHXId.getDiscordUserId()),
            checkAndLogLoginRequestDTO);

        discordNotifyService.sendMessage(checkAndLogLoginRequestDTO.getTransactionId(),
            oneByFHXId.getDiscordUserId(), DiscordErrorMessages.getMessageWithUserId(
                DiscordErrorMessages.DISCONNECTED_BY_OTHER_PLAYER, keyByKeyId.getDiscordUserId()),
            checkAndLogLoginRequestDTO);

      } else {

        LoginAudit alreadyOnlineDocument = buildLoginAudit(checkAndLogLoginRequestDTO, keyByKeyId);
        alreadyOnlineDocument.setAlreadyOnlineDocument(oneByFHXId);
        loginAuditDao.alreadyOnline(alreadyOnlineDocument);
        discordNotifyService.sendMessage(checkAndLogLoginRequestDTO.getTransactionId(),
            keyByKeyId.getDiscordUserId(), DiscordErrorMessages.LOGIN_ACCOUNT_ALREADY_ONLINE,
            checkAndLogLoginRequestDTO);

        return new CheckAndLogLoginResponseDTO(false,
            ErrorMessages.LOGIN_ACCOUNT_ALREADY_ONLINE,
            ErrorMessages.LOGIN_ACCOUNT_ALREADY_ONLINE_CODE);
      }

    }

    loginAuditDao.success(buildLoginAudit(checkAndLogLoginRequestDTO, keyByKeyId));

    addNullSafeFhxId(keyByKeyId, checkAndLogLoginRequestDTO.getFhxId());

    return new CheckAndLogLoginResponseDTO(true, "Success", "Success");

  }


  @Override
  public void accountState(AccountStateRequestDTO requestDTO) {

    MultiClientKeyDocument keyByKeyId = multiClientKeyDao.findKeyByKeyId(
        requestDTO.getMultiClientKey());

    switch (requestDTO.getAction()) {

      case ONLINE -> {

        ActiveAccountDocument activeAccountDocument = activeAccountDao.findOneByFHXId(
            requestDTO.getFhxId());

        if (Objects.isNull(activeAccountDocument)) {

          ActiveAccountDocument newActiveAccount = buildActiveAccountDocument(keyByKeyId,
              requestDTO);
          activeAccountDao.save(newActiveAccount);

        } else {

          activeAccountDocument.setIp(requestDTO.getIp());
          activeAccountDocument.setMultiClientKey(requestDTO.getMultiClientKey());
          activeAccountDocument.setDiscordUserName(keyByKeyId.getDiscordUserName());
          activeAccountDocument.setDiscordUserId(keyByKeyId.getDiscordUserId());
          activeAccountDao.save(activeAccountDocument);

        }

        break;
      }
      case OFFLINE -> {
        activeAccountDao.delete(requestDTO.getFhxId());
        loginAuditDao.logOut(buildLoginAudit(requestDTO, keyByKeyId));

      }

      default -> System.out.println("asd");

    }

  }

  @Override
  public DownloadDTO getDownloadFile() {
    try {
      Path filePath = this.fileStorageLocation.resolve(
          dejavuMultiClientConfiguration.getDownloadFileName()).normalize();

      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        String contentType = Files.probeContentType(filePath);

        return new DownloadDTO(resource, contentType);
      } else {
        throw new RuntimeException("File not found or not readable: "
            + dejavuMultiClientConfiguration.getDownloadFileName());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DownloadDTO downloadClient() {

    String fileName = MultiClientFileUtil.getFullFileName(
        dejavuMultiClientConfiguration.getClientName(),
        "exe");
    try {
      Path filePath = this.fileStorageLocation.resolve(
          fileName).normalize();

      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        String contentType = Files.probeContentType(filePath);

        return new DownloadDTO(resource, contentType);
      } else {
        throw new RuntimeException("File not found or not readable: "
            + fileName);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DownloadDTO downloadPatcher() {

    try {
      Path filePath = this.fileStorageLocation.resolve(
          dejavuMultiClientConfiguration.getPatcherName()).normalize();

      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        String contentType = Files.probeContentType(filePath);

        return new DownloadDTO(resource, contentType);
      } else {
        throw new RuntimeException("File not found or not readable: "
            + dejavuMultiClientConfiguration.getPatcherName());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public MultiClientVersionDTO getCurrentVersion() {
    String fileName = MultiClientFileUtil.getFullFileName(
        dejavuMultiClientConfiguration.getClientName(),
        "exe");

    StringBuilder updateMessageBuilder = new StringBuilder();
    updateMessageBuilder.append(fileName);
    updateMessageBuilder.append(" successfully downloaded \n ");
    updateMessageBuilder.append(dejavuMultiClientConfiguration.getLastUpdates());

    return new MultiClientVersionDTO(dejavuMultiClientConfiguration.getMultiClientVersion(),
        updateMessageBuilder.toString()
    );
  }

  @Override
  public void errorLogger(ErrorLoggerRequestDTO requestDTO) {

    ErrorLogDocument errorLogDocument = mapper.toErrorLogDocument(requestDTO);

    if (!Objects.isNull(requestDTO.getMultiClientKey())) {
      MultiClientKeyDocument keyByKeyId = multiClientKeyDao.findKeyByKeyId(
          requestDTO.getMultiClientKey());

      if (!Objects.isNull(keyByKeyId)) {
        errorLogDocument.setDiscordUserId(keyByKeyId.getDiscordUserId());
        errorLogDocument.setDiscordUserName(keyByKeyId.getDiscordUserName());

      }

    }

    errorLogDao.saveErrorLog(errorLogDocument);


  }


  private void addNullSafeFhxId(MultiClientKeyDocument keyByKeyId, String fhxId) {

    if (CollectionUtils.isEmpty(keyByKeyId.getIds())) {

      keyByKeyId.setIds(new ArrayList<>());
    }

    if (!keyByKeyId.getIds().stream().anyMatch(id -> StringUtils.equalsIgnoreCase(id, fhxId))) {
      keyByKeyId.getIds().add(fhxId);
      multiClientKeyDao.saveMultiClientKey(keyByKeyId);
    }


  }


  private Boolean checkUserAlreadyOnline(ActiveAccountDocument oneByFHXId) {

    if (dejavuMultiClientConfiguration.getCheckUserOnline() && !Objects.isNull(oneByFHXId)
        && !oneByFHXId.getOpenToDisconnect()
        && TimeRangeUtils.checkTimeInSecondsRange(oneByFHXId.getLastUpdate(), 180L)) {

      return true;
    }

    return false;
  }


  private LoginAudit buildLoginAudit(CheckAndLogLoginRequestDTO checkAndLogLoginRequestDTO,
      MultiClientKeyDocument keyByKeyId) {

    LoginAudit loginAudit = new LoginAudit();
    loginAudit.setIp(checkAndLogLoginRequestDTO.getUserIp());
    loginAudit.setMultiClientKey(keyByKeyId.getKey());
    loginAudit.setFhxId(checkAndLogLoginRequestDTO.getFhxId());
    loginAudit.setDiscordUserId(keyByKeyId.getDiscordUserId());
    loginAudit.setDiscordUserName(keyByKeyId.getDiscordUserName());
    loginAudit.setTransactionId(checkAndLogLoginRequestDTO.getTransactionId());
    loginAudit.setLoginMessage(checkAndLogLoginRequestDTO.getLoginMessage());

    return loginAudit;

  }

  private LoginAudit buildLoginAudit(AccountStateRequestDTO requestDTO,
      MultiClientKeyDocument keyByKeyId) {

    LoginAudit loginAudit = new LoginAudit();
    loginAudit.setIp(requestDTO.getIp());
    loginAudit.setMultiClientKey(keyByKeyId.getKey());
    loginAudit.setFhxId(requestDTO.getFhxId());
    loginAudit.setDiscordUserId(keyByKeyId.getDiscordUserId());
    loginAudit.setDiscordUserName(keyByKeyId.getDiscordUserName());
    loginAudit.setTransactionId(requestDTO.getTransactionId());
    loginAudit.setLoginMessage("Disconnected");

    return loginAudit;

  }

  private ActiveAccountDocument buildActiveAccountDocument(MultiClientKeyDocument keyByKeyId,
      AccountStateRequestDTO requestDTO) {

    ActiveAccountDocument activeAccountDocument = new ActiveAccountDocument();
    activeAccountDocument.setIp(requestDTO.getIp());
    activeAccountDocument.setMultiClientKey(keyByKeyId.getKey());
    activeAccountDocument.setFhxId(requestDTO.getFhxId());
    activeAccountDocument.setDiscordUserId(keyByKeyId.getDiscordUserId());
    activeAccountDocument.setDiscordUserName(keyByKeyId.getDiscordUserName());
    activeAccountDocument.setOpenToDisconnect(false);
    activeAccountDocument.setCreateTime(LocalDateTime.now());
    return activeAccountDocument;

  }

}
