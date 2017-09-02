/*
 * Copyright 2017 Makoto Consulting Group, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makotojava.learn.recipe.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * Unit test for simple App.
 */
@RunWith(JUnitPlatform.class)
@DisplayName("Testing DateTimeUtils")
public class DateTimeUtilsTest {

  // A few handy constants for testing (and consistency) with java.util.Date
  private Instant INSTANT_20170115_1435UTC =
      Instant.ofEpochMilli(LocalDateTime.of(2017, 1, 15, 14, 35).toInstant(ZoneOffset.UTC).toEpochMilli());

  private LocalDateTime LOCALDATETIME_20170215_1200 = LocalDateTime.of(2017, 2, 15, 12, 0);

  private ZonedDateTime ZONEDDATETIME_20170315_1900PST =
      ZonedDateTime.of(2017, 3, 15, 19, 0, 0, 0, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));

  private DateTimeUtils classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new DateTimeUtils();
  }

  @Nested
  @DisplayName("Testing: toDate")
  public class ToDate {

    @Nested
    @DisplayName("When Instant is 01/15/2017 at 14:35 UTC")
    public class ParamInstant {

      // Create a Date from an Instant
      @Test
      @DisplayName("JodaTime Date matches Date from toDate")
      public void toDate() {
        // Using Joda-Time as a bench testing tool, that's all
        Date expectedDate = new org.joda.time.LocalDateTime(2017, 1, 15, 14, 35).toDate(TimeZone.getTimeZone("UTC"));
        assertEquals(expectedDate, classUnderTest.toDate(INSTANT_20170115_1435UTC));
      }
    }

    @Nested
    @DisplayName("When LocalDateTime is 2/15/2017 at 12:00 CST")
    public class ParamLocalDateTime {
      // Create a Date from a LocalDateTime
      @Test
      @DisplayName("JodaTime Date matches Date from toDate")
      public void toDate() {
        // Using Joda-Time as a bench testing tool, that's all
        Date expectedDate = new org.joda.time.LocalDateTime(2017, 2, 15, 18, 0).toDate(TimeZone.getTimeZone("UTC"));
        assertEquals(expectedDate,
            classUnderTest.toDate(LOCALDATETIME_20170215_1200, ZoneId.of(ZoneId.SHORT_IDS.get("CST"))));
      }
    }

    @Nested
    @DisplayName("When millis since Epoch is from 01/15/2017 at 14:35 UTC")
    public class ParamLong {
      // Create a Date from a Long
      @Test
      @DisplayName("JodaTime Date matches Date from toDate")
      public void toDate() {
        // Using Joda-Time as a bench testing tool, that's all
        Date expectedDate = new org.joda.time.LocalDateTime(2017, 1, 15, 14, 35).toDate(TimeZone.getTimeZone("UTC"));
        assertEquals(expectedDate, classUnderTest.toDate(INSTANT_20170115_1435UTC.toEpochMilli()));
      }
    }

    @Nested
    @DisplayName("When date string is \'2017-01-15 14:35\' and format string is \'yyyy-MM-DD HH:mm:ss\'")
    public class ParamString {
      // Create a Date from its String representation
      @Test
      @DisplayName("JodaTime Date matches Date from toDate")
      public void toDate() {
        Date expectedDate = new org.joda.time.LocalDateTime(2017, 1, 15, 14, 35).toDate(TimeZone.getTimeZone("UTC"));
        assertEquals(expectedDate,
            classUnderTest.toDate("2017-01-15T14:35:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }
    }

    @Nested
    @DisplayName("When ZonedDateTime is 2017-03-15 at 19:00 PST")
    public class ParamZonedDateTime {
      // Create a Date from a ZonedDateTime
      @Test
      @DisplayName("JodaTime Date matches Date from toDate")
      public void toDate() {
        Date expectedDate = new org.joda.time.LocalDateTime(2017, 3, 15, 19, 00).toDate(TimeZone.getTimeZone("PST"));
        assertEquals(expectedDate, classUnderTest.toDate(ZONEDDATETIME_20170315_1900PST));
      }
    }

  } // END: ToDate

  @Nested
  @DisplayName("Testing: toDuration")
  public class ToDuration {

    private Date fromDateParam =
        new org.joda.time.LocalDateTime(2017, 3, 15, 19, 00).toDate(TimeZone.getTimeZone("PST"));
    private Date toDateParam = new org.joda.time.LocalDateTime(2017, 3, 16, 19, 00).toDate(TimeZone.getTimeZone("PST"));
    ZoneId zoneIdParam = ZoneId.of(ZoneId.SHORT_IDS.get("PST"));
    LocalDateTime fromLdtParam =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(fromDateParam.getTime()), zoneIdParam);
    LocalDateTime toLdtParam =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(toDateParam.getTime()), zoneIdParam);
    Instant fromInstantParam = Instant.ofEpochMilli(fromDateParam.getTime());
    Instant toInstantParam = Instant.ofEpochMilli(toDateParam.getTime());
    ZonedDateTime fromZdtParam = ZonedDateTime.of(fromLdtParam, zoneIdParam);
    ZonedDateTime toZdtParam = ZonedDateTime.of(toLdtParam, zoneIdParam);

    @Nested
    @DisplayName("Date: When fromDate is 3/15/2017 at 19:00PST and toDate is 3/16/2017 at 19:00PST")
    public class ParamDate {
      // Create a Duration from two Date
      @Test
      @DisplayName("Duration in milliseconds is correct")
      public void toDuration() {
        long expectedDurationInMillis = toDateParam.getTime() - fromDateParam.getTime();
        assertEquals(expectedDurationInMillis, classUnderTest.toDuration(fromDateParam, toDateParam).toMillis());
      }
    }

    @Nested
    @DisplayName("Instant: When fromDate is 3/15/2017 at 19:00PST and toDate is 3/16/2017 at 19:00PST")
    public class ParamInstant {
      // Create a Duration from two Instants
      @Test
      @DisplayName("Duration in milliseconds is correct")
      public void toDuration() {
        long expectedDurationInMillis = toDateParam.getTime() - fromDateParam.getTime();
        assertEquals(expectedDurationInMillis, classUnderTest.toDuration(fromInstantParam, toInstantParam).toMillis());
      }
    }

    @Nested
    @DisplayName("LocalDateTime: When fromDate is 3/15/2017 at 19:00PST and toDate is 3/16/2017 at 19:00PST")
    public class ParamLocalDateTime {
      // Create a Duration from two LocalDateTime
      @Test
      @DisplayName("Duration in milliseconds is correct")
      public void toDuration() {
        long expectedDurationInMillis = toDateParam.getTime() - fromDateParam.getTime();
        assertEquals(expectedDurationInMillis, classUnderTest.toDuration(fromLdtParam, toLdtParam).toMillis());
      }
    }

    @Nested
    @DisplayName("Long millis: When fromDate is 3/15/2017 at 19:00PST and toDate is 3/16/2017 at 19:00PST")
    public class ParamLong {
      // Create a Duration from two Long
      @Test
      @DisplayName("Duration in milliseconds is correct")
      public void toDuration() {
        long expectedDurationInMillis = toDateParam.getTime() - fromDateParam.getTime();
        assertEquals(expectedDurationInMillis,
            classUnderTest.toDuration(fromDateParam.getTime(), toDateParam.getTime()).toMillis());
      }
    }

    @Nested
    @DisplayName("ZonedDateTime: When fromDate is 3/15/2017 at 19:00PST and toDate is 3/16/2017 at 19:00PST")
    public class ParamZonedDateTime {
      // Create a Duration from two ZonedDateTime
      @Test
      @DisplayName("Duration in milliseconds is correct")
      public void toDuration() {
        long expectedDurationInMillis = toDateParam.getTime() - fromDateParam.getTime();
        assertEquals(expectedDurationInMillis, classUnderTest.toDuration(fromZdtParam, toZdtParam).toMillis());
      }
    }

  } // END: ToDuration

  @Nested
  @DisplayName("Testing: toInstant")
  public class ToInstant {

    private Date dateParam = new org.joda.time.LocalDateTime(2017, 3, 15, 20, 00).toDate(TimeZone.getTimeZone("MST"));

    ZoneId zoneIdParam = ZoneId.of(ZoneId.SHORT_IDS.get("MST"));
    LocalDateTime ldtParam =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(dateParam.getTime()), zoneIdParam);
    Instant instantParam = Instant.ofEpochMilli(dateParam.getTime());
    ZonedDateTime zdtParam = ZonedDateTime.of(ldtParam, zoneIdParam);

    @Nested
    @DisplayName("Param: Date: When date is 3/15/2017 at 20:00MST")
    public class ParamDate {
      // Create an Instant from Date
      @Test
      @DisplayName("Instant in milliseconds is correct")
      public void toInstant() {
        long expectedMillis = dateParam.getTime();
        assertEquals(expectedMillis, classUnderTest.toInstant(dateParam).toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: LocalDateTime: When date is 3/15/2017 at 20:00MST")
    public class ParamLocalDateTime {
      // Create an Instant from a LocalDateTime
      @Test
      @DisplayName("Instant in milliseconds is correct")
      public void toInstant() {
        long expectedMillis = ldtParam.atZone(zoneIdParam).toInstant().toEpochMilli();
        assertEquals(expectedMillis, classUnderTest.toInstant(ldtParam, zoneIdParam).toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: long - number of millis since Epoch: When date is 3/15/2017 at 20:00MST")
    public class ParamLong {
      // Create an Instant from Long number of millis since Epoch
      @Test
      @DisplayName("Instant in milliseconds is correct")
      public void toInstant() {
        long expectedMillis = instantParam.toEpochMilli();
        assertEquals(expectedMillis, classUnderTest.toInstant(instantParam.toEpochMilli()).toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: ZonedDateTime: When date is 3/15/2017 at 20:00MST")
    public class ParamZonedDateTime {
      // Create an Instant from a ZonedDateTime
      @Test
      @DisplayName("Instant in milliseconds is correct")
      public void toInstant() {
        long expectedMillis = zdtParam.toInstant().toEpochMilli();
        assertEquals(expectedMillis, classUnderTest.toInstant(zdtParam).toEpochMilli());
      }
    }

  } // END:ToInstant

  @Nested
  @DisplayName("Testing: toLocalDateTime")
  public class ToLocalDateTime {

    private Date dateParam = new org.joda.time.LocalDateTime(2017, 4, 15, 10, 00).toDate(TimeZone.getTimeZone("EST"));
    private Date dateParam2 = new org.joda.time.LocalDateTime(1945, 4, 15, 10, 00).toDate(TimeZone.getTimeZone("EST"));

    private ZoneId zoneIdParam = ZoneId.of(ZoneId.SHORT_IDS.get("EST"));
    private Instant instantParam = Instant.ofEpochMilli(dateParam.getTime());
    private long longParam = dateParam.getTime();
    private ZonedDateTime zdtParam = ZonedDateTime.ofInstant(instantParam, zoneIdParam);

    @Nested
    @DisplayName("Param: Date: When date is 4/15/2017 at 10:00 EST")
    public class ParamDate {
      // Create a LocalDateTime from a Date
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime() {
        long expectedMillis = dateParam.getTime();
        assertEquals(expectedMillis,
            classUnderTest.toLocalDateTime(dateParam, zoneIdParam).atZone(zoneIdParam).toInstant().toEpochMilli());
      }

      // Create a LocalDateTime from a Date
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime2() {
        long expectedMillis = dateParam2.getTime();
        assertEquals(expectedMillis,
            classUnderTest.toLocalDateTime(dateParam2, zoneIdParam).atZone(zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: Instant: When instant is 4/15/2017 at 10:00 EST")
    public class ParamInstant {
      // Create a LocalDateTime from an Instant
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime() {
        long expectedMillis = instantParam.toEpochMilli();
        assertEquals(expectedMillis,
            classUnderTest.toLocalDateTime(instantParam, zoneIdParam).atZone(zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: Long: When millis value is from 4/15/2017 at 10:00 EST")
    public class ParamLong {
      // Create a LocalDateTime from an Instant
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime() {
        long expectedMillis = longParam;
        assertEquals(expectedMillis,
            classUnderTest.toLocalDateTime(longParam, zoneIdParam).atZone(zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: String: When date string is \'2017-01-15 14:35\' and format string is \'yyyy-MM-DD HH:mm:ss\'")
    public class ParamString {
      // Create a LocalDateTime from a String
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime() {
        LocalDateTime expectedDate = LocalDateTime.of(2017, 1, 15, 14, 35);
        assertEquals(expectedDate,
            classUnderTest.toLocalDateTime("2017-01-15T14:35:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }
    }

    @Nested
    @DisplayName("Param: ZonedDateTime: When millis value is from 4/15/2017 at 10:00 EST")
    public class ParamZonedDateTime {
      // Create a LocalDateTime from a ZonedDateTime
      @Test
      @DisplayName("LocalDateTime at EST TZ converted to millis is correct")
      public void toLocalDateTime() {
        long expectedMillis = zdtParam.toInstant().toEpochMilli();
        assertEquals(expectedMillis,
            classUnderTest.toLocalDateTime(zdtParam).atZone(zoneIdParam).toInstant().toEpochMilli());
      }
    }

  } // END: ToLocalDateTime

  @Nested
  @DisplayName("Testing: toString")
  public class ToString {

    private Date dateParam = new org.joda.time.LocalDateTime(2017, 5, 15, 3, 00).toDate(TimeZone.getTimeZone("CST"));
    private ZoneId zoneIdParam = ZoneId.of(ZoneId.SHORT_IDS.get("CST"));
    private Instant instantParam = Instant.ofEpochMilli(dateParam.getTime());
    private LocalDateTime ldtParam = LocalDateTime.ofInstant(instantParam, zoneIdParam);
    private ZonedDateTime zdtParam = ZonedDateTime.ofInstant(instantParam, zoneIdParam);

    @Nested
    @DisplayName("Param: Date: When date is 5/15/2017 at 03:00 CST")
    public class ParamDate {
      // Format Date as a String
      @Test
      @DisplayName("Formatted String is 05/15/2017 03:00")
      public void testToString() {
        String expectedString = "05/15/2017 03:00";
        assertEquals(expectedString, classUnderTest.toString(dateParam, zoneIdParam, "MM/dd/yyyy HH:mm"));
      }
    }

    @Nested
    @DisplayName("Param: LocalDateTime: When date is 5/15/2017 at 03:00 CST")
    public class ParamLocalDateTime {
      // Format LocalDateTime as a String
      @Test
      @DisplayName("Formatted String is correct")
      public void testToString() {
        String expectedString = "05/15/2017 03:00";
        assertEquals(expectedString, classUnderTest.toString(ldtParam, zoneIdParam, "MM/dd/yyyy HH:mm"));
      }
    }

    @Nested
    @DisplayName("Param: ZonedDateTime: When date is 5/15/2017 at 03:00 CST")
    public class ParamZonedDateTime {
      // Format ZonedDateTime as a String
      @Test
      @DisplayName("Formatted String is correct")
      public void testToString() {
        String expectedString = "05/15/2017 03:00";
        assertEquals(expectedString, classUnderTest.toString(zdtParam, "MM/dd/yyyy HH:mm"));
      }
    }

  } // END: ToString

  @Nested
  @DisplayName("Testing: toZonedDateTime")
  public class ToZonedDateTime {

    private Date dateParam = new org.joda.time.LocalDateTime(2017, 6, 15, 23, 00).toDate(TimeZone.getTimeZone("CST"));
    private ZoneId zoneIdParam = ZoneId.of(ZoneId.SHORT_IDS.get("CST"));
    private Instant instantParam = Instant.ofEpochMilli(dateParam.getTime());
    private LocalDateTime ldtParam = LocalDateTime.ofInstant(instantParam, zoneIdParam);
    private Long longParam = dateParam.getTime();

    @Nested
    @DisplayName("Param: Date: When date is 6/15/2017 at 23:00 CST")
    public class ParamDate {
      // Create a ZonedDateTime from a Date
      @Test
      @DisplayName("ZonedDateTime millis value matches millis value from param")
      public void toZonedDateTime() {
        long expectedMillisValue = dateParam.getTime();
        assertEquals(expectedMillisValue,
            classUnderTest.toZonedDateTime(dateParam, zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: Instant: When instant is 6/15/2017 at 23:00 CST")
    public class ParamInstant {
      // Create a ZonedDateTime from an Instant
      @Test
      @DisplayName("ZonedDateTime millis value matches millis value from param")
      public void toZonedDateTime() {
        long expectedMillis = instantParam.toEpochMilli();
        assertEquals(expectedMillis,
            classUnderTest.toZonedDateTime(instantParam, zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: LocalDateTime: When date is 6/15/2017 at 23:00 CST")
    public class ParamLocalDateTime {
      // Create a ZonedDateTime from a LocalDateTime
      @Test
      @DisplayName("ZonedDateTime millis value matches millis value from param at CST")
      public void toZonedDateTime() {
        long expectedMillis = ldtParam.atZone(zoneIdParam).toInstant().toEpochMilli();
        assertEquals(expectedMillis, classUnderTest.toZonedDateTime(ldtParam, zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: Long - milliseconds since Epoch: When millis is from 6/15/2017 at 23:00 CST")
    public class ParamLong {
      // Create a ZonedDateTime from a Long number of millis since Epoch
      @Test
      @DisplayName("ZonedDateTime millis value matches millis value from param")
      public void toZonedDateTime() {
        long expectedMillis = longParam;
        assertEquals(expectedMillis, classUnderTest.toZonedDateTime(longParam, zoneIdParam).toInstant().toEpochMilli());
      }
    }

    @Nested
    @DisplayName("Param: String - testing parsing logic: : When date is \'2017-05-15T23:00")
    public class ParamString {
      // Create a ZonedDateTime from a String
      @Test
      @DisplayName("ZonedDateTime from parsed string is correct")
      public void toZonedDateTime() {
        ZonedDateTime expectedDate = ZonedDateTime.of(ldtParam, zoneIdParam);
        assertEquals(expectedDate,
            classUnderTest.toZonedDateTime("2017-06-15T23:00-05:00[America/Chicago]", DateTimeFormatter.ISO_DATE_TIME));
      }
    }

  } // END: ToZonedDateTime

  // * Computations

  @Test
  @DisplayName("The first Monday in September 2021 is 9/6/2021")
  public void firstMondayInSeptember2021() {
    LocalDate expectedLd = LocalDate.of(2021, 9, 6);
    assertEquals(expectedLd, classUnderTest.firstDay(2021, 9, DayOfWeek.MONDAY));
  }

  @Test
  @DisplayName("The last Monday in September 2021 is 9/27/2021")
  public void lastMondayInSeptember2021() {
    LocalDate expectedLd = LocalDate.of(2021, 9, 27);
    assertEquals(expectedLd, classUnderTest.lastDay(2021, 9, DayOfWeek.MONDAY));
  }

  @Test
  @DisplayName("The third Wednesday in July 2018 is 7/18/2018")
  public void thirdWednesdayInJuly2018() {
    LocalDate expectedLd = LocalDate.of(2018, 7, 18);
    assertEquals(expectedLd, classUnderTest.nthDayOfWeekIn(2018, 7, 3, DayOfWeek.WEDNESDAY));
  }

  @Test
  @DisplayName("The next Presidential election day in the USA is 11/3/2020")
  public void presidentialElectionDayUSA2020() {
    LocalDate expectedLd = LocalDate.of(2020, 11, 3);
    assertEquals(expectedLd, classUnderTest.presidentialElectionDayUsa(2020));
  }

  @Test
  @DisplayName("In 2024, the USA Presidential election day is 11/5/2020")
  public void presidentialElectionDayUSA2024() {
    LocalDate expectedLd = LocalDate.of(2024, 11, 5);
    assertEquals(expectedLd, classUnderTest.presidentialElectionDayUsa(2024));
  }

  @Test
  @DisplayName("90 days after January 1, 2018 is 4/1/2018")
  public void ninetyDaysAfter20180101() {
    LocalDate expectedLd = LocalDate.of(2018, 4, 1);
    assertEquals(expectedLd, classUnderTest.nDaysAfter(90, LocalDate.of(2018, 1, 1)));
  }

}
