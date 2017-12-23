package com.cuisongliu.springboot.shiro.support.module.po;

import com.cuisongliu.springboot.core.util.CollectionUtil;
import com.cuisongliu.springboot.core.util.StringUtil;
import com.cuisongliu.springboot.shiro.support.constant.ShiroConstant;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 * @author cuijinrui
 * @since 2017-07-11
 */
@Table(name="s_authorization")
public class Authorization implements Serializable {
    /**
     *  主键
     */
    @Id
    private Long id;
    /**
     *  user id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     *  app id
     */
    @Column(name = "app_id")
    private Long appId;
    /**
     *  角色列表
     */
    @Column(name = "role_ids")
    private String roleIds;
    /**
     * 角色列表
     */
    @Transient
    private List<Long> roleList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getRoleList() {
        List<Long> roleListSuper = new ArrayList<>();
        if(StringUtil.isNotEmpty(this.getRoleIds())){
            String[] roleIdStr = this.getRoleIds().split(ShiroConstant.SPLIT);
            for(String roleId : roleIdStr) {
                if(StringUtil.isEmpty(roleId)) {
                    continue;
                }
                roleListSuper.add(Long.valueOf(roleId));
            }
        }
        this.roleList= roleListSuper;
        return this.roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleIds = CollectionUtil.join(roleList, ShiroConstant.SPLIT);
        this.roleList = roleList;
    }


}
