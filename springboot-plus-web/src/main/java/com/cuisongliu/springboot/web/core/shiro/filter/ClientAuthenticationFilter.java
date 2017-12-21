package com.cuisongliu.springboot.web.core.shiro.filter;
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

import com.cuisongliu.springboot.web.conf.properties.SpringWebProperties;
import com.cuisongliu.springboot.web.core.constant.SystemConstant;
import com.cuisongliu.springboot.web.module.cache.AppCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 客户端访问控制filter
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-20 14:47
 */
public class ClientAuthenticationFilter extends AuthenticationFilter{

    @Autowired
    private SpringWebProperties springWebProperties;

    @Autowired
    private AppCache appCache;

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        String backUrl = appCache.selectByAppKey(springWebProperties.getAppSuperKey()).getHttpLocal();
        saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));
        try {
            redirectToLogin(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute(SystemConstant.FALLBACK_URL, fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }
    private String getDefaultBackUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
        backUrl.append(contextPath);
        backUrl.append(getSuccessUrl());
        return backUrl.toString();
    }

    class ClientSavedRequest extends SavedRequest {
        private String scheme;
        private String domain;
        private int port;
        private String contextPath;
        private String backUrl;

        public ClientSavedRequest(HttpServletRequest request, String backUrl) {
            super(request);
            this.scheme = request.getScheme();
            this.domain = request.getServerName();
            this.port = request.getServerPort();
            this.backUrl = backUrl;
            this.contextPath = request.getContextPath();
        }

        @Override
        public String getRequestUrl() {
            String requestURI = getRequestURI();
            if(backUrl != null) {//1
                if(backUrl.toLowerCase().startsWith("http://") || backUrl.toLowerCase().startsWith("https://")) {
                    return backUrl;
                } else if(!backUrl.startsWith(contextPath)) {//2
                    requestURI = contextPath + backUrl;
                } else {//3
                    requestURI = backUrl;
                }
            }

            StringBuilder requestUrl = new StringBuilder(scheme);//4
            requestUrl.append("://");
            requestUrl.append(domain);//5
            //6
            if("http".equalsIgnoreCase(scheme) && port != 80) {
                requestUrl.append(":").append(String.valueOf(port));
            } else if("https".equalsIgnoreCase(scheme) && port != 443) {
                requestUrl.append(":").append(String.valueOf(port));
            }
            //7
            requestUrl.append(requestURI);
            //8
            if (backUrl == null && getQueryString() != null) {
                requestUrl.append("?").append(getQueryString());
            }
            return requestUrl.toString();
        }
    }
}
