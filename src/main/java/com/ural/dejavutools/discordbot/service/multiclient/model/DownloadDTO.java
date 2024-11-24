package com.ural.dejavutools.discordbot.service.multiclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadDTO {

  private Resource resource;
  private String contentType;

}
