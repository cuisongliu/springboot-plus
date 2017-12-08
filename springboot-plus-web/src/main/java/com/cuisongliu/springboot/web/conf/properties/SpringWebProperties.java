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
@ConfigurationProperties(prefix = SpringWebProperties.PROPERTIES_PREFI)
public class SpringWebProperties {
    public final static String PROPERTIES_PREFI ="mars.web";

    /**
     * session验证
     */
    private int  sessionValidationInterval=0;

    /**
     * session超时
     */
    private int  sessionInvalidateTime=0;

    /**
     * 开启多机部署
     */
    private boolean enableMulti = true;
    /**
     * shiro redis key
     */
    private String shiroRedisKey = "shiro_redis_cache:";


    public int getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(int sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public int getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(int sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public String getShiroRedisKey() {
        return shiroRedisKey;
    }

    public void setShiroRedisKey(String shiroRedisKey) {
        this.shiroRedisKey = shiroRedisKey;
    }

    public boolean isEnableMulti() {
        return enableMulti;
    }

    public void setEnableMulti(boolean enableMulti) {
        this.enableMulti = enableMulti;
    }
}
