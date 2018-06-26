package com.util.msf.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:40
 * @Description: 实现新旧日期格式转换
 */
public class T {

    public static final ZoneId ZONEID = ZoneId.systemDefault();

    /**
     * 构建新的日期时间
     *
     * @param date
     * @return
     * @see LocalDate#from(TemporalAccessor)
     * @see LocalDateTime#from(TemporalAccessor)
     */
    public static Temporal local(Date date) {
        return date.toInstant().atZone(ZONEID);
    }

    /**
     * 构建新的日期时间
     *
     * @param date
     * @return
     */
    public static LocalDate localDate(Date date) {
        return LocalDate.from(local(date));
    }

    /**
     * 构建新的日期时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime localDateTime(Date date) {
        return LocalDateTime.from(local(date));
    }

    /**
     * 构建老的日期时间
     *
     * @param dateTime
     * @return
     * @see Date#from(Instant)
     */
    public static Instant instant(LocalDateTime dateTime) {
        return dateTime.atZone(ZONEID).toInstant();
    }

    /**
     * 构建老的日期时间，时分秒为00:00:00
     *
     * @param dateTime
     * @return
     * @see Date#from(Instant)
     */
    public static Instant instant(LocalDate dateTime) {
        return dateTime.atStartOfDay(ZONEID).toInstant();
    }

    /**
     * 构建老的日期时间
     *
     * @param dateTime
     * @return
     * @see Date#from(Instant)
     */
    public static Date date(LocalDateTime dateTime) {
        return Date.from(instant(dateTime));
    }

    /**
     * 构建老的日期时间，时分秒为00:00:00
     *
     * @param dateTime
     * @return
     * @see Date#from(Instant)
     */
    public static Date date(LocalDate dateTime) {
        return Date.from(instant(dateTime));
    }

    /**
     * 构建老的日期时间
     *
     * @param dateTime
     * @param hour
     * @param minute
     * @param second
     * @return
     * @see Date#from(Instant)
     */
    public static Date date(LocalDate dateTime, int hour, int minute, int second) {
        return Date.from(instant(dateTime.atTime(hour, minute, second)));
    }

    /**
     * 设置为一天的开始时间，时分秒为00:00:00
     *
     * @param date
     * @return
     */
    public static Date startOfDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 设置为一天的结束时间，时分秒23:59:59
     *
     * @param date
     * @return
     */
    public static Date endOfDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
