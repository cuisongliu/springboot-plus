package com.cuisongliu.springboot.core.web.core.shiro.core;

import com.cuisongliu.springboot.core.web.module.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Set;

/**
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 * @author jerry
 */
public class UserInfo  extends User {

    public  UserInfo(User user){
        BeanUtils.copyProperties(user,this);
    }

    private Set<String> roles;
    private Set<String> permissions;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

}
