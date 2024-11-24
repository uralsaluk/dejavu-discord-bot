package com.ural.dejavutools.discordbot.service.boss;

@Deprecated
public class ElcavenOptionsHandler {
/*

  @Override
  public void commandEventHandler(SlashCommandInteractionEvent event) {

    OptionMapping messageOption = event.getOption("option");
    String message = messageOption.getAsString();

    OptionMapping dropOption = event.getOption("drop");

    if (message.equals("dead")) {

      Elcaven elcavenRecord = new Elcaven(
          event.getUser().getName(), SpawnConstants.ELCAVEN_HOUR,
          SpawnConstants.ELCAVEN_MINUTES
      );
      if (dropOption != null) {

        elcavenRecord.setDrop(dropOption.getAsAttachment());
      }

      InMemoryData.ELCAVEN_LIST.add(elcavenRecord);
      Elcaven elc = InMemoryData.ELCAVEN_LIST.
          getLast();

      event.reply(elc.getDescription())
          .queue();

      //      event.getHook().sendMessage(attachment.getId()).queue();

      //       event.getHook().sendMessage().setFiles()
    } else if (message.equals("info")) {

      if (InMemoryData.ELCAVEN_LIST.isEmpty()) {
        event.reply("No Record Found").queue();

      } else {
        event.reply(InMemoryData.ELCAVEN_LIST.getLast().getSpawnDescription()).queue();
      }
    } else if (message.equals("remove last")) {

      if (InMemoryData.ELCAVEN_LIST.isEmpty()) {
        event.reply("No Record Found").queue();

      } else {

        InMemoryData.ELCAVEN_LIST.removeLast();
        event.reply("Last record removed by :" + event.getUser().getName()).queue();
      }
    }


  }*/
}
