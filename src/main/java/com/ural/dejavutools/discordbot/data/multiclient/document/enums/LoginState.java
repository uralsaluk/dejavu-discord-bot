package com.ural.dejavutools.discordbot.data.multiclient.document.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum LoginState {

  SUCCESS,
  FAIL,
  ALREADY_ONLINE,
  
  LOGOUT;
}
