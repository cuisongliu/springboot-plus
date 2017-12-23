package com.cuisongliu.springboot.shiro.support.realm;
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
import com.cuisongliu.springboot.shiro.support.module.dto.UserInfo;
import com.cuisongliu.springboot.shiro.support.password.PasswordHelper;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象的统一接口
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-18 15:12
 */
public abstract class ShiroAbstractRealm extends AuthorizingRealm{
    @Autowired
    protected SpringShiroProperties springShiroProperties;

    protected PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String appKey  = "";
        if (springShiroProperties.getEnableServer()){
            appKey  = springShiroProperties.getAppSuperKey();
        }else {
            appKey  = springShiroProperties.getAppKey();
        }
        //TODO USERINFO 缓存
        UserInfo userInfo = null;
//        UserInfo userInfo = userCache.selectUserInfoByUsername(appKey,username);
        authorizationInfo.setRoles(userInfo.getRoles());
        authorizationInfo.setStringPermissions(userInfo.getPermissions());
        return authorizationInfo;
    }

    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }
}
