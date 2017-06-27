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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 首字母大写
     *
     * @param src
     * @return string
     */
    public static String toFirstUppercase(String src) {
        byte[] ss = src.getBytes();
        if (ss[0] >= 97 && ss[0] <= 122) {
            ss[0] = (byte) (ss[0] - 32);
            return new String(ss);
        }
        return src;
    }

    /**
     * 首字母小写
     *
     * @param src
     * @return string
     */
    public static String toFirstLowerCase(String src) {
        byte[] ss = src.getBytes();
        if (ss[0] >= 65 && ss[0] <= 90) {
            ss[0] = (byte) (ss[0] + 32);
            return new String(ss);
        }
        return src;
    }

    /**
     * 字符串转int
     *
     * @return int
     */
    public static int StringToInt(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    /**
     * 查询条件判空
     *
     * @return boolean
     */
    public static boolean isEmpty(String param) {
        if (param == null) {
            return true;
        }
        return "".equals(param.trim());
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * 判断字符串是否为非空(包含null与"")
     *
     * @param str
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !(str == null || "".equals(str));
    }

    /**
     * 判断字符串是否为非空(包含null与"","    ")
     *
     * @param str
     * @return boolean
     */
    public static boolean isNotEmptyIgnoreBlank(String str) {
        return !(str == null || "".equals(str) || "".equals(str.trim()));
    }


    /**
     * 判断是否为int
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        boolean isInteger = false;
        try {
            new Integer(str);
            isInteger = true;
        } catch (Exception ex) {

        }
        return isInteger;
    }

    /**
     * 判断是否为long
     * @param str
     * @return boolean
     */
    public static boolean isLong(String str) {
        boolean isLong = false;
        try {
            new Long(str);
            isLong = true;
        } catch (Exception ex) {

        }
        return isLong;
    }

    /**
     * 驼峰格式转下划线方式
     *
     * @param str
     * @return string
     */
    public static String trans(String str) {

        List<Integer> record = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);

            if ((tmp <= 'Z') && (tmp >= 'A')) {
                record.add(i);//记录每个大写字母的位置
            }

        }
//        record.remove(0);//不需要加下划线设置

        str = str.toLowerCase();
        char[] charofstr = str.toCharArray();
        String[] t = new String[record.size()];
        for (int i = 0; i < record.size(); i++) {
            t[i] = "_" + charofstr[Integer.parseInt(record.get(i) + "")];//加“_”
        }
        String result = "";
        int flag = 0;
        for (int i = 0; i < str.length(); i++) {
            if ((flag < record.size()) && (i == Integer.parseInt(record.get(flag) + ""))) {
                result += t[flag];
                flag++;
            } else
                result += charofstr[i];
        }

        return result;
    }

    /**
     * 去掉换行空格tab等
     *
     * @param str
     * @return string
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * null转成空字符串-"";
     *
     * @param param
     * @return string
     */
    public static String null2EmptyString(Object param) {

        if (null == param) {
            param = "";
        }
        return param.toString();
    }

    /**
     * 文件大小显示 使用KB MB GB显示
     * @param fileSize 长度大小
     * @return string 返回的数据
     */
    public static String convertFileSizeToMB(Long fileSize) {
        if (null == fileSize) {
            return "";
        }
        double kbSize = fileSize / 1024.0;
        double mbSize = kbSize / 1024.0;
        String fileSizeToXB = "";
        if (kbSize > 1000) {
            fileSizeToXB = new DecimalFormat("#.00").format(mbSize) + "MB";
            if (fileSizeToXB.indexOf(".") > -1 && fileSizeToXB.indexOf(".") == 0) {
                fileSizeToXB = "0" + fileSizeToXB;
            }
        } else {
            //太小使用小单位显示
            if (fileSize < 1000) {
                //使用B
                fileSizeToXB = new DecimalFormat("#.00").format(fileSize) + "B";
                if (fileSizeToXB.indexOf(".") > -1 && fileSizeToXB.indexOf(".") == 0) {
                    fileSizeToXB = "0" + fileSizeToXB;
                }
            } else {
                fileSizeToXB = new DecimalFormat("#.00").format(kbSize) + "KB";
                if (fileSizeToXB.indexOf(".") > -1 && fileSizeToXB.indexOf(".") == 0) {
                    fileSizeToXB = "0" + fileSizeToXB;
                }
            }
        }
        return fileSizeToXB;
    }

    /**
     * 将字符串转成utf-8编码
     *
     * @param s
     * @return string
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 补0操作
     *
     * @param str
     * @param length
     * @return string
     */
    public static String addZero(String str, int length) {
        int num = str.length();
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < (length - num); i++) {
            buff.append("0");
        }
        buff.append(str);
        return buff.toString();
    }

    /**
     * 获取该位数最大值
     * @param len
     * @return
     */
    public static Integer getMaxSerial(int len) {
        String strVal = "9";

        for(int i = 1; i < len; ++i) {
            strVal = strVal + "9";
        }

        return Integer.valueOf(strVal);
    }
}
