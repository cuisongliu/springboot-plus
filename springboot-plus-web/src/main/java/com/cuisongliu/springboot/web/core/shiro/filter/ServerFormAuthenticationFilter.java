package com.cuisongliu.springboot.web.core.shiro.filter;

import com.cuisongliu.springboot.web.core.constant.SystemConstant;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 服务端验证帐号和密码
 * <p>User: Zhang Kaitao
 * <p>Date: 14-3-16
 * <p>Version: 1.0
 * @author jerry
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {



    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String fallbackUrl = (String) getSubject(request, response)
                .getSession().getAttribute(SystemConstant.FALLBACK_URL);
        if(StringUtils.isEmpty(fallbackUrl)) {
            fallbackUrl = getSuccessUrl();
        }
        WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
    }

}
