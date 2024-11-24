package com.ural.dejavutools.discordbot;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiscordBotApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiscordBotApplication.class, args);
  }

  @PostConstruct
  public void setTimeZone() {

    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Istanbul"));

  }


}
