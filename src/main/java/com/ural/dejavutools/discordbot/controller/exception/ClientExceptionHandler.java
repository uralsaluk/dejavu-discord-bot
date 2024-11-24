package com.ural.dejavutools.discordbot.controller.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ural.dejavutools.discordbot.controller.model.BaseResponse;
import com.ural.dejavutools.discordbot.controller.model.ResultResponse;
import com.ural.dejavutools.discordbot.controller.model.checkkey.CheckKeyRestResponse;
import com.ural.dejavutools.discordbot.data.boss.NotificationClientLogDao;
import com.ural.dejavutools.discordbot.data.boss.document.LogType;
import com.ural.dejavutools.discordbot.data.boss.document.NotificationClientLogDocument;
import com.ural.dejavutools.discordbot.service.boss.exception.NotificationClientException;
import com.ural.dejavutools.discordbot.service.multiclient.DiscordNotifyService;
import com.ural.dejavutools.discordbot.service.multiclient.exception.MultiClientCheckKeyException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

  private DiscordNotifyService discordNotifyService;

  private NotificationClientLogDao notificationClientLogDao;

  public ClientExceptionHandler(DiscordNotifyService discordNotifyService,
      NotificationClientLogDao notificationClientLogDao) {
    this.discordNotifyService = discordNotifyService;
    this.notificationClientLogDao = notificationClientLogDao;
  }

  @ExceptionHandler(MultiClientCheckKeyException.class)
  public ResponseEntity<CheckKeyRestResponse> handleMultiClientCheckKeyException(
      MultiClientCheckKeyException ex, HttpServletRequest request) {

    if (ex.getSendDiscordNotification()) {
      discordNotifyService.sendMessage(ex.getCheckKeyRequestDTO().getTransactionId(),
          ex.getUserId(), ex.getDiscordMessage(),
          ex.getCheckKeyRequestDTO());
    }

    CheckKeyRestResponse errorResponse = new CheckKeyRestResponse(false, ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.OK);
  }


  @ExceptionHandler(NotificationClientException.class)
  public ResponseEntity<BaseResponse> handleNotificationClientException(
      NotificationClientException ex, HttpServletRequest request) {

    NotificationClientLogDocument notificationClientLogDocument = new NotificationClientLogDocument();
    String ipAddress = request.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = request.getRemoteAddr();
    }
    notificationClientLogDocument.setUserIp(ipAddress);
    notificationClientLogDocument.setKey(ex.getKey());
    notificationClientLogDocument.setNotificationTime(LocalDateTime.now());
    notificationClientLogDocument.setLogType(LogType.ERROR);
    notificationClientLogDocument.setDetail(ex.getErrorMessage());
    notificationClientLogDao.save(notificationClientLogDocument);

    BaseResponse baseResponse = new BaseResponse();
    ResultResponse resultResponse = new ResultResponse(false, ex.getErrorCode(),
        ex.getErrorMessage());
    baseResponse.setResult(resultResponse);
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse> handleValidationException(
      HttpMessageNotReadableException exception) {
    String errorDetails = "";

    if (exception.getCause() instanceof InvalidFormatException) {
      InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
      if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
        errorDetails = String.format(
            "Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
            ifx.getValue(), ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(),
            Arrays.toString(ifx.getTargetType().getEnumConstants()));
      }
    }
    BaseResponse errorResponse = new BaseResponse();

    ResultResponse resultResponse = new ResultResponse(false, exception.getMessage(),
        errorDetails);
    errorResponse.setResult(resultResponse);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
