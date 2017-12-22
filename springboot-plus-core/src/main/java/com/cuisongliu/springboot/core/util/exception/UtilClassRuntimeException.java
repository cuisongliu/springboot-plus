/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cuisongliu.springboot.core.util.exception;


import com.cuisongliu.springboot.core.util.StringUtil;

/**
 * 工具类初始化异常
 */
public class UtilClassRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 8247610319171014183L;

	public UtilClassRuntimeException(Throwable e) {
		super(e.getMessage(), e);
	}
	
	public UtilClassRuntimeException(String message) {
		super(message);
	}
	
	public UtilClassRuntimeException(String messageTemplate, Object... params) {
		super(StringUtil.format(messageTemplate, params));
	}
	
	public UtilClassRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public UtilClassRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
		super(StringUtil.format(messageTemplate, params), throwable);
	}
}
