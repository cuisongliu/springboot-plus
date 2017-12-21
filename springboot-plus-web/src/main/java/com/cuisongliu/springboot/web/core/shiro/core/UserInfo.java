package com.cuisongliu.springboot.web.core.shiro.core;

import com.cuisongliu.springboot.web.module.entity.User;

import java.util.Set;

/**
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 * @author jerry
 */
public class UserInfo  extends User {
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
