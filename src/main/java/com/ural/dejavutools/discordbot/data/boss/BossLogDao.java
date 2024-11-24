package com.ural.dejavutools.discordbot.data.boss;

import com.ural.dejavutools.discordbot.data.boss.document.BossDocument;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;

public interface BossLogDao {

  void saveBossRecord(BossDocument t);

  List<BossDocument> getActiveRecord();

  List<BossDocument> getByFilter(Query query);

  BossDocument findOneByFilter(Query query);

  void disableNotificationsByQuery(Query query);

}
