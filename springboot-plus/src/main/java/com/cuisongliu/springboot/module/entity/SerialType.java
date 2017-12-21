package com.cuisongliu.springboot.module.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "s_serial_type")
public class SerialType {
    @Id
    private Long id;

    /**
     * 编码唯一非空
     */
    @Column(name = "serial_type_code")
    private String serialTypeCode;

    /**
     * 前缀
     */
    @Column(name = "serial_type_prefix")
    private String serialTypePrefix;

    /**
     * 类型名字
     */
    @Column(name = "serial_type_name")
    private String serialTypeName;

    /**
     * 有效0无效-1
     */
    private Integer state;

    /**
     * 时间格式
     */
    @Column(name = "timestamp_format")
    private String timestampFormat;

    /**
     * 递增长度(几位数字)
     */
    @Column(name = "serial_len")
    private Integer serialLen;

    /**
     * 分隔符
     */
    private String split;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取编码唯一非空
     *
     * @return serial_type_code - 编码唯一非空
     */
    public String getSerialTypeCode() {
        return serialTypeCode;
    }

    /**
     * 设置编码唯一非空
     *
     * @param serialTypeCode 编码唯一非空
     */
    public void setSerialTypeCode(String serialTypeCode) {
        this.serialTypeCode = serialTypeCode == null ? null : serialTypeCode.trim();
    }

    /**
     * 获取前缀
     *
     * @return serial_type_prefix - 前缀
     */
    public String getSerialTypePrefix() {
        return serialTypePrefix;
    }

    /**
     * 设置前缀
     *
     * @param serialTypePrefix 前缀
     */
    public void setSerialTypePrefix(String serialTypePrefix) {
        this.serialTypePrefix = serialTypePrefix == null ? null : serialTypePrefix.trim();
    }

    /**
     * 获取类型名字
     *
     * @return serial_type_name - 类型名字
     */
    public String getSerialTypeName() {
        return serialTypeName;
    }

    /**
     * 设置类型名字
     *
     * @param serialTypeName 类型名字
     */
    public void setSerialTypeName(String serialTypeName) {
        this.serialTypeName = serialTypeName == null ? null : serialTypeName.trim();
    }

    /**
     * 获取有效0无效-1
     *
     * @return state - 有效0无效-1
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置有效0无效-1
     *
     * @param state 有效0无效-1
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取时间格式
     *
     * @return timestamp_format - 时间格式
     */
    public String getTimestampFormat() {
        return timestampFormat;
    }

    /**
     * 设置时间格式
     *
     * @param timestampFormat 时间格式
     */
    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat == null ? null : timestampFormat.trim();
    }

    /**
     * 获取递增长度(几位数字)
     *
     * @return serial_len - 递增长度(几位数字)
     */
    public Integer getSerialLen() {
        return serialLen;
    }

    /**
     * 设置递增长度(几位数字)
     *
     * @param serialLen 递增长度(几位数字)
     */
    public void setSerialLen(Integer serialLen) {
        this.serialLen = serialLen;
    }

    /**
     * 获取分隔符
     *
     * @return spilt - 分隔符
     */
    public String getSplit() {
        return split;
    }

    /**
     * 设置分隔符
     *
     * @param split 分隔符
     */
    public void setSplit(String split) {
        this.split = split == null ? null : split.trim();
    }
}