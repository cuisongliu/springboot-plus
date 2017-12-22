package com.cuisongliu.springboot.shiro.support.resolver;

import com.cuisongliu.springboot.shiro.support.constant.ShiroConstant;
import com.cuisongliu.springboot.shiro.support.entity.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <p>用于绑定@FormModel的方法参数解析器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午5:01
 * <p>Version: 1.0
 * @author jerry
 */
public class UserInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return webRequest.getAttribute(ShiroConstant.CURRENT_USER, NativeWebRequest.SCOPE_REQUEST);

    }
}
