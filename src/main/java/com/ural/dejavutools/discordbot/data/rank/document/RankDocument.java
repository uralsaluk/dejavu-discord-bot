package com.ural.dejavutools.discordbot.data.rank.document;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Data
public class RankDocument {

  @Id
  @Field("_id")
  private ObjectId id;
  private LocalDate dateOfRecord;
  private String name;
  private Integer level;
  private Long experience;
  private String className;
  private String guildName;

}
