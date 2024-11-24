package com.ural.dejavutools.discordbot.controller.model.checkkey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckKeyRestResponse {

  private Boolean success;
  private String message;
}
