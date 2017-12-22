/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 cuisongliu@qq.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cuisongliu.springboot.core.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * DateUtils.java
 * 日期时间工具
 */
public class DateUtil {

    /**
     * 缺省日期格式 yyyyMMdd
     */
    public static final String DEFAULT_DATE_FMT = "yyyyMMdd";


    /**
     * 缺省时间格式yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FMT2 = "yyyy-MM-dd";

    /**
     * 缺省时间格式 yyyy/MM/dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FMT = "yyyyMMddHHmmss";

    /**
     * 缺省时间格式 yyyy/MM/dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FMT1 = "yyyy/MM/dd HH:mm:ss";

    /**
     * 缺省时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FMT2 = "yyyy-MM-dd HH:mm:ss";


    /**
     * 全部时区名字
     */
    private static final List TimeZoneIds = Arrays.asList(TimeZone.getAvailableIDs());

    /**
     * 自定义时区缓存
     */
    private static final Map TimeZoneCache = new HashMap();

    /**
     * 北京时区
     */
    public static final TimeZone timeZoneBeijing = TimeZone.getTimeZone("Asia/Shanghai");


    /**
     * 转换日期毫秒数为缺省日期格式字符串
     *
     * @param date
     * @return string
     */
    public static String Date2String(long date) {
        return Date2String(new Date(date), DEFAULT_DATE_FMT, null);
    }

    /**
     * 转换日期毫秒数为缺省日期格式字符串
     *
     * @param date
     * @param timeZone
     * @return string
     */
    public static String Date2String(long date, TimeZone timeZone) {
        return Date2String(new Date(date), DEFAULT_DATE_FMT, timeZone);
    }

    /**
     * 转换日期为缺省日期格式字符串
     *
     * @param date
     * @return string
     */
    public static String Date2String(Date date) {
        return Date2String(date, DEFAULT_DATE_FMT, null);
    }

    /**
     * 转换日期为缺省日期格式字符串
     *
     * @param date
     * @param timeZone
     * @return string
     */
    public static String Date2String(Date date, TimeZone timeZone) {
        return Date2String(date, DEFAULT_DATE_FMT, timeZone);
    }

    /**
     * 转换日期毫秒数为缺省日期格式字符串
     *
     * @param date
     * @return string
     */
    public static String Time2String(long date) {
        return Date2String(new Date(date), DEFAULT_TIME_FMT2, null);
    }

    /**
     * 转换日期毫秒数为缺省日期格式字符串
     *
     * @param date
     * @param timeZone
     * @return string
     */
    public static String Time2String(long date, TimeZone timeZone) {
        return Date2String(new Date(date), DEFAULT_TIME_FMT1, timeZone);
    }

    /**
     * 转换日期为缺省日期格式字符串
     *
     * @param date
     * @return string
     */
    public static String Time2String(Date date) {
        return Date2String(date, DEFAULT_TIME_FMT2, null);
    }

    /**
     * 转换日期为缺省日期格式字符串
     *
     * @param date
     * @param timeZone
     * @return string
     */
    public static String Time2String(Date date, TimeZone timeZone) {
        return Date2String(date, DEFAULT_TIME_FMT1, timeZone);
    }

    /**
     * 转换日期为指定格式字符串
     *
     * @param date
     * @param format
     * @return string
     */
    public static String Date2String(Date date, String format) {
        return Date2String(date, format, null);

    }

    /**
     * 转换日期为指定格式字符串
     *
     * @param date
     * @param format
     * @param timeZone
     * @return string
     */
    public static String Date2String(Date date, String format, TimeZone timeZone) {
        if (date == null || format == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeZone != null)
            sdf.setTimeZone(timeZone);
        return sdf.format(date);

    }

    /**
     * 解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd, yyyy/MM/dd, yyyyMMddHHmm,
     * yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss.SSS 格式,
     * 其它方式结果不保证正确
     *
     * @param str
     * @return date
     */
    public static Date String2Date(String str) {
        return String2Date(str, (TimeZone) null);
    }

