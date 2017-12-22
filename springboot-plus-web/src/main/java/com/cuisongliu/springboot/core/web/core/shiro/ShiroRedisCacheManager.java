package com.cuisongliu.springboot.core.web.core.shiro;
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

import com.cuisongliu.springboot.core.web.core.redis.RedisCache;
import com.cuisongliu.springboot.core.web.core.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * shiro redis manager
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-08 10:24
 */
public class ShiroRedisCacheManager implements CacheManager {
    private static final Logger logger = LoggerFactory
            .getLogger(RedisCacheManager.class);

    /**
     * fast lookup by name map
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    private RedisManager redisManager;
    /**
     * The Redis key prefix for caches
     */
    private String keyPrefix ;

    public ShiroRedisCacheManager(RedisManager redisManager, String keyPrefix) {
        this.redisManager = redisManager;
        this.keyPrefix = keyPrefix;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {

            // initialize the Redis manager instance
            redisManager.init();
            // create a new cache instance
            c = new RedisCache<K, V>(redisManager, keyPrefix);
            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }
}
