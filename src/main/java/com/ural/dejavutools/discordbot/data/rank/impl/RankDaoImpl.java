package com.ural.dejavutools.discordbot.data.rank.impl;

import com.ural.dejavutools.discordbot.data.constant.CollectionConstants;
import com.ural.dejavutools.discordbot.data.rank.RankDao;
import com.ural.dejavutools.discordbot.data.rank.document.RankDocument;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RankDaoImpl implements RankDao {

  private MongoTemplate mongoTemplate;

  public RankDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void saveRankHistory(List<RankDocument> rankDocuments) {

    rankDocuments.forEach(x ->
    {
      Criteria criteria = new Criteria();
      criteria.and("dateOfRecord").is(LocalDate.now());
      criteria.and("name").is(x.getName());
      Query query = new Query(criteria);

      RankDocument oldDocument = mongoTemplate.findOne(query, RankDocument.class,
          CollectionConstants.FHX_RANKING_HISTORY);
      if (Objects.isNull(oldDocument)) {

        mongoTemplate.save(x, CollectionConstants.FHX_RANKING_HISTORY);
      }

    });

    //mongoTemplate.insertAll(rankDocuments);

  }

  @Override
  public List<RankDocument> findPreviousRecordsByDay(Integer minusDays) {
    Query query = new Query();
    // query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(1)));
    query.addCriteria(Criteria.where("dateOfRecord").is(LocalDate.now().minusDays(minusDays)));

    return mongoTemplate.find(query, RankDocument.class, CollectionConstants.FHX_RANKING_HISTORY);
  }

  @Override
  public void saveDailyRank(List<RankDocument> rankDocuments) {

    mongoTemplate.remove(new Query(), CollectionConstants.FHX_DAILY_RANK);
    mongoTemplate.insert(rankDocuments, CollectionConstants.FHX_DAILY_RANK);

  }

  @Override
  public void saveWeeklyRank(List<RankDocument> rankDocuments) {

    mongoTemplate.remove(new Query(), CollectionConstants.FHX_WEEKLY_RANK);
    mongoTemplate.insert(rankDocuments, CollectionConstants.FHX_WEEKLY_RANK);

  }

  @Override
  public List<RankDocument> getDailyRank() {
    Query query = new Query();
    query.with(Sort.by(Direction.DESC, "experience"));
    return mongoTemplate.find(query, RankDocument.class, CollectionConstants.FHX_DAILY_RANK);
  }

  @Override
  public List<RankDocument> getWeeklyRank() {
    Query query = new Query();
    query.with(Sort.by(Direction.DESC, "experience"));
    return mongoTemplate.find(query, RankDocument.class, CollectionConstants.FHX_WEEKLY_RANK);
  }
}
