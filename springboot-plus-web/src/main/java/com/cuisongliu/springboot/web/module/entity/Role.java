package com.cuisongliu.springboot.web.module.entity;

import com.cuisongliu.springboot.core.util.CollectionUtil;
import com.cuisongliu.springboot.core.util.StringUtil;
import com.cuisongliu.springboot.web.constant.SystemConstant;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author cuijinrui
 * @since 2017-07-11
 */
@Table(name="s_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@Id
	private Long id;

	/**
	 * 角色标识 程序中判断使用,如"admin"
	 */
	private String role;

	/**
	 * 角色描述,UI界面显示使用
	 */
	private String description;
	/**
	 *  拥有的资源
	 */
	@Column(name = "permission_ids")
	private String permissionIds;
	/**
	 *  拥有的资源
	 */
	@Transient
	private List<Long> permissionList;

	/**
	 * 序号
	 */
	private Integer num;

	/**
	 *  是否可用
	 */
	private Boolean available =Boolean.FALSE;
	/**
	 * 提示
	 */
	private String tips;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<Long> getPermissionList() {
		List<Long> permissionListSuper = new ArrayList<>();
		if(StringUtil.isNotEmpty(this.getPermissionIds())){
			String[] permissionIdStr = this.getPermissionIds().split(SystemConstant.SPLIT);
			for(String permissionId : permissionIdStr) {
				if(StringUtils.isEmpty(permissionId)) {
					continue;
				}
				permissionListSuper.add(Long.valueOf(permissionId));
			}
		}
		this.permissionList= permissionListSuper;
		return this.permissionList;
	}

	public void setPermissionList(List<Long> permissionList) {
		this.permissionIds=CollectionUtil.join(permissionList,SystemConstant.SPLIT);
		this.permissionList = permissionList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
