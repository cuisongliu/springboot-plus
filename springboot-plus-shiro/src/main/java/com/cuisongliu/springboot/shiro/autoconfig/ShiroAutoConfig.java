package com.cuisongliu.springboot.shiro.autoconfig;
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

import com.cuisongliu.springboot.shiro.autoconfig.properties.SpringShiroProperties;
import com.cuisongliu.springboot.shiro.support.filter.ClientAuthenticationFilter;
import com.cuisongliu.springboot.shiro.support.filter.ServerFormAuthenticationFilter;
import com.cuisongliu.springboot.shiro.support.filter.UserInfoFilter;
import com.cuisongliu.springboot.shiro.support.password.PasswordHelper;
import com.cuisongliu.springboot.shiro.support.realm.ShiroAbstractRealm;
import com.cuisongliu.springboot.shiro.support.realm.ShiroClientRealm;
import com.cuisongliu.springboot.shiro.support.realm.ShiroServerRealm;
import com.cuisongliu.springboot.shiro.support.redis.RedisManager;
import com.cuisongliu.springboot.shiro.support.redis.ShiroRedisCacheManager;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro config
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-08 10:23
 */
@Configuration
@EnableRedisHttpSession
@ConditionalOnClass({ShiroFilterFactoryBean.class })
@EnableConfigurationProperties({SpringShiroProperties.class})
@ComponentScan(basePackages = { "com.cuisongliu.springboot.shiro"})
@MapperScan(basePackages = {"com.cuisongliu.springboot.shiro.support.module.dao"})
public class ShiroAutoConfig {

    @Autowired
    private SpringShiroProperties springShiroProperties;

    @Value("${spring.aop.proxy-target-class:false}")
    private boolean proxyTargetClass;

    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Autowired
    private RedisProperties redisProperties;
    ///redis 相关配置  begin///
    @Bean
    public RedisManager redisManager(){
        return new RedisManager(redisHost,redisPort,redisTimeout,redisPassword);
    }
    /**
     * 缓存管理器 使用redis实现
     */
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(RedisManager redisManager){
        return new ShiroRedisCacheManager(redisManager, springShiroProperties.getRedisCacheKey());
    }
    ///redis 相关配置  end///
    /**
     * spring session管理器（多机环境）
     */
    @Bean
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    @Bean
    public PasswordHelper passwordHelper(){
        return  new PasswordHelper();
    }

    @Bean
    public ShiroAbstractRealm realm(PasswordHelper passwordHelper){
        ShiroAbstractRealm realm;
        if (springShiroProperties.getEnableServer()){
            realm = new ShiroServerRealm();
        }else {
            realm = new ShiroClientRealm();
        }
        realm.setPasswordHelper(passwordHelper);
        return  realm;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CacheManager cacheShiroManager, SessionManager sessionManager, ShiroAbstractRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheShiroManager);

        {
            if (springShiroProperties.getEnableRememberMe()){
                //SimpleCookie 设置
                SimpleCookie rememberMeCookie = new SimpleCookie(springShiroProperties.getRememberMeCookieName());
                rememberMeCookie.setHttpOnly(springShiroProperties.getRememberMeCookieHttpOnly());
                //默认7天
                rememberMeCookie.setMaxAge(springShiroProperties.getRememberMeCookieDays() * 24 * 60 * 60);
                //rememberMe管理器, cipherKey生成见{@code Base64Test.java}
                CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
                rememberMeManager.setCipherKey(Base64.decode(springShiroProperties.getRememberMeKey()));
                rememberMeManager.setCookie(rememberMeCookie);
                //设置rememberMe
                securityManager.setRememberMeManager(rememberMeManager);
            }
        }


        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(proxyTargetClass);
        return  defaultAdvisorAutoProxyCreator;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 获取userInfo的filter
     * @return
     */
    @Bean
    public UserInfoFilter userInfoFilter(){
        return new UserInfoFilter();
    }

    public ServerFormAuthenticationFilter serverAuthenticationFilter(){
        ServerFormAuthenticationFilter filter = new ServerFormAuthenticationFilter();
        if (springShiroProperties.getEnableRememberMe()){
            filter.setRememberMeParam(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        }
        filter.setUsernameParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        filter.setPasswordParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);
        return filter;
    }

    public ClientAuthenticationFilter clientAuthenticationFilter(){
        return  new ClientAuthenticationFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager webSecurityManager, UserInfoFilter userInfoFilter){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        Map<String,Filter> filterMap = new HashMap<>();
        if (springShiroProperties.getEnableServer()){
            filterMap.put("authc",serverAuthenticationFilter());
        }else {
            filterMap.put("authc",clientAuthenticationFilter());
        }
        filterMap.put("sysUser",userInfoFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        String urlPrefix= springShiroProperties.getAppSuperLocal();

        shiroFilterFactoryBean.setSuccessUrl(urlPrefix+springShiroProperties.getSuccessUrl());
        shiroFilterFactoryBean.setLoginUrl(urlPrefix+springShiroProperties.getLoginUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(urlPrefix+springShiroProperties.getUnauthorizedUrl());

        shiroFilterFactoryBean.setFilterChainDefinitionMap(springShiroProperties.getFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }
}
