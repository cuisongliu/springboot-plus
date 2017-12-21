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
import com.cuisongliu.springboot.web.module.dao.PermissionDAO;
import com.cuisongliu.springboot.web.module.dao.RoleDAO;
import com.cuisongliu.springboot.web.module.entity.Permission;
import com.cuisongliu.springboot.web.module.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
@CacheConfig(cacheNames = CacheConstant.CACHE_ROLE)
public class RoleCache extends BaseBiz<Role> {

    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PermissionDAO permissionDAO;

    @Cacheable(key = "#roles")
    public Set<String> selectPermissionsByRoles(Set<Long> roles){
        Set<Long> permissionIds = new LinkedHashSet<>();
        //获取所有的角色
        List<Role> roleList=  roleDAO.selectByIds(CollectionUtil.join(roles, SystemConstant.SPLIT));
        for (Role role : roleList){
            permissionIds.addAll(role.getPermissionList());
        }
        Set<String> permissionsResult = new LinkedHashSet<>();
        List<Permission> permissions = permissionDAO.selectByIds(CollectionUtil.join(permissionIds, SystemConstant.SPLIT));
        for (Permission p : permissions){
            permissionsResult.add(p.getPermission());
        }
        return permissionsResult;
    }
}
