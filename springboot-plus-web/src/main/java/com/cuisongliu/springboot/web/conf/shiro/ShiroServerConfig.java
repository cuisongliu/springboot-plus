package com.cuisongliu.springboot.web.conf.shiro;
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

import com.cuisongliu.springboot.web.conf.properties.SpringWebShiroProperties;
import com.cuisongliu.springboot.web.core.shiro.realm.ShiroAbstractRealm;
import com.cuisongliu.springboot.web.core.shiro.realm.ShiroServerRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro server config
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-18 15:07
 */
@Configuration
@EnableConfigurationProperties({SpringWebShiroProperties.class})
@ConditionalOnProperty(prefix = SpringWebShiroProperties.PROPERTIES_PREFIX, name = "enable-server",havingValue = "true")
public class ShiroServerConfig {
    @Autowired
    private SpringWebShiroProperties springWebShiroProperties;
    @Bean
    public ShiroAbstractRealm realm(){
        return  new ShiroServerRealm();
    }


}
