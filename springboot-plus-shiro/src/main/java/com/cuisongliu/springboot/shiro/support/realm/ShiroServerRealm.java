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
import org.apache.shiro.authc.*;

/**
 * shiro 认证工具
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-18 0:08
 */
public class ShiroServerRealm extends ShiroAbstractRealm {

    public ShiroServerRealm(SpringShiroProperties springShiroProperties,PasswordHelper passwordHelper) {
        super(springShiroProperties,passwordHelper);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        UserInfo user = userCache.selectUserInfoByUsername(springShiroProperties.getAppKey(),username);
        if(user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            //帐号锁定
            throw new LockedAccountException();
        }
        return passwordHelper.authInfo(user,this);
    }
}
