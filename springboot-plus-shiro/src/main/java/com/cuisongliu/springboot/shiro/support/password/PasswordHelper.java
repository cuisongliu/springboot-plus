package com.cuisongliu.springboot.shiro.support.password;
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
import com.cuisongliu.springboot.shiro.support.realm.ShiroAbstractRealm;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * password 帮助类
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-24 0:12
 */
public class PasswordHelper {


    private final SpringShiroProperties springShiroProperties;

    private SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public PasswordHelper(SpringShiroProperties springShiroProperties) {
        this.springShiroProperties = springShiroProperties;
    }

    /**
     * 获取随机位数的字符串
     *
     * @author fengshuonan
     * @Date 2017/8/24 14:09
     */
    public String getPasswordSalt() {
       randomNumberGenerator.setDefaultNextBytesSize(springShiroProperties.getSaltLength()/2);
       return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(Md5Hash.ALGORITHM_NAME, credentials, salt, springShiroProperties.getMd5Iterations()).toString();
    }


    /**
     * shiro 认证工具类
     * @param userInfo shiro的储存对象
     * @param realm  realm对象
     * @return
     */
    public SimpleAuthenticationInfo authInfo(UserInfo userInfo,ShiroAbstractRealm realm){
        ByteSource credentialsSalt = new Md5Hash(userInfo.getUsername()+userInfo.getSalt());
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                credentialsSalt,//salt=username+salt
                realm.getName()  //realm name
        );
        return  authenticationInfo;
    }
}
