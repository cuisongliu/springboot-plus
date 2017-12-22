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
package com.cuisongliu.springboot.core.util.web.xss;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Xss使用的替换方法
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-10-23 11:05
 */
public class XssUtil {

    /**
     * 对字符串数组进行过滤
     *
     * @param values
     */
    public static void cleanXSS(String[] values) {
        if (values != null) {
            for (int num = 0; num < values.length; num++) {
                values[num] = cleanXSS(values[num]);
            }
        }
    }

    /**
     * HTML过滤危险内容.
     *
     * @param value
     * @return string
     */
    public static  String cleanXSS(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        //不要对<,>,',"进行编码，避免长文本显示出问题
        // You'll need to remove the spaces from the html entities below
        /**
         * 使用一个 Whitelist 类用来对 HTML 文档进行过滤.<br/>
         * 参考：http://www.oschina.net/question/12_14127 .
         */
        return Jsoup.clean(value, Whitelist.relaxed()); // 最宽松的一个过滤方法
    }
}
