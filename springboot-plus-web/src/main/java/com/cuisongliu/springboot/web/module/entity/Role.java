package com.cuisongliu.springboot.web.module.entity;

import com.cuisongliu.springboot.core.util.CollectionUtil;
import com.cuisongliu.springboot.core.util.StringUtil;
import com.cuisongliu.springboot.web.core.constant.SystemConstant;
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
	@Column(name = "resource_ids")
	private String resourceIds;
	/**
	 *  拥有的资源
	 */
	@Transient
	private List<Long> resourceList;

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

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<Long> getResourceList() {
		List<Long> resourceListSuper = new ArrayList<>();
		if(StringUtil.isNotEmpty(this.getResourceIds())){
			String[] resourceIdStr = this.getResourceIds().split(SystemConstant.ROLE_SPLIT);
			for(String resourceId : resourceIdStr) {
				if(StringUtils.isEmpty(resourceId)) {
					continue;
				}
				resourceListSuper.add(Long.valueOf(resourceId));
			}
		}
		this.resourceList= resourceListSuper;
		return this.resourceList;
	}

	public void setResourceList(List<Long> resourceList) {
		this.resourceIds=CollectionUtil.join(resourceList,SystemConstant.ROLE_SPLIT);
		this.resourceList = resourceList;
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
