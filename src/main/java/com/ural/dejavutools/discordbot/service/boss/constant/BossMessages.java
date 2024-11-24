package com.ural.dejavutools.discordbot.service.boss.constant;

import com.ural.dejavutools.discordbot.data.boss.document.BossType;
import com.ural.dejavutools.discordbot.service.util.DiscordMessageUtils;
import com.ural.dejavutools.discordbot.service.util.DiscordTİmeUtil;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

public class BossMessages {

  public static final String FIRST_MESSAGE = "${bossType} was killed  ${deadTime}  window opened by  ${userId}";
  public static final String LAST_NOTIFICATION_SENT = "Last Notification sent, ${bossType} window is going to close in 1 hour.";
  public static final String ACTIVE_RECORD_REMOVED = "There was an active ${bossType}  record, so it's removed by ${userId}";
  public static final String NO_ACTIVE_RECORD = "There is no active ${bossType}  record";

  //------------

  public static final String LABARK_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder! ${roleId}  about ${deadTime}  from now **LaBark** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String LABARK_LAST_NTF_MESSAGE =
      "\uD83D\uDEA8  **LAST NOTIFICATION**  Reminder! ${roleId}  about ${deadTime}  from now **LaBark** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";
  //---------

  public static final String ELCAVEN_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder! ${roleId}  about ${deadTime}  from now **Belziev(Elcaven)** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String ELCAVEN_LAST_NTF_MESSAGE =
      "\uD83D\uDEA8  **LAST NOTIFICATION** Reminder! ${roleId}    about ${deadTime}  from now **Belziev(Elcaven)** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String LABORC_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder! ${roleId}  about ${deadTime}  from now **LA BORC** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String CULT_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder! ${roleId}  about ${deadTime}  from now **CULT** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";


  public static final String RENEGADE_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder! ${roleId}  about ${deadTime}  from now **RENEGADE** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String RENEGADE_LAST_NTF_MESSAGE =
      "\uD83D\uDEA8  **LAST NOTIFICATION**   Reminder! ${roleId}  about ${deadTime}  from now **RENEGADE** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String OBSIDIAN_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **OBSIDIAN** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/1242825077993635890/1242826331205533827";

  public static final String GHOUL_FIRST_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **GHOUL** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/1242825077993635890/1242826331205533827";


  public static final String LABARK_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **LaBark** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";
  //---------

  public static final String ELCAVEN_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **Belziev(Elcaven)** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String LABORC_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **LA BORC** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String CULT_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **CULT** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String RENEGADE_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **RENEGADE** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please check stream on voice and post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/939806848670896189/1192373546874327081";

  public static final String OBSIDIAN_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **OBSIDIAN** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/1242825077993635890/1242826331205533827";

  public static final String GHOUL_INFO_NTF_MESSAGE =
      "\uD83D\uDEA8  Reminder!   about ${deadTime}  from now **GHOUL** can spawn until latest ${windowTime} from now!  <:DogeSmug:1089429500649017395>  Please post when dead, and don't forget loot rules linked below \n"
          + "\n"
          + "https://discord.com/channels/764564360260616192/1242825077993635890/1242826331205533827";


  public static String getMessageByBossType(BossType bossType, String message,
      @Nullable String userId) {

    String bossTypeReplaced = StringUtils.replace(message, "${bossType}", bossType.toString());

    if (StringUtils.isNotEmpty(userId)) {

      return StringUtils.replace(bossTypeReplaced, "${userId}",
          DiscordMessageUtils.getUserAnnotationText(userId));

    }

    return bossTypeReplaced;

  }

  public static String getMessageByBossType(BossType bossType, String message) {

    return StringUtils.replace(message, "${bossType}", bossType.toString());

  }

  public static String getFirstMessage(BossType bossType, OffsetDateTime relativeTime,
      String userId) {

    String bossTypeReplaced = StringUtils.replace(FIRST_MESSAGE, "${bossType}",
        bossType.toString());

    String deadTimeReplaced = StringUtils.replace(bossTypeReplaced, "${deadTime}",
        DiscordTİmeUtil.getRelativeTime(relativeTime));

    String userReplaced = StringUtils.replace(deadTimeReplaced, "${userId}",
        DiscordMessageUtils.getUserAnnotationText(userId));

    return userReplaced;

  }


  public static String updateRoleTag(String message, String roleId) {

    String userReplaced = StringUtils.replace(message, "${roleId}",
        DiscordMessageUtils.getRoleAnnotationText(roleId));

    return userReplaced;

  }


}
