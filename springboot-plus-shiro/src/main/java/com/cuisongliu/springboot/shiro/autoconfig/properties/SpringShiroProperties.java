package com.cuisongliu.springboot.shiro.autoconfig.properties;
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

import java.util.HashMap;
import java.util.Map;

/**
 * spring boot 额外的配置文件
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-06 21:19
 */
@ConfigurationProperties(prefix = SpringShiroProperties.PROPERTIES_PREFIX)
public class SpringShiroProperties {
    public final static String PROPERTIES_PREFIX = "spring.shiro";
    /**
     *  设置APP_KEY
     */
    private String appKey = "shiro_redis";

    /**
     *  中心服务器的Key
     */
    private String appSuperKey = "shiro_redis";

    /**
     *  中心服务器的地址
     */
    private String appSuperLocal = "http://localhost";

    /**
     * md5循环次数
     */
    private int md5Iterations = 2;

    /**
     *  随机生成盐的位数 最好为偶数
     */
    private int saltLength = 4;

    /**
     *
     */
    private String dbPrefix = "sys";

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

    /**
     *  过滤filter
     */
    private Map<String,String> filterChainDefinitions = new HashMap<>();

    /**
     * 返回成功页面
     */
    private String successUrl = "/";
    /**
     * 返回登录页面
     */
    private String loginUrl = "/login";
    /**
     * 返回未授权页面
     */
    private String unauthorizedUrl = "/unauthorized";

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

    public Map<String, String> getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(Map<String, String> filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSuperKey() {
        return appSuperKey;
    }

    public void setAppSuperKey(String appSuperKey) {
        this.appSuperKey = appSuperKey;
    }

    public String getAppSuperLocal() {
        return appSuperLocal;
    }

    public void setAppSuperLocal(String appSuperLocal) {
        this.appSuperLocal = appSuperLocal;
    }

    public int getMd5Iterations() {
        return md5Iterations;
    }

    public void setMd5Iterations(int md5Iterations) {
        this.md5Iterations = md5Iterations;
    }

    public int getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(int saltLength) {
        this.saltLength = saltLength;
    }

    public String getDbPrefix() {
        return dbPrefix;
    }

    public void setDbPrefix(String dbPrefix) {
        this.dbPrefix = dbPrefix;
    }
}