    /**
     * 解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd, yyyy/MM/dd, yyyyMMddHHmm,
     * yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss.SSS 格式,
     * 其它方式结果不保证正确
     *
     * @param str
     * @param timeZone
     * @return date
     */
    public static Date String2Date(String str, TimeZone timeZone) {
        if (str == null)
            return null;
        str = str.trim();
        if (Pattern.matches("^\\d{6}$", str))//(str.length() == 6)
            return String2Date(str, "yyMMdd", timeZone);
        if (Pattern.matches("^\\d{8}$", str))//(str.length() == 8)
            return String2Date(str, "yyyyMMdd", timeZone);
        //if (str.length() == 10) {
        if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$", str))//(str.indexOf("-") != -1)
            return String2Date(str, "yyyy-M-d", timeZone);

        if (Pattern.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$", str))//(str.indexOf("/") != -1)
            return String2Date(str, "yyyy/M/d", timeZone);
        //}

        if (Pattern.matches("^\\d{12}$", str))//(str.length() == 12)
            return String2Date(str, "yyyyMMddHHmm", timeZone);
        if (Pattern.matches("^\\d{14}$", str))//(str.length() == 14)
            return String2Date(str, "yyyyMMddHHmmss", timeZone);
        if (Pattern.matches("^\\d{17}$", str))//(str.length() == 17)
            return String2Date(str, "yyyyMMddHHmmssSSS", timeZone);
//		if (str.length() == 19) {
        if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", str))//(str.indexOf("-") != -1)
            return String2Date(str, "yyyy-M-d H:m:s", timeZone);
        if (Pattern.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", str))//(str.indexOf("/") != -1)
            return String2Date(str, "yyyy/M/d H:m:s", timeZone);
//		}

//		if (str.length() == 23) {
        if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$", str))//(str.indexOf("-") != -1)
            return String2Date(str, "yyyy-M-d H:m:s.SSS", timeZone);
        if (Pattern.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$", str))//(str.indexOf("/") != -1)
            return String2Date(str, "yyyy/M/d H:m:s.SSS", timeZone);
//		}

        try {
            return new SimpleDateFormat().parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("validation.date.parse_error");
        }
    }

    /**
     * 按指定方式解析日期时间
     *
     * @param str
     * @param format
     * @return date
     */
    public static Date String2Date(String str, String format) {
        return String2Date(str, format, null);
    }

    /**
     * 按指定方式解析日期时间
     *
     * @param str
     * @param format
     * @param timeZone
     * @return date
     */
    public static Date String2Date(String str, String format, TimeZone timeZone) {
        if (str == null)
            return null;
        if (format == null)
            //format = DEFAULT_DATE_FMT;
            format = DEFAULT_TIME_FMT2;
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        if (timeZone != null)
            fmt.setTimeZone(timeZone);
        try {
            return fmt.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("validation.date.parse_error", e);
        }
    }

    /**
     * 比较2个时间先后
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
     */
    public static int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * 按格式比较2个时间先后
     *
     * @param date1  时间1
     * @param date2  时间2
     * @param format 格式
     * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
     */
    public static int compare(Date date1, Date date2, String format) {
        return DateUtil.String2Date(DateUtil.Date2String(date1, format), format)
                .compareTo(DateUtil.String2Date(DateUtil.Date2String(date2, format), format));
    }

    /**
     * 把Date转为Timestamp
     */
    public static Timestamp date2Timestamp(Date adate) {
        return new Timestamp(adate.getTime());
    }


    /**
     * 校验日期字符串是否是有效的格式
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return boolean
     */
    public static boolean checkDateValid(String dateStr, String format) {
        Date d;
        try {
            d = DateUtil.String2Date(dateStr, format);
        } catch (Exception e) {
            return false;
        }
        return DateUtil.Date2String(d, format).equals(dateStr);
    }

    /**
     * 日期运算
     *
     * @param endDate
     * @param count
     * @return date
     */
    public static Date dateAddDay(Date endDate, int count) {
        if (endDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, count);
        endDate = cal.getTime();

        return endDate;
    }

    /**
     * 将日期转换为指定格式
     *
     * @param date
     * @param format 如 yyyy-MM-dd HH:mm:ss等
     * @return date
     */
    public static Date covertDateFormat(Date date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 根据差距的天数获取日期对象
     *
     * @param date Date 指定的日期对象
     * @param day  int 差距时间 <br>
     *             day > 0 表示获取指定日期day天之后的日期 <br>
     *             day < 0 表示获取指定日期day天之前的日期 <br>
     * @return Date
     */
    public static Date getDifferenceDateByDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DATE, day);
        return calendar.getTime();
    }


    /**
     * 日期转字符串
     *
     * @param date
     * @param type 如 yyyy-MM-dd HH:mm:ss等
     * @return string
     */
    public static String dateToString(Date date, String type) {

        if (date == null) return "";

        SimpleDateFormat sdf = new SimpleDateFormat(type);
        //sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String datestr = sdf.format(date);
        return datestr;
    }
}