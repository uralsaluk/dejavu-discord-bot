package com.ural.dejavutools.discordbot.service.util;

public class MultiClientFileUtil {

  public static String getFullFileName(String name, String fileType) {

    StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(name);
    fileNameBuilder.append(".");
    fileNameBuilder.append(fileType);

    return fileNameBuilder.toString();

  }

}
