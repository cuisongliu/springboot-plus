package com.cuisongliu.springboot.shiro.support.entity;

import java.util.Set;

/**
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 * @author jerry
 */
//TODO 继承User
public class UserInfo  {

//    public UserInfo(User user){
//        BeanUtils.copyProperties(user,this);
//    }

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
