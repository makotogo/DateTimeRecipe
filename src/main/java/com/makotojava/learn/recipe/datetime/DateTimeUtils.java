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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Date and Time Recipe code.
 * 
 * Some simple utility classes to demonstrate various uses of the
 * JDK Date/Time classes.
 *
 */
public class DateTimeUtils {

  /**
   * Creates a Date object representing the instant corresponding
   * to the specified Instant.
   * 
   * @param instant
   *          The Instant from which the Date is to be created.
   * 
   * @return Date - the JDK Date object.
   */
  public Date toDate(Instant instant) {
    return new Date(instant.toEpochMilli());
  }

  /**
   * Creates a Date object representing the instant corresponding
   * to the specified LocalDateTime in the specified time zone.
   * 
   * @param localDateTime
   *          The LocalDateTime from which the Date is
   *          to be created
   * @param timeZoneId
   *          The time zone where the LocalDateTime zone
   *          represents
   * @return Date - the JDK Date object.
   */
  public Date toDate(LocalDateTime localDateTime, ZoneId timeZoneId) {
    return new Date(ZonedDateTime.ofLocal(localDateTime, timeZoneId, null).toInstant().toEpochMilli());
  }

  /**
   * Creates a Date object representing the instant corresponding
   * to the specified number of milliseconds since the Epoch.
   * 
   * @param epochMilli
   *          The number of milliseconds since 1/1/1970 at midnight UTC
   * 
   * @return - the JDK Date object.
   */
  public Date toDate(long epochMilli) {
    return new Date(epochMilli);
  }

  /**
   * Creates a Date object from the specified String representation
   * (if possible), using the specified format Pattern. Assumes UTC
   * as the time zone.
   * 
   * @param dateTimeString
   *          The string representation of the date/time
   * @param dtf
   *          The DateTimeFormatter to use
   * 
   * @return - the JDK Date object.
   * 
   * @throws IllegalArgumentException
   *           If pattern is bad
   * 
   * @throws DateTimeParseException
   *           If date/time cannot be parsed.
   */
  public Date toDate(String dateTimeString, DateTimeFormatter dtf) {
    LocalDateTime ldt = LocalDateTime.parse(dateTimeString, dtf);
    return new Date(ldt.toInstant(ZoneOffset.UTC).toEpochMilli());
  }

  /**
   * Creates a Date object representing the instant corresponding
   * to the specified ZonedDateTime.
   * 
   * @param zonedDateTime
   *          The ZonedDateTime from which the Date is to be created
   * @return Date - the JDK Date object.
   */
  public Date toDate(ZonedDateTime zonedDateTime) {
    return new Date(zonedDateTime.toInstant().toEpochMilli());
  }

  /**
   * Creates a Duration object representing the interval between
   * fromDate and toDate.
   * 
   * @param fromDate
   *          The instant of the beginning (inclusive) of the interval
   * @param toDate
   *          The instant of the end (exclusive) of the interval
   * @return - the Duration object
   */
  public Duration toDuration(Date fromDate, Date toDate) {
    return Duration.between(Instant.ofEpochMilli(fromDate.getTime()), Instant.ofEpochMilli(toDate.getTime()));
  }

  /**
   * Creates a Duration object representing the interval between
   * fromInstant and toInstant.
   * 
   * @param fromInstant
   *          The Instant of the beginning (inclusive) of the interval
   * @param toInstant
   *          The Instant of the end (exclusive) of the interval
   * @return - the Duration object
   */
  public Duration toDuration(Instant fromInstant, Instant toInstant) {
    return Duration.between(fromInstant, toInstant);
  }

  /**
   * Creates a Duration object representing the interval between
   * the fromLocalDateTime and toLocalDateTime. Assumes system default time
   * zone for any necessary internal conversions.
   * 
   * @param fromLocalDateTime
   *          The beginning of the interval (inclusive)
   * @param toLocalDateTime
   *          The end of the interval (exclusive)
   * @return The Duration object
   */
  public Duration toDuration(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
    return Duration.between(fromLocalDateTime, toLocalDateTime);
  }

  /**
   * Creates a Duration object representing the interval between the
   * fromMillis and toMillis values. Provided for interface consistency.
   * 
   * @param fromMillis
   *          The instant of the beginning of the interval in ms (inclusive)
   * @param toMillis
   *          The instant of the end of the interval in ms (exclusive)
   * @return The Duration object
   */
  public Duration toDuration(long fromMillis, long toMillis) {
    return Duration.of(toMillis - fromMillis, ChronoUnit.MILLIS);
  }

