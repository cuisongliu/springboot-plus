package com.cuisongliu.springboot.web.conf;
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

import com.cuisongliu.springboot.web.conf.properties.SpringWebProperties;
import com.cuisongliu.springboot.web.core.util.redis.RedisManager;
import com.cuisongliu.springboot.web.core.util.shiro.ShiroDbRealm;
import com.cuisongliu.springboot.web.core.util.shiro.ShiroRedisCacheManager;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * shiro config
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-08 10:23
 */
@Configuration
@EnableConfigurationProperties({SpringWebProperties.class,RedisProperties.class})
@EnableRedisHttpSession
public class ShiroConfig {
    /**
     * spring session管理器（多机环境）
     */
    @Bean
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    ///redis 相关配置  begin///
    @Bean
    public RedisManager redisManager(RedisProperties redisProperties){
        return new RedisManager(redisProperties.getHost(),redisProperties.getPort(),redisProperties.getTimeout(),redisProperties.getPassword());
    }
    /**
     * 缓存管理器 使用redis实现
     */
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(SpringWebProperties springWebProperties,RedisManager redisManager){
        return new ShiroRedisCacheManager(redisManager,springWebProperties.getShiroRedisKey());
    }
    ///redis 相关配置  end///

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie(SpringWebProperties springWebProperties) {
        SimpleCookie simpleCookie = new SimpleCookie(springWebProperties.getRememberMeCookieName());
        simpleCookie.setHttpOnly(true);
        //默认7天
        simpleCookie.setMaxAge(springWebProperties.getRememberMeCookieDays() * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }
    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }
    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheShiroManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroDbRealm());
        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

}
