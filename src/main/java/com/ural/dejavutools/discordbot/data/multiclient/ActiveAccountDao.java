package com.ural.dejavutools.discordbot.data.multiclient;

import com.ural.dejavutools.discordbot.data.multiclient.document.ActiveAccountDocument;
import org.springframework.data.mongodb.core.query.Query;

public interface ActiveAccountDao {


  ActiveAccountDocument findOneByFHXId(String fhxId);

  void save(ActiveAccountDocument document);

  void delete(String fhxId);

  void delete(Query query);

}
