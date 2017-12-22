package com.cuisongliu.springboot.core.cache.autoconfig;
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


import com.cuisongliu.springboot.core.cache.autoconfig.properties.SpringRedisCacheProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * redis缓存
 * spring.cache.type=Redis
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-07 19:24
 */
@Configuration
@EnableCaching
@ConditionalOnClass({ JedisConnectionFactory.class })
@AutoConfigureAfter(CacheAutoConfiguration.class)
@EnableConfigurationProperties({SpringRedisCacheProperties.class})
@ConditionalOnProperty(prefix = SpringRedisCacheProperties.PROPERTIES_PREFIX, name = "type",havingValue = "redis")
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private SpringRedisCacheProperties springWebCacheProperties;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }



    @Bean
    @ConditionalOnMissingBean(name = "cacheManager")
    @Override
    public CacheManager cacheManager() {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        setSerializer(template);
        template.afterPropertiesSet();
        RedisCacheManager cacheManager = new RedisCacheManager(template);
        cacheManager.setDefaultExpiration(springWebCacheProperties.getDefaultExpiration());
        cacheManager.setLoadRemoteCachesOnStartup(springWebCacheProperties.isLoadRemoteCachesOnStartup());
        cacheManager.setUsePrefix(springWebCacheProperties.isUsePrefix());
        cacheManager.setCachePrefix(new DefaultRedisCachePrefix(springWebCacheProperties.getCachePrefix()));
        cacheManager.setTransactionAware(springWebCacheProperties.isTransactionAware());
        return cacheManager;
    }

    @SuppressWarnings({"unchecked"})
    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