  /**
   * Creates a Duration object representing the interval between the
   * specified fromZonedDateTime and toZonedDateTime.
   * 
   * @param fromZonedDateTime
   *          The instant of the beginning of the interval (inclusive)
   * @param toZonedDateTime
   *          The instant of the end of the interval (exclusive)
   * @return The Duration object
   */
  public Duration toDuration(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime) {
    return Duration.between(fromZonedDateTime, toZonedDateTime);
  }

  /**
   * Creates an Instant object representing the instant specified by the
   * dateInstant object.
   * 
   * @param dateInstant
   *          The instant represented by the JDK Date object
   * @return The Instant object
   */
  public Instant toInstant(Date dateInstant) {
    return Instant.ofEpochMilli(dateInstant.getTime());
  }

  /**
   * Creates an Instant object representing the instant specified by the
   * localDateTime object at the location specified by the timeZoneId.
   * 
   * @param localDateTime
   *          The LocalDateTime representing the local time of the instant
   * @param timeZoneId
   *          The time zone where the instant occurred
   * @return The Instant object
   */
  public Instant toInstant(LocalDateTime localDateTime, ZoneId timeZoneId) {
    return localDateTime.atZone(timeZoneId).toInstant();
  }

  /**
   * Creates an Instant object representing the instant corresponding
   * to the specified number of milliseconds since the epoch.
   * 
   * @param epochMilli
   *          The number of milliseconds since the epoch
   * 
   * @return Instant - the Instant instance corresponding to the
   *         specified number of milliseconds since the epoch.
   */
  public Instant toInstant(long epochMilli) {
    return Instant.ofEpochMilli(epochMilli);
  }

  /**
   * Creates an Instant representing the instant in time corresponding
   * to the specified ZonedDateTime object.
   * 
   * @param zonedDateTime
   *          The ZonedDateTime object representing the instant on the
   *          timeline from which the Instant to be created is based.
   * @return The Instant object.
   */
  public Instant toInstant(ZonedDateTime zonedDateTime) {
    return zonedDateTime.toInstant();
  }

  /**
   * Creates an LocalDateTime object representing the instant in time corresponding
   * to the specified Date object at the specified time zone.
   * 
   * @param date
   *          The Date object representing the instant on the
   *          timeline from which the LocalDateTime to be created is based.
   * @param timeZoneId
   *          The time zone ID at which the specified date instant occurred
   * @return The LocalDateTime object
   */
  public LocalDateTime toLocalDateTime(Date date, ZoneId timeZoneId) {
    return Instant.ofEpochMilli(date.getTime()).atZone(timeZoneId).toLocalDateTime();
  }

  /**
   * Creates an LocalDateTime object representing the instant in time corresponding
   * to the specified Instant object at the specified time zone.
   * 
   * @param instant
   *          The Instant on the timeline
   * @param timeZoneId
   *          The time zone ID at which the instant on the timeline specified
   *          Instance occurred
   * @return The LocalDateTime object
   */
  public LocalDateTime toLocalDateTime(Instant instant, ZoneId timeZoneId) {
    return instant.atZone(timeZoneId).toLocalDateTime();
  }

