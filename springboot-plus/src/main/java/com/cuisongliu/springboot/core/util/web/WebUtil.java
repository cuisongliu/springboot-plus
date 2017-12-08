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
package com.cuisongliu.springboot.core.util.web;

import com.alibaba.fastjson.JSON;
import com.cuisongliu.springboot.core.util.exception.UtilClassRuntimeException;
import com.cuisongliu.springboot.core.util.web.xss.XssHttpServletRequestWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Web操作工具类.
 *
 * @author   cuisongliu
 * @version  2016.10.25
 */
public final class WebUtil {

	/**
	 * 判断是否为IE浏览器
	 *
	 * @return
	 */
	public static boolean isMSIEFun() {
		String agent = getRequest().getHeader("User-Agent");
		boolean isMSIE = (agent != null && (agent.indexOf("MSIE") != -1 || agent.indexOf("rv:11") != -1 || agent.indexOf("Edge") != -1));   //增加判断IE11问题	增加edge浏览器问题
		return isMSIE;
	}


	/**
	 * 获取 HttpServletRequest
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}

	/**
	 * 获取 包装防Xss Sql注入的 HttpServletRequest
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return new XssHttpServletRequestWrapper(request);
	}
	/**
	 * 私有构造方法.
	 */
	private WebUtil() {}
	
	/**
	 * 获取客户端的真实IP地址.<br/>
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，那么取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * @param request .
	 * @return IP.
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       
	       if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip) && ip.contains(",")){
	    	   ip = ip.substring(0, ip.indexOf(",")); // 截取第一个
	       }
	       return ip;
	}

	/**
	 * 获取所有请求的值
	 */
	public static Map<String, String> getRequestParameters() {
		HashMap<String, String> values = new HashMap<>();
		HttpServletRequest request = getRequest();
		Enumeration enums = request.getParameterNames();
		while ( enums.hasMoreElements()){
			String paramName = (String) enums.nextElement();
			String paramValue = request.getParameter(paramName);
			values.put(paramName, paramValue);
		}
		return values;
	}


	/**
	 * 渲染json对象
	 */
	public static void renderJson(HttpServletResponse response, Object jsonObject) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(jsonObject));
		} catch (IOException e) {
			//TODO 异常CODE
			throw new UtilClassRuntimeException(e);
		}
	}

}
