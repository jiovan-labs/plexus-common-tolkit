/*
 * Copyright (c) 2016 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.plexus.common.toolkit.util;


import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatCombo;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatConst;


/**
 * Utilities for {@link java.util.Date}
 *
 * @author David Hsing
 *
 * @see java.util.concurrent.TimeUnit
 * @see org.apache.commons.lang3.time.DateUtils
 * @see org.apache.commons.lang3.time.DateFormatUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JdkDateWraps {
    /**
     * Checks whether the date is after the Unix epoch.
     *
     * @param date The date or date text
     *
     * @return true when the date is after the epoch; false otherwise
     */
    public static boolean afterEpoch(@Nullable Date date) {
        return date != null && date.after(getEpochDate());
    }

    /**
     * Returns the year that represents, from LONG.MIN_YEAR to LONG.MAX_YEAR
     *
     * @param date The source date to check
     *
     * @return the year that represents, from LONG.MIN_YEAR to LONG.MAX_YEAR
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Integer getYear(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.ofJdkDate(date).getYear();
    }

    /**
     * Returns the month-of-year that represents, from 1 (January) to 12 (December)
     *
     * @param date The source date to check
     *
     * @return the month-of-year that represents, from 1 (January) to 12 (December)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Integer getMonth(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.ofJdkDate(date).getMonthValue();
    }

    /**
     * Returns the day-of-month that represents, from 1 to 31
     *
     * @param date The source date to check
     *
     * @return the day-of-month that represents, from 1 to 31
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Integer getDayOfMonth(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.ofJdkDate(date).getDayOfMonth();
    }

    /**
     * Returns the day of the week of the given date.
     *
     * @param date The date or date text
     *
     * @return the day of the week, or null when the date is null
     */
    @Nullable
    public static Integer getDayOfWeak(@Nullable Date date) {
        return CalendarPlainWraps.getCalendarField(date, Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * Returns the day of the year of the given date.
     *
     * @param date The date or date text
     *
     * @return the day of the year, or null when the date is null
     */
    @Nullable
    public static Integer getDayOfYear(@Nullable Date date) {
        return CalendarPlainWraps.getCalendarField(date, Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns the Unix epoch date.
     *
     *
     * @return the epoch date
     */
    @Nonnull
    public static Date getEpochDate() {
        return getSpecificDate(1970, 1, 1);
    }

    /**
     * Returns a date for the given year, month and day.
     *
     * @param year The year
     * @param monthOfYear The month of the year
     * @param dayOfMonth The day of the month
     *
     * @return the requested date
     */
    @Nonnull
    public static Date getSpecificDate(int year, int monthOfYear, int dayOfMonth) {
        return getSpecificDateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
    }

    /**
     * Returns a date and time for the given fields.
     *
     * @param year The year
     * @param monthOfYear The month of the year
     * @param dayOfMonth The day of the month
     * @param hourOfDay The hour of the day
     * @param minuteOfHour The minute of the hour
     * @param secondOfMinute The second of the minute
     *
     * @return the requested date and time
     */
    @Nonnull
    public static Date getSpecificDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        return getSpecificDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, 0);
    }

    /**
     * Returns a {@link java.util.Date} with specified calendar fields
     *
     * @param year The year to represent, from LONG.MIN_YEAR to LONG.MAX_YEAR
     * @param monthOfYear The month-of-year to represent, from 1 (January) to 12 (December)
     * @param dayOfMonth The day-of-month to represent, from 1 to 31
     * @param hourOfDay The hour-of-day to represent, from 0 to 23
     * @param minuteOfHour The minute-of-hour to represent, from 0 to 59
     * @param secondOfMinute The second-of-minute to represent, from 0 to 59
     * @param nanoOfSecond The nano-of-second to represent, from 0 to 999,999,999
     * @return a {@link java.util.Date} with specified calendar fields
     *
     * @throws java.lang.IllegalArgumentException if any calendar fields are invalid
     * @throws java.time.DateTimeException if the value of any field is out of range, or if the day-of-month is invalid for the month-year
     * @see java.util.GregorianCalendar
     */
    @Nonnull
    public static Date getSpecificDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int nanoOfSecond) {
        if (year < Year.MIN_VALUE || year > Year.MAX_VALUE) {
            throw new IllegalArgumentException(AssertMessageWraps.isYear("year"));    // $NON-NLS-1$
        }
        if (monthOfYear < 1 || monthOfYear > 12) {
            throw new IllegalArgumentException(AssertMessageWraps.isMonth("monthOfYear"));    // $NON-NLS-1$
        }
        if (dayOfMonth < 1 || dayOfMonth > 31) {
            throw new IllegalArgumentException(AssertMessageWraps.isDay("dayOfMonth"));    // $NON-NLS-1$
        }
        return LocalDateWraps.toJdkDate(LocalDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, nanoOfSecond));
    }

    /**
     * Returns yesterday's date with the time set to midnight.
     *
     *
     * @return yesterday's date
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Date getYesterdayDate() {
        return getDayStartDate(getYesterdayDateTime());
    }

    /**
     * Returns yesterday's date and time.
     *
     *
     * @return yesterday's date and time
     */
    @Nonnull
    public static Date getYesterdayDateTime() {
        return DateUtils.addDays(getCurrentDateTime(), -1);
    }

    /**
     * Returns the current date with the time set to midnight.
     *
     *
     * @return the current date
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Date getCurrentDate() {
        return getDayStartDate(getCurrentDateTime());
    }

    /**
     * Returns the current date and time.
     *
     *
     * @return the current date and time
     */
    @Nonnull
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**
     * Returns tomorrow's date with the time set to midnight.
     *
     *
     * @return tomorrow's date
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Date getTomorrowDate() {
        return getDayStartDate(getTomorrowDateTime());
    }

    /**
     * Returns tomorrow's date and time.
     *
     *
     * @return tomorrow's date and time
     */
    @Nonnull
    public static Date getTomorrowDateTime() {
        return DateUtils.addDays(getCurrentDateTime(), 1);
    }

    /**
     * Returns the minimum supported date and time.
     *
     *
     * @return the minimum date and time
     */
    @Nonnull
    public static Date getMinDateTime() {
        return getSpecificDateTime(0, 1, 1, 0, 0, 0, 0);
    }

    /**
     * Returns the maximum supported date and time.
     *
     *
     * @return the maximum date and time
     */
    @Nonnull
    public static Date getMaxDateTime() {
        return getSpecificDateTime(9999, 12, 31, 23, 59, 59, 999);
    }

    /**
     * Returns the first moment of the day containing the given date.
     *
     * @param date The date or date text
     *
     * @return the start of the day, or null when the date is null
     */
    @Nullable
    public static Date getDayStartDate(@Nullable Date date) {
        return getDayStartDate(date, null, null);
    }

    /**
     * Returns the first moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param zone The time zone
     *
     * @return the start of the day, or null when the date is null
     */
    public static Date getDayStartDate(@Nullable Date date, @Nullable TimeZone zone) {
        return getDayStartDate(date, zone, null);
    }

    /**
     * Returns the first moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param locale The locale
     *
     * @return the start of the day, or null when the date is null
     */
    public static Date getDayStartDate(@Nullable Date date, @Nullable Locale locale) {
        return getDayStartDate(date, null, locale);
    }

    /**
     * Returns the first moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the start of the day, or null when the date is null
     */
    @Nullable
    public static Date getDayStartDate(@Nullable Date date, @Nullable TimeZone zone, @Nullable Locale locale) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(ObjectUtils.getIfNull(zone, TimeZone.getDefault()), ObjectUtils.getIfNull(locale, Locale.getDefault(Locale.Category.FORMAT)));
        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Returns the last moment of the day containing the given date.
     *
     * @param date The date or date text
     *
     * @return the end of the day, or null when the date is null
     */
    public static Date getDayEndDate(@Nullable Date date) {
        return getDayEndDate(date, null, null);
    }

    /**
     * Returns the last moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param zone The time zone
     *
     * @return the end of the day, or null when the date is null
     */
    public static Date getDayEndDate(@Nullable Date date, @Nullable TimeZone zone) {
        return getDayEndDate(date, zone, null);
    }

    /**
     * Returns the last moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param locale The locale
     *
     * @return the end of the day, or null when the date is null
     */
    public static Date getDayEndDate(@Nullable Date date, @Nullable Locale locale) {
        return getDayEndDate(date, null, locale);
    }

    /**
     * Returns the last moment of the day containing the given date.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the end of the day, or null when the date is null
     */
    @Nullable
    public static Date getDayEndDate(@Nullable Date date, @Nullable TimeZone zone, @Nullable Locale locale) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(ObjectUtils.getIfNull(zone, TimeZone.getDefault()), ObjectUtils.getIfNull(locale, Locale.getDefault(Locale.Category.FORMAT)));
        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Returns the number of days in the given month.
     *
     * @param year The year
     * @param month The month
     *
     * @return the number of days in the month
     */
    @Nullable
    public static Integer getMonthDays(int year, int month) {
        return LocalDateWraps.getMonthDays(year, month);
    }

    /**
     * Returns the number of days in the given month.
     *
     * @param date The date or date text
     *
     * @return the number of days in the month
     */
    @Nullable
    public static Integer getMonthDays(@Nullable Date date) {
        return LocalDateWraps.getMonthDays(LocalDateWraps.ofJdkDateTime(date));
    }

    /**
     * Returns the display name of the month of the given date.
     *
     * @param date The date or date text
     * @param style The text style
     *
     * @return the month display name, or null when the date is null
     */
    @Nullable
    public static String getMonthDisplayName(@Nullable Date date, @Nullable TextStyle style) {
        return getMonthDisplayName(date, style, Locale.getDefault());
    }

    /**
     * Returns the display name of the month of the given date.
     *
     * @param date The date or date text
     * @param style The text style
     * @param locale The locale
     *
     * @return the month display name, or null when the date is null
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String getMonthDisplayName(@Nullable Date date, @Nullable TextStyle style, @Nullable Locale locale) {
        return ObjectUtils.anyNull(date, style, locale) ? null : CalendarPlainWraps.getMonthDisplayName(CalendarPlainWraps.getCalendarField(date, Calendar.MONTH, locale) + 1, style, locale);
    }

    /**
     * Returns the first day of the given month.
     *
     * @param year The year
     * @param month The month
     *
     * @return the first date of the month
     */
    @Nullable
    public static Date getMonthFirstDate(int year, int month) {
        return LocalDateWraps.toJdkDate(LocalDateWraps.getMonthFirstDateTime(year, month));
    }

    /**
     * Returns the first day of the given month.
     *
     * @param date The date or date text
     *
     * @return the first date of the month
     */
    @Nullable
    public static Date getMonthFirstDate(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.toJdkDate(LocalDateWraps.getMonthFirstDateTime(LocalDateWraps.ofJdkDateTime(date)));
    }

    /**
     * Returns the last day of the given month.
     *
     * @param year The year
     * @param month The month
     *
     * @return the last date of the month
     */
    @Nullable
    public static Date getMonthLastDate(int year, int month) {
        return LocalDateWraps.toJdkDate(LocalDateWraps.getMonthLastDateTime(year, month));
    }

    /**
     * Returns the last day of the given month.
     *
     * @param date The date or date text
     *
     * @return the last date of the month
     */
    @Nullable
    public static Date getMonthLastDate(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.toJdkDate(LocalDateWraps.getMonthLastDateTime(LocalDateWraps.ofJdkDateTime(date)));
    }

    /**
     * Returns the first day of the season containing the given month.
     *
     * @param year The year
     * @param month The month
     *
     * @return the first date of the season
     */
    @Nullable
    public static Date getSeasonFirstDate(int year, int month) {
        return LocalDateWraps.toJdkDate(LocalDateWraps.getSeasonFirstDateTime(year, month));
    }

    /**
     * Returns the first day of the season containing the given month.
     *
     * @param date The date or date text
     *
     * @return the first date of the season
     */
    @Nullable
    public static Date getSeasonFirstDate(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.toJdkDate(LocalDateWraps.getSeasonFirstDateTime(LocalDateWraps.ofJdkDateTime(date)));
    }

    /**
     * Returns the last day of the season containing the given month.
     *
     * @param year The year
     * @param month The month
     *
     * @return the last date of the season
     */
    @Nullable
    public static Date getSeasonLastDate(int year, int month) {
        return LocalDateWraps.toJdkDate(LocalDateWraps.getSeasonLastDateTime(year, month));
    }

    /**
     * Returns the last day of the season containing the given month.
     *
     * @param date The date or date text
     *
     * @return the last date of the season
     */
    @Nullable
    public static Date getSeasonLastDate(@Nullable Date date) {
        return (date == null) ? null : LocalDateWraps.toJdkDate(LocalDateWraps.getSeasonLastDateTime(LocalDateWraps.ofJdkDateTime(date)));
    }

    /**
     * Formats the current date using the default pattern.
     *
     *
     * @return the formatted current date
     */
    public static String formatCurrentDate() {
        return formatCurrentDateTime(TemporalFormatConst.ISO_YYYYMMDD);
    }

    /**
     * Formats the current date and time.
     *
     *
     * @return the formatted current date and time
     */
    public static String formatCurrentDateTime() {
        return formatCurrentDateTime(TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
    }

    /**
     * Formats the current date and time.
     *
     * @param format The date format pattern
     *
     * @return the formatted current date and time
     */
    public static String formatCurrentDateTime(@Nullable String format) {
        return formatCurrentDateTime(format, null);
    }

    /**
     * Formats the current date and time.
     *
     * @param format The date format pattern
     * @param zone The time zone
     *
     * @return the formatted current date and time
     */
    public static String formatCurrentDateTime(@Nullable String format, @Nullable TimeZone zone) {
        return formatCurrentDateTime(format, zone, null);
    }

    /**
     * Formats the current date and time.
     *
     * @param format The date format pattern
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the formatted current date and time
     */
    public static String formatCurrentDateTime(@Nullable String format, @Nullable TimeZone zone, @Nullable Locale locale) {
        return formatDateTime(getCurrentDateTime(), format, zone, locale);
    }

    /**
     * Formats the date using the default pattern.
     *
     * @param date The date or date text
     *
     * @return the formatted date, or null when the date is null
     */
    public static String formatDate(@Nullable Date date) {
        return formatDateTime(date, TemporalFormatConst.ISO_YYYYMMDD, null, null);
    }

    /**
     * Formats the date and time using the requested pattern and locale.
     *
     * @param date The date or date text
     *
     * @return the formatted date and time, or null when the date is null
     */
    public static String formatDateTime(@Nullable Date date) {
        return formatDateTime(date, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS, null, null);
    }

    /**
     * Formats the date and time using the requested pattern and locale.
     *
     * @param date The date or date text
     * @param format The date format pattern
     *
     * @return the formatted date and time, or null when the date is null
     */
    public static String formatDateTime(@Nullable Date date, @Nullable String format) {
        return formatDateTime(date, format, null, null);
    }

    /**
     * Formats the date and time using the requested pattern and locale.
     *
     * @param date The date or date text
     * @param format The date format pattern
     * @param zone The time zone
     *
     * @return the formatted date and time, or null when the date is null
     */
    public static String formatDateTime(@Nullable Date date, @Nullable String format, @Nullable TimeZone zone) {
        return formatDateTime(date, format, zone, null);
    }

    /**
     * Formats the date and time using the requested pattern and locale.
     *
     * @param date The date or date text
     * @param format The date format pattern
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the formatted date and time, or null when the date is null
     */
    @Nullable
    public static String formatDateTime(@Nullable Date date, @Nullable String format, @Nullable TimeZone zone, @Nullable Locale locale) {
        return (date == null || StringUtils.isBlank(format)) ? null : DateFormatUtils.format(date, format, zone, locale);
    }

    /**
     * Checks whether the text matches any of the given date formats.
     *
     * @param date The date or date text
     * @param formats The date format patterns
     *
     * @return true when a format matches; false otherwise
     */
    public static boolean matchAnyFormats(@Nullable String date, @Nullable String... formats) {
        return matchAnyFormats(date, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Checks whether the text matches any of the given date formats.
     *
     * @param date The date or date text
     * @param formats The date format patterns
     *
     * @return true when a format matches; false otherwise
     */
    public static boolean matchAnyFormats(@Nullable String date, @Nullable Collection<String> formats) {
        return StringUtils.isNotBlank(date) && CollectionPlainWraps.isNotEmpty(formats) && formats.stream().filter(StringUtils::isNotBlank).anyMatch(format -> matchFormat(date, format));
    }

    /**
     * Checks whether the text matches any of the given date formats.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     * @param formats The date format patterns
     *
     * @return true when a format matches; false otherwise
     */
    public static boolean matchAnyFormats(@Nullable String date, @Nullable TimeZone zone, @Nullable Locale locale, @Nullable String... formats) {
        return matchAnyFormats(date, zone, locale, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Checks whether the text matches any of the given date formats.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     * @param formats The date format patterns
     *
     * @return true when a format matches; false otherwise
     */
    public static boolean matchAnyFormats(@Nullable String date, @Nullable TimeZone zone, @Nullable Locale locale, @Nullable Collection<String> formats) {
        return StringUtils.isNotBlank(date) && CollectionPlainWraps.isNotEmpty(formats) && formats.stream().filter(StringUtils::isNotBlank).anyMatch(format -> matchFormat(date, format, zone, locale));
    }

    /**
     * Checks whether the text matches the given date format.
     *
     * @param date The date or date text
     * @param format The date format pattern
     *
     * @return true when the format matches; false otherwise
     */
    public static boolean matchFormat(@Nullable String date, @Nullable String format) {
        if (StringUtils.isAnyBlank(date, format)) {
            return false;
        }
        try {
            parseDate(date, format);
            return true;
        } catch (ParseException ignored) {
        }
        return false;
    }

    /**
     * Checks whether the text matches the given date format.
     *
     * @param date The date or date text
     * @param format The date format pattern
     * @param zone The time zone
     * @param locale The locale
     *
     * @return true when the format matches; false otherwise
     */
    public static boolean matchFormat(@Nullable String date, @Nullable String format, @Nullable TimeZone zone, @Nullable Locale locale) {
        if (StringUtils.isAnyBlank(date, format)) {
            return false;
        }
        try {
            parseDate(date, format, zone, locale);
            return true;
        } catch (ParseException ignored) {
        }
        return false;
    }

    /**
     * Parses a date using the given format.
     *
     * The parse honors the requested time zone and locale.
     *
     * @param date The date or date text
     * @param format The date format pattern
     *
     * @return the parsed date
     *
     * @throws java.text.ParseException if the text cannot be parsed
     */
    @Nullable
    public static Date parseDate(@Nullable String date, @Nullable String format) throws ParseException {
        return parseDate(date, format, null, null);
    }

    /**
     * Parses a date using the given format.
     *
     * The parse honors the requested time zone and locale.
     *
     * @param date The date or date text
     * @param format The date format pattern
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the parsed date
     *
     * @throws java.text.ParseException if the text cannot be parsed
     */
    @Nullable
    public static Date parseDate(@Nullable String date, @Nullable String format, @Nullable TimeZone zone, @Nullable Locale locale) throws ParseException {
        return StringUtils.isAnyBlank(date, format) ? null : FastDateFormat.getInstance(format, zone, locale).parse(date);
    }

    /**
     * Parses a date without throwing parse exceptions.
     *
     * @param date The date or date text
     * @param format The date format pattern
     *
     * @return the parsed date, or null when parsing fails
     */
    @Nullable
    public static Date parseDateQuietly(@Nullable String date, @Nullable String format) {
        return parseDateQuietly(date, format, null, null);
    }

    /**
     * Parses a date without throwing parse exceptions.
     *
     * @param date The date or date text
     * @param format The date format pattern
     * @param zone The time zone
     * @param locale The locale
     *
     * @return the parsed date, or null when parsing fails
     */
    @Nullable
    public static Date parseDateQuietly(@Nullable String date, @Nullable String format, @Nullable TimeZone zone, @Nullable Locale locale) {
        try {
            return parseDate(date, format, zone, locale);
        } catch (ParseException ignored) {
        }
        return null;
    }

    /**
     * Parses a date using the first matching format.
     *
     * @param date The date or date text
     * @param formats The date format patterns
     *
     * @return the parsed date, or null when no format matches
     */
    @Nullable
    public static Date parseDateFormats(@Nullable String date, @Nullable String... formats) {
        return parseDateFormats(date, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Parses a date using the first matching format.
     *
     * @param date The date or date text
     * @param formats The date format patterns
     *
     * @return the parsed date, or null when no format matches
     */
    @Nullable
    public static Date parseDateFormats(@Nullable String date, @Nullable Collection<String> formats) {
        return parseDateFormats(date, null, null, formats);
    }

    /**
     * Parses a date using the first matching format.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     * @param formats The date format patterns
     *
     * @return the parsed date, or null when no format matches
     */
    @Nullable
    public static Date parseDateFormats(@Nullable String date, @Nullable TimeZone zone, @Nullable Locale locale, @Nullable String... formats) {
        return parseDateFormats(date, zone, locale, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Parses a date using the first matching format.
     *
     * @param date The date or date text
     * @param zone The time zone
     * @param locale The locale
     * @param formats The date format patterns
     *
     * @return the parsed date, or null when no format matches
     */
    @Nullable
    public static Date parseDateFormats(@Nullable String date, @Nullable TimeZone zone, @Nullable Locale locale, @Nullable Collection<String> formats) {
        if (StringUtils.isBlank(date) || CollectionPlainWraps.isEmpty(formats)) {
            return null;
        }
        return formats.stream().map(format -> parseDateQuietly(date, format, zone, locale)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Parses a date by guessing the format.
     *
     * @param date The date or date text
     *
     * @return the parsed date, or null when the format cannot be inferred
     */
    @Nullable
    public static Date parseDateGuessing(@Nullable String date) {
        return parseDateFormats(date, TemporalFormatCombo.DATE_FORMATS);
    }

    /**
     * Parses a date and time by guessing the format.
     *
     * @param dateTime The date and time text
     *
     * @return the parsed date and time, or null when the format cannot be inferred
     */
    @Nullable
    public static Date parseDateTimeGuessing(@Nullable String dateTime) {
        return parseDateFormats(dateTime, TemporalFormatCombo.DATETIME_FORMATS);
    }

    /**
     * Returns a date that plus amount with the given data field
     *
     * @param date The date, not null
     * @param field The calendar field type to add to
     * @param amount The amount to add, may be negative
     *
     * @return a date that plus amount with the given data field
     *
     * @see org.apache.commons.lang3.time.DateUtils#addYears
     * @see org.apache.commons.lang3.time.DateUtils#addMonths
     * @see org.apache.commons.lang3.time.DateUtils#addDays
     * @see org.apache.commons.lang3.time.DateUtils#addHours
     * @see org.apache.commons.lang3.time.DateUtils#addMinutes
     * @see org.apache.commons.lang3.time.DateUtils#addSeconds
     * @see org.apache.commons.lang3.time.DateUtils#addMilliseconds
     */
    @SuppressWarnings("MagicConstant")
    public static Date plusTemporal(@Nullable Date date, int field, int amount) {
        if (date == null || field < 0 || amount == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * Adds an amount to a field of the given date.
     *
     * @param date The date or date text
     * @param duration The duration to add
     *
     * @return the adjusted date, or null when the date is null
     */
    @Nullable
    public static Date plusTemporal(@Nullable Date date, @Nullable Duration duration) {
        return (duration == null || duration.isZero()) ? date : plusTemporal(date, Calendar.MILLISECOND, (int) duration.toMillis());
    }

    /**
     * Converts the date to epoch milliseconds.
     *
     * @param date The date or date text
     *
     * @return the epoch milliseconds, or null when the date is null
     */
    @Nullable
    public static Long toEpochMilli(@Nullable Date date) {
        return (date == null) ? null : date.getTime();
    }

    /**
     * Converts the date to epoch seconds.
     *
     * @param date The date or date text
     *
     * @return the epoch seconds, or null when the date is null
     */
    @Nullable
    public static Long toEpochSecond(@Nullable Date date) {
        return (date == null) ? null : date.getTime() / 1000L;
    }
}
