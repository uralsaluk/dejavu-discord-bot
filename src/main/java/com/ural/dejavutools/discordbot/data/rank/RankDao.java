package com.ural.dejavutools.discordbot.data.rank;

import com.ural.dejavutools.discordbot.data.rank.document.RankDocument;
import java.util.List;

public interface RankDao {

  void saveRankHistory(List<RankDocument> rankDocuments);

  List<RankDocument> findPreviousRecordsByDay(Integer minusDays);


  void saveDailyRank(List<RankDocument> rankDocuments);

  void saveWeeklyRank(List<RankDocument> rankDocuments);

  List<RankDocument> getDailyRank();

  List<RankDocument> getWeeklyRank();

}
