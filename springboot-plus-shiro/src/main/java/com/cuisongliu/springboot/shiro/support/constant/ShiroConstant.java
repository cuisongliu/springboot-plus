package com.cuisongliu.springboot.shiro.support.constant;
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

/**
 * 系统常量
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-19 10:23
 */
public interface ShiroConstant {
    /**
     * 数据库的前缀
     */
    String DB_PREFIX = "sys";
    /**
     * 角色和资源的分隔符 逗号
     */
    String SPLIT =",";
    /**
     * 当前session的userInfo
     */
    String CURRENT_USER = "userInfo";
    /**
     *  客户端set
     *  服务端get
     */
    String FALLBACK_URL = "authc.fallbackUrl";
}
