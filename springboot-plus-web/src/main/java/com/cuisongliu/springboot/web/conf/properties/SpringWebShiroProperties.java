package com.cuisongliu.springboot.web.conf.properties;
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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot 额外的配置文件
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-06 21:19
 */
@Configuration
@ConfigurationProperties(prefix = SpringWebShiroProperties.PROPERTIES_PREFIX)
public class SpringWebShiroProperties {
    public final static String PROPERTIES_PREFIX = "mars.web.shiro";

    /**
     * 是否开启RememberMe
     */
    private Boolean enableRememberMe = true;

    /**
     * 设置shiro为server
     */
    private Boolean enableServer = true;

    /**
     * server 模式下session超时时间
     */
    private long globalSessionTimeout = 1800000;

    /**
     * session验证
     */
    private String rememberMeCookieName = "rememberMe";

    /**
     * session超时
     */
    private int rememberMeCookieDays = 7;

    /**
     *  RememberMeCookieHttpOnly 默认为true
     */
    private Boolean rememberMeCookieHttpOnly = true;

    /**
     * 设置RememberMe key
     */
    private String rememberMeKey = "Z3VucwAAAAAAAAAAAAAAAA==";

    /**
     * shiro redis key
     */
    private String redisCacheKey = "shiro_redis#shiro:";

    public Boolean getEnableRememberMe() {
        return enableRememberMe;
    }

    public void setEnableRememberMe(Boolean enableRememberMe) {
        this.enableRememberMe = enableRememberMe;
    }

    public Boolean getEnableServer() {
        return enableServer;
    }

    public void setEnableServer(Boolean enableServer) {
        this.enableServer = enableServer;
    }

    public long getGlobalSessionTimeout() {
        return globalSessionTimeout;
    }

    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    public String getRememberMeCookieName() {
        return rememberMeCookieName;
    }

    public void setRememberMeCookieName(String rememberMeCookieName) {
        this.rememberMeCookieName = rememberMeCookieName;
    }

    public int getRememberMeCookieDays() {
        return rememberMeCookieDays;
    }

    public void setRememberMeCookieDays(int rememberMeCookieDays) {
        this.rememberMeCookieDays = rememberMeCookieDays;
    }

    public Boolean getRememberMeCookieHttpOnly() {
        return rememberMeCookieHttpOnly;
    }

    public void setRememberMeCookieHttpOnly(Boolean rememberMeCookieHttpOnly) {
        this.rememberMeCookieHttpOnly = rememberMeCookieHttpOnly;
    }

    public String getRememberMeKey() {
        return rememberMeKey;
    }

    public void setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    public String getRedisCacheKey() {
        return redisCacheKey;
    }

    public void setRedisCacheKey(String redisCacheKey) {
        this.redisCacheKey = redisCacheKey;
    }
}
