package com.cuisongliu.springboot.web.module.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * APP
 * </p>
 *
 * @author cuijinrui
 * @since 2017-07-11
 */
@Table(name="s_app")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@Id
	private Long id;
    /**
     * 系统名称
     */
	private String name;
    /**
     *  系统密钥Key
     */
	@Column(name = "app_key")
	private String appKey;
	/**
	 *  系统网址
	 */
	@Column(name = "http_local")
	private String httpLocal;
    /**
     *  是否可用
     */
	private Boolean available =Boolean.FALSE;

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

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getHttpLocal() {
		return httpLocal;
	}

	public void setHttpLocal(String httpLocal) {
		this.httpLocal = httpLocal;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
}
