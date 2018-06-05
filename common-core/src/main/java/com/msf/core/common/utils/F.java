package com.msf.core.common.utils;

import com.msf.core.common.constant.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:34
 * @Description:
 */
public class F {

    private static Logger logger = LoggerFactory.getLogger(F.class);

    /**
     * @see Const#YYYYMMDDHHMMSS
     */
    public static final DateTimeFormatter YMDHMS_FORMAT = DateTimeFormatter.ofPattern(Const.YYYYMMDDHHMMSS);

    /**
     * @see Const#YYYYMMDD
     */
    public static final DateTimeFormatter YMD_FORMAT = DateTimeFormatter.ofPattern(Const.YYYYMMDD);

    /**
     * @see Const#HHMMSS
     */
    public static final DateTimeFormatter HMS_FORMAT = DateTimeFormatter.ofPattern(Const.HHMMSS);


    /**
     * @see Const#YYYYMMDDHH
     */
    public static final DateTimeFormatter YMDH_FORMAT = DateTimeFormatter.ofPattern(Const.YYYYMMDDHH);

    /**
     * @see Const#YYYYMMDDHHMM
     */
    public static final DateTimeFormatter YMDHM_FORMAT = DateTimeFormatter.ofPattern(Const.YYYYMMDDHHMM);

    /**
     * 解析时间字符串
     *
     * @param date
     * @return
     * @see Const#YYYYMMDDHHMMSS
     */
    public static Optional<Date> parse(String date) {
        return parse(date, Const.YYYYMMDDHHMMSS);
    }

    /**
     * 按照格式解析时间字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Optional<Date> parse(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return Optional.empty();
        }
        pattern = StringUtils.isBlank(pattern) ? Const.YYYYMMDDHHMMSS : pattern;
        try {
            return Optional.ofNullable(FastDateFormat.getInstance(pattern).parse(date));
        } catch (Exception e) {
            logger.error("解析时间字符串化异常，date={}, pattern={}", date, pattern, e);
        }
        return Optional.empty();
    }

    /**
     * 按照指定格式格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Optional<String> format(Date date, String pattern) {
        if (date == null) {
            return Optional.empty();
        }
        pattern = StringUtils.isBlank(pattern) ? Const.YYYYMMDDHHMMSS : pattern;
        return Optional.ofNullable(FastDateFormat.getInstance(pattern).format(date));
    }


    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static Optional<String> format(Date date) {
        return format(date, Const.YYYYMMDDHHMMSS);
    }

    /**
     * 格式化时间，格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static Optional<String> format(LocalDateTime dateTime) {
        return format(dateTime, Const.YYYYMMDDHHMMSS);
    }

    /**
     * 按照指定的格式来格式化时间
     *
     * @param dateTime
     * @param pattern
     * @return
     * @see Const
     */
    public static Optional<String> format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return Optional.empty();
        }
        pattern = StringUtils.isBlank(pattern) ? Const.YYYYMMDDHHMMSS : pattern;
        return Optional.of(dateTime.format(DateTimeFormatter.ofPattern(pattern)));
    }

    /**
     * 格式化日期，格式yyyy-MM-dd
     *
     * @param dateTime
     * @return
     */
    public static Optional<String> format(LocalDate dateTime) {
        if (dateTime == null) {
            return Optional.empty();
        }
        return Optional.of(dateTime.format(DateTimeFormatter.ofPattern(Const.YYYYMMDD)));
    }

    /**
     * @param date
     * @return
     * @Description 获取YYYY-MM-DD 格式日期
     */
    public static String nowDate(Date date) {
        return F.format(date, Const.YYYYMMDD).get();
    }

    /**
     * @param pattern 自定义格式字符串
     * @return
     * @Description 获取自定义格式日期/时间
     */
    public static String nowDate(String pattern) {
        return F.format(new Date(), pattern).get();
    }

    /**
     * @param date
     * @return
     * @Description 获取yyyy-MM-dd HH:mm:ss 格式时间
     */
    public static String nowDateTime(Date date) {
        return F.format(date, Const.YYYYMMDDHHMMSS).get();
    }
}
