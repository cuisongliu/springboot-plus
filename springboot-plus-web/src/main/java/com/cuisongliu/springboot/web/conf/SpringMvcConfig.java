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

import com.cuisongliu.springboot.core.conf.web.SpringMvcAbstractConfig;
import com.cuisongliu.springboot.core.util.web.xss.XssFilter;
import com.cuisongliu.springboot.shiro.support.resolver.UserInfoMethodArgumentResolver;
import com.cuisongliu.springboot.web.listener.ConfigListener;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * spring abstract
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-06 21:05
 */
@MapperScan(basePackages = {"com.cuisongliu.springboot.web.module.dao"})
public abstract class SpringMvcConfig extends SpringMvcAbstractConfig {

    /**
     * addResourceHandlers 方法的映射关系
     * @return resource 映射关系
     */
    protected abstract Map<String,String> preResourceHandlers();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<String,String> resourceMap = preResourceHandlers();
        for (Map.Entry<String, String> entry:resourceMap.entrySet()){
            registry.addResourceHandler(entry.getKey()).addResourceLocations(entry.getValue());
        }
        super.addResourceHandlers(registry);
    }


    /**
     * 设置xssFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");// 忽略资源
        return filterRegistrationBean;
    }

    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");
        properties.put("kaptcha.textproducer.font.size", "45");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    /**
     * ConfigListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigListener> configListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new ConfigListener());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        super.addArgumentResolvers(argumentResolvers);
        //获取UserInfo的方法
        argumentResolvers.add(new UserInfoMethodArgumentResolver());
    }
}
