package com.cuisongliu.springboot.core.web.module.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 资源
 * </p>
 *
 * @author cuijinrui
 * @since 2017-07-11
 */
@Table(name="s_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@Id
	private Long id;
    /**
     * 资源名称
     */
	private String name;

	/**
	 * 资源类型
	 */
	private String type;

	/**
	 * 资源路径
	 */
	private String url;

	/**
	 * 权限字符串
	 */
	private String permission;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 菜单排序号
	 */
	private Integer num;
	/**
	 *  父编号
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 *  父编号
	 */
	@Column(name = "parent_ids")
	private String parentIds;

	/**
	 *  是否可用
	 */
	private Boolean available = Boolean.FALSE;
	/**
	 * 备注
	 */
	private String tips;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
