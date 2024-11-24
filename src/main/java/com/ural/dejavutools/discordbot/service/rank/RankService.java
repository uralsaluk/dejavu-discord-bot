package com.ural.dejavutools.discordbot.service.rank;

import com.ural.dejavutools.discordbot.service.rank.constant.RankType;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterGrindRank;
import java.util.List;

public interface RankService {


  void publishRank(RankType rankType);

  List<CharacterGrindRank> getDailyRank();

  List<CharacterGrindRank> getWeeklyRank();

  void notifyChannel();

}