  /**
   * Creates a LocalDateTime object representing the instant corresponding
   * to the specified number of milliseconds since the epoch (at UTC),
   * adjusted for the specified time zone.
   * 
   * @param epochMilli
   *          The number of milliseconds since the epoch
   * 
   * @param timeZoneId
   *          The Local ZoneId where the Date/Time is to be
   *          adjusted relative to UTC.
   * 
   * @return LocalDateTime - the LocalDateTime instance corresponding to
   *         the specified number of millis since the epoch at UTC, adjusted for
   *         the local time as specified by the ZoneId.
   */
  public LocalDateTime toLocalDateTime(long epochMilli, ZoneId timeZoneId) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), timeZoneId);
  }

  /**
   * Creates an LocalDateTime object from the specified String representation
   * (if possible), using the specified format Pattern.
   * 
   * @param dateTimeString
   *          The string representation of the date/time
   * @param dtf
   *          The DateTimeFormatter to use
   * @return The Instant object
   * 
   */
  public LocalDateTime toLocalDateTime(String dateTimeString, DateTimeFormatter dtf) {
    return LocalDateTime.parse(dateTimeString, dtf);
  }

  /**
   * Creates a LocalDateTime object representing the instant in time corresponding
   * to the specified ZonedDateTime object, at the time zone of the ZonedDateTime object.
   * 
   * @param zonedDateTime
   *          The ZonedDateTime object representing the instant on the
   *          timeline from which the Instant to be created is based.
   * 
   * @return The LocalDateTime object.
   */
  public LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
    return zonedDateTime.toLocalDateTime();
  }

  /**
   * Returns the String representation (if possible) of the specified
   * Date object, using the specified formatPattern.
   * 
   * @param date
   *          The Date object whose string representation is to be returned
   * @param timeZoneId
   *          The time zone ID at which the specified date instant occurred
   * @param formatPattern
   *          The formatPattern to be used. See TODO: dig up link to DateTimeFormatter javadoc
   * @return The String representation, if possible, or a formatting exception if not
   */
  public String toString(Date date, ZoneId timeZoneId, String formatPattern) {
    return Instant.ofEpochMilli(date.getTime()).atZone(timeZoneId).format(DateTimeFormatter.ofPattern(formatPattern));
  }

  /**
   * Returns the String representation (if possible) of the specified
   * LocalDateTime object, using the specified formatPattern.
   * 
   * @param localDateTime
   *          The LocalDateTime object whose string representation is to be returned
   * @param timeZoneId
   *          The time zone ID at which the specified date instant occurred
   * @param formatPattern
   *          The formatPattern to be used. See TODO: dig up link to DateTimeFormatter javadoc
   * @return The String representation, if possible, or a formatting exception if not
   */
  public String toString(LocalDateTime localDateTime, ZoneId timeZoneId, String formatPattern) {
    return localDateTime.format(DateTimeFormatter.ofPattern(formatPattern));
  }

  /**
   * 
   * Returns the String representation (if possible) of the specified
   * ZonedDateTime object, using the specified formatPattern.
   * 
   * @param zonedDateTime
   *          The ZonedDateTime object whose string representation is to be returned
   * @param formatPattern
   *          The formatPattern to be used. See TODO: dig up link to DateTimeFormatter javadoc
   * @return The String representation, if possible, or a formatting exception if not
   */
  public String toString(ZonedDateTime zonedDateTime, String formatPattern) {
    return zonedDateTime.format(DateTimeFormatter.ofPattern(formatPattern));
  }

  /**
   * Creates a ZonedDateTime object representing the instant in time corresponding
   * to the specified Date object at the specified time zone.
   * 
   * @param date
   *          The Date object representing the instant on the
   *          timeline from which the ZonedDateTime to be created is based.
   * @param timeZoneId
   *          The time zone ID at which the specified date instant occurred
   * @return The ZonedDateTime object
   */
  public ZonedDateTime toZonedDateTime(Date date, ZoneId timeZoneId) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), timeZoneId);
  }

  /**
   * Creates a ZonedDateTime object representing the instant in time corresponding
   * to the specified Instant object at the specified time zone.
   * 
   * @param instant
   *          The Instant on the timeline
   * @param timeZoneId
   *          The time zone ID at which the instant on the timeline specified
   *          Instance occurred
   * @return The ZonedDateTime object
   */
  public ZonedDateTime toZonedDateTime(Instant instant, ZoneId timeZoneId) {
    return ZonedDateTime.ofInstant(instant, timeZoneId);
  }

  /**
   * Creates an ZonedDateTime object representing the instant specified by the
   * localDateTime object at the location specified by the timeZoneId.
   * 
   * @param localDateTime
   *          The LocalDateTime representing the local time of the instant
   * @param timeZoneId
   *          The time zone where the instant occurred
   * @return The ZonedDateTime object
   */
  public ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId timeZoneId) {
    return ZonedDateTime.of(localDateTime, timeZoneId);
  }

  /**
   * Creates a ZonedDateTime object representing the instant corresponding
   * to the specified number of milliseconds since the epoch (at UTC),
   * adjusted for the specified time zone.
   * 
   * @param epochMilli
   *          The number of milliseconds since the epoch
   * 
   * @param timeZoneId
   *          The Local ZoneId where the Date/Time is to be
   *          adjusted relative to UTC.
   * 
   * @return ZonedDateTime - the ZonedDateTime instance corresponding to
   *         the specified number of millis since the epoch at UTC, at the
   *         specified ZoneId.
   */
  public ZonedDateTime toZonedDateTime(long epochMilli, ZoneId timeZoneId) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), timeZoneId);
  }

  /**
   * Creates an Instant object from the specified String representation
   * (if possible), using the specified format Pattern.
   * 
   * @param dateTimeString
   *          The string representation of the date/time
   * @param dtf
   *          The DateTimeFormatter to use
   * @return The Instant object
   * 
   */
  public ZonedDateTime toZonedDateTime(String dateTimeString, DateTimeFormatter dtf) {
    return ZonedDateTime.parse(dateTimeString, dtf);
  }

  /**
   * Returns the LocalDate corresponding to the first day of the
   * specified month and year.
   * 
   * The default date is set to the first day of the month/year,
   * and will be adjusted by a TemporalAdjuster.
   * 
   * @param year
   *          The year.
   * 
   * @param monthOfYear
   *          The month of the year (1 = January, ..., 12 = December)
   * 
   * @param dayOfWeek
   *          The DayOfWeek to be returned. If null, then whatever day
   *          that is the first day is returned.
   * 
   * @return The LocalDate object.
   */
  public LocalDate firstDay(int year, int monthOfYear, DayOfWeek dayOfWeek) {
    LocalDate ret = LocalDate.of(year, monthOfYear, 1);// default to 1st calendar day of the month/year
    if (dayOfWeek != null) {
      ret = ret.with(TemporalAdjusters.firstInMonth(dayOfWeek));
    } else {
      ret = ret.with(TemporalAdjusters.firstDayOfMonth());
    }
    return ret;
  }

  /**
   * Returns the LocalDate corresponding to the last day of the
   * specified month and year.
   * 
   * The default date is set to the first day of the month/year,
   * and will be adjusted by a TemporalAdjuster.
   * 
   * @param year
   *          The year.
   * 
   * @param monthOfYear
   *          The month of the year (1 = January, ..., 12 = December)
   * 
   * @param dayOfWeek
   *          The DayOfWeek to be returned. If null, then whatever day
   *          that is the last day is returned.
   * 
   * @return The LocalDate object.
   */
  public LocalDate lastDay(int year, int monthOfYear, DayOfWeek dayOfWeek) {
    LocalDate ret = LocalDate.of(year, monthOfYear, 1);// default to 1st calendar day of the month/year
    if (dayOfWeek != null) {
      ret = ret.with(TemporalAdjusters.lastInMonth(dayOfWeek));
    } else {
      ret = ret.with(TemporalAdjusters.lastDayOfMonth());
    }
    return ret;
  }

  /**
   * Returns a LocalDate corresponding to the specified weekOrdinal (Nth)
   * day-of-the-week in the specified year and month.
   * 
   * @param year
   *          The year.
   * 
   * @param monthOfYear
   *          The month of the year (1 = January, ..., 12 = December).
   * 
   * @param weekOrdinal
   *          The week within the month. Unbounded. Negative values
   *          refer to previous weeks, positive values to weeks within the monthOfYear.
   *          There is no enforcement of this number. The 7th week in the month, for
   *          example, would obviously return some week within the next month.
   * 
   * @param dayOfWeek
   *          The DayOfWeek to be returned. Not null.
   * 
   * @return The LocalDate object.
   */
  public LocalDate nthDayOfWeekIn(int year, int monthOfYear, int weekOrdinal, DayOfWeek dayOfWeek) {
    LocalDate ret = LocalDate.of(year, monthOfYear, 1);// default to 1st calendar day of the month/year

    if (dayOfWeek == null) {
      throw new IllegalArgumentException("DayOfWeek argument cannot be null!");
    }

    ret = ret.with(TemporalAdjusters.dayOfWeekInMonth(weekOrdinal, dayOfWeek));

    return ret;
  }

  /**
   * Returns the LocalDate corresponding to the USA Presidential election day
   * for the specified year. If the year is not an election year, an IllegalArgumentException
   * is thrown.
   * 
   * @param year
   *          The presidential election year.
   * 
   * @return The LocalDate object.
   */
  public LocalDate presidentialElectionDayUsa(int year) {
    // Make sure it's an election year - Naive approach?
    if (year % 4 != 0) {
      throw new IllegalArgumentException("The specified year: " + year + " is not a U.S. Presidential election year");
    }
    // Rule for U.S. Presidential Election day:
    /// Must be the first Tuesday after the first Monday in November.
    return LocalDate.of(year, 11, 1)//
        .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))// The first Monday
        .with(TemporalAdjusters.next(DayOfWeek.TUESDAY));// The first Tuesday after that
  }

  /**
   * Returns a LocalDate that is numberOfDays after the specified
   * referenceDate.
   * 
   * @param referenceDate
   *          The reference date, i.e., the date to which
   *          the returned date is relative.
   * 
   * @return The LocalDate object.
   */
  public LocalDate nDaysAfter(long numberOfDays, LocalDate referenceDate) {
    // That was easy. Thanks, Java 8 Time API!
    return referenceDate.plusDays(numberOfDays);
  }

}
