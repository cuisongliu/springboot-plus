package com.cuisongliu.springboot.core.web.module.cache;
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

import com.cuisongliu.springboot.core.service.BaseService;
import com.cuisongliu.springboot.core.web.core.shiro.core.UserInfo;
import com.cuisongliu.springboot.core.web.module.dao.UserDAO;
import com.cuisongliu.springboot.core.web.module.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * app service
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-20 17:49
 */
@Service
@CacheConfig(cacheNames = CacheConstant.CACHE_USER)
public class UserCache extends BaseService<User> {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthorizationCache authorizationCache;

    @Cacheable(key = "T(String).valueOf(#appKey).concat('-').concat(#userName)")
    public UserInfo selectUserInfoByUsername(String appKey, String userName){
        User user = new User();
        user.setUsername(userName);
        user = userDAO.selectOne(user);
        UserInfo userInfo = null;
        if (user !=null){
            userInfo = new UserInfo(user);
            Set<Long> roleIdSet = authorizationCache.selectRolesIdByUserId(appKey,userInfo.getId());
            userInfo.setRoles(authorizationCache.selectRolesNameByRoleIds(roleIdSet));
            userInfo.setPermissions(authorizationCache.selectPermissionsByRoleIds(roleIdSet));
        }
        return userInfo;
    }

}
