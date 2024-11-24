package com.ural.dejavutools.discordbot.controller.intercepter;

import com.ural.dejavutools.discordbot.controller.model.BaseResponse;
import com.ural.dejavutools.discordbot.controller.model.ResultResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class BodyModifierAdvice implements ResponseBodyAdvice<Object> {


  public BodyModifierAdvice() {
  }

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {

    try {
      BaseResponse responseObj = (BaseResponse) body;
      if (responseObj.getResult() != null && !StringUtils.isEmpty(
          responseObj.getResult().getErrorCode())) {
        return responseObj;
      } else {
        responseObj.setResult(new ResultResponse());
        return responseObj;
      }
    } catch (Exception e) {
      return body;
    }
  }
}
