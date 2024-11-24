package com.ural.dejavutools.discordbot.controller.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (request.getHeader("notificationKey") == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required header is missing");
      return false;
    }
    return true;
  }
}
