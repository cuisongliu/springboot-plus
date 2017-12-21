package com.cuisongliu.springboot.web.module.cache;
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

import com.cuisongliu.springboot.core.biz.BaseBiz;
import com.cuisongliu.springboot.core.util.CollectionUtil;
import com.cuisongliu.springboot.web.core.constant.SystemConstant;
import com.cuisongliu.springboot.web.module.dao.AuthorizationDAO;
import com.cuisongliu.springboot.web.module.dao.PermissionDAO;
import com.cuisongliu.springboot.web.module.dao.RoleDAO;
import com.cuisongliu.springboot.web.module.entity.App;
import com.cuisongliu.springboot.web.module.entity.Authorization;
import com.cuisongliu.springboot.web.module.entity.Permission;
import com.cuisongliu.springboot.web.module.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * app service
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-20 17:49
 */
@Service
@CacheConfig(cacheNames = CacheConstant.CACHE_AUTHORIZATION)
public class AuthorizationCache extends BaseBiz<Authorization> {

    @Autowired
    private AuthorizationDAO authorizationDAO;
    @Autowired
    private AppCache appCache;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PermissionDAO permissionDAO;

    @Cacheable(key = "T(String).valueOf(#appKey).concat('-').concat(#userId)")
    public Set<Long> selectRolesIdByUserId(String appKey,Long userId){
        App app = appCache.selectByAppKey(appKey);
        if (app == null){
            return Collections.EMPTY_SET;
        }
        Authorization auth = new Authorization();
        auth.setUserId(userId);
        auth.setAppId(app.getId());
        List<Authorization> authorizations = authorizationDAO.select(auth);
        Set<Long> roleIds = new LinkedHashSet<>();
        for (Authorization tempAuth : authorizations){
            roleIds.addAll(tempAuth.getRoleList());
        }
        return roleIds;
    }

    @Cacheable(key = "#roleIds")
    public Set<String> selectRolesNameByRoleIds(Set<Long> roleIds){
        List<Role> roleList = roleDAO .selectByIds(CollectionUtil.join(roleIds, SystemConstant.SPLIT));
        Set<String> roleStringSet = new LinkedHashSet<>();
        for (Role role:roleList){
            roleStringSet.add(role.getRole());
        }
        return roleStringSet;
    }

    @Cacheable(key = "#roleIds")
    public Set<String> selectPermissionsByRoleIds(Set<Long> roleIds){
        List<Role> roleList = roleDAO .selectByIds(CollectionUtil.join(roleIds, SystemConstant.SPLIT));
        Set<Long> permissionsIds = new LinkedHashSet<>();
        for (Role role:roleList){
            permissionsIds.addAll(role.getPermissionList());
        }
        List<Permission> permissionList = permissionDAO.selectByIds(CollectionUtil.join(permissionsIds, SystemConstant.SPLIT));
        Set<String> permissions = new LinkedHashSet<>();
        for (Permission p:permissionList){
            permissions.add(p.getPermission());
        }
        return permissions;
    }
}
