package com.ural.dejavutools.discordbot;

import com.ural.dejavutools.discordbot.data.rank.document.RankDocument;
import com.ural.dejavutools.discordbot.service.boss.constant.BossMessages;
import com.ural.dejavutools.discordbot.service.boss.model.BossEventDTO;
import com.ural.dejavutools.discordbot.service.multiclient.model.accountstate.FHXAction;
import com.ural.dejavutools.discordbot.service.rank.mapper.RankServiceMapper;
import com.ural.dejavutools.discordbot.service.rank.model.CharacterInfo;
import com.ural.dejavutools.discordbot.service.util.CryptoUtils;
import com.ural.dejavutools.discordbot.service.util.DiscordTİmeUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;


class DiscordBotApplicationTests {

  private static final RankServiceMapper mapper = RankServiceMapper.MAPPER;

  @Test
  void contextLoads() {

    OffsetDateTime offsetDateTime = OffsetDateTime.now();
    System.out.println(offsetDateTime);

    String offString = offsetDateTime.toString();
    System.out.println(offString);

    OffsetDateTime off2 = OffsetDateTime.parse(offString);
    System.out.println(off2);
  }

  @Test
  public void messageConverterTest() {

    String relativeTimesOnMessage = DiscordTİmeUtil.getRelativeTimesOnMessage(
        BossMessages.LABARK_FIRST_NTF_MESSAGE, OffsetDateTime.now(), 30000L, 5505L);

    System.out.println(relativeTimesOnMessage);
  }

  @Test
  public void testCheckIfInRange() {

    OffsetDateTime minus5 = OffsetDateTime.now().minusMinutes(5);

    OffsetDateTime now = OffsetDateTime.now();
    System.out.println(
        now.until(minus5, ChronoUnit.SECONDS)
    );

    long between = ChronoUnit.SECONDS.between(now, minus5);

    System.out.println(Math.abs(between));

    Duration duration = Duration.between(now, minus5);

    System.out.println(Math.abs(duration.toSeconds()));

  }

  @Test
  public void test() {

    CharacterInfo characterInfo = new CharacterInfo();
    characterInfo.setClassName("test");
    characterInfo.setExperience(213123123L);

    List<CharacterInfo> characterInfos = Arrays.asList(characterInfo, characterInfo);

    List<RankDocument> rankDocuments = mapper.toRankDocumentList(characterInfos);

    System.out.println(rankDocuments);

    System.out.println(LocalDate.now());
  }


  @Test
  public void stringFormatTableTest() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(
        String.format("| %-15s | %-15s | %-10s | %-15s |\n", "Nick ", "Experience", "Class",
            "Guild"));
    stringBuilder.append(
        String.format("| %-15s | %-15s | %-10s | %-15s |\n", "asd",
            "aasdasdasdasd",
            "dsdsds", "asd"));
    stringBuilder.append(
        String.format("| %-15s | %-15s | %-10s | %-15s |\n", "asd",
            "asd",
            "dsdsds", "asd"));

    System.out.println(stringBuilder);
  }

  @Test
  public void longMinus() {

    Long a = 5123123123L;
    Long b = 4312312312L;

    System.out.println(Math.abs(a - b));
  }

  @Test
  public void stringDateConvertingTest() {

    LocalDateTime now = LocalDateTime.now();

    System.out.println(now);

    String nowSt = now.toString();

    System.out.println(nowSt);

    LocalDateTime nowParse = LocalDateTime.parse(nowSt);

    System.out.println(nowParse);

  }

  @Test
  public void enumTest() {

    System.out.println(StringUtils.equals("ONLINE", FHXAction.ONLINE.toString()));

    FHXAction online = FHXAction.valueOf("ONLINE");

    System.out.println(online);

    System.out.println(FHXAction.ONLINE.name());
    System.out.println(FHXAction.ONLINE.toString());

    System.out.println(StringUtils.equals(FHXAction.ONLINE.name(), FHXAction.ONLINE.toString()));

  }


  @Test
  public void cryptoTest() throws Exception {
    SecretKey key = CryptoUtils.generateKey();

    String stringFromKey = CryptoUtils.getStringFromKey(key);
    SecretKey keyFromString = CryptoUtils.getKeyFromString(stringFromKey);

    SecretKey secretKey = CryptoUtils.getKeyFromString(
        "DejavuDiscordSecretKew==");

    System.out.println(CryptoUtils.getStringFromKey(secretKey));

    ;
  }


  @Test
  public void integerTest() {
    BossEventDTO bossEventDTO = new BossEventDTO();

    bossEventDTO.setMinutesBeforeOption(null);

    System.out.println(bossEventDTO);

    System.out.println(bossEventDTO.getMinutesBeforeOption());
  }

  @Test
  public void testWindowTimeCalculator() {

    Integer bossSpawnRangePercentConstant = 20;

    Long respawnTimeSeconds = 32400L;

    double range = respawnTimeSeconds * (bossSpawnRangePercentConstant / 100.0);

    BigDecimal bd = new BigDecimal(range).setScale(0, RoundingMode.CEILING);
    Long roundedRange = bd.longValue();

    Long windowStartAtSeconds =
        respawnTimeSeconds - roundedRange;
    Long windowEndAtSeconds =
        respawnTimeSeconds + roundedRange;

    System.out.println(range);
    System.out.println(roundedRange);
    System.out.println(windowStartAtSeconds);
    System.out.println(windowEndAtSeconds);

    ;


  }


}
