package com.cuisongliu.springboot.module.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "s_serial_no")
public class SerialNo {
    @Id
    private Integer id;

    /**
     * 对应类型ID
     */
    @Column(name = "serial_type_id")
    private String serialTypeId;

    /**
     * 对应单据类型序列前缀
     */
    @Column(name = "code_prefix")
    private String codePrefix;

    /**
     * 开始序列号
     */
    @Column(name = "begin_serial_no")
    private Integer beginSerialNo;

    /**
     * 下个序列号
     */
    @Column(name = "next_serial_no")
    private Integer nextSerialNo;

    /**
     * 最大序列号
     */
    @Column(name = "max_serial_no")
    private Integer maxSerialNo;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取对应类型ID
     *
     * @return serial_type_id - 对应类型ID
     */
    public String getSerialTypeId() {
        return serialTypeId;
    }

    /**
     * 设置对应类型ID
     *
     * @param serialTypeId 对应类型ID
     */
    public void setSerialTypeId(String serialTypeId) {
        this.serialTypeId = serialTypeId;
    }

    /**
     * 获取对应单据类型序列前缀
     *
     * @return code_prefix - 对应单据类型序列前缀
     */
    public String getCodePrefix() {
        return codePrefix;
    }

    /**
     * 设置对应单据类型序列前缀
     *
     * @param codePrefix 对应单据类型序列前缀
     */
    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix == null ? null : codePrefix.trim();
    }

    /**
     * 获取开始序列号
     *
     * @return begin_serial_no - 开始序列号
     */
    public Integer getBeginSerialNo() {
        return beginSerialNo;
    }

    /**
     * 设置开始序列号
     *
     * @param beginSerialNo 开始序列号
     */
    public void setBeginSerialNo(Integer beginSerialNo) {
        this.beginSerialNo = beginSerialNo;
    }

    /**
     * 获取下个序列号
     *
     * @return next_serial_no - 下个序列号
     */
    public Integer getNextSerialNo() {
        return nextSerialNo;
    }

    /**
     * 设置下个序列号
     *
     * @param nextSerialNo 下个序列号
     */
    public void setNextSerialNo(Integer nextSerialNo) {
        this.nextSerialNo = nextSerialNo;
    }

    /**
     * 获取最大序列号
     *
     * @return max_serial_no - 最大序列号
     */
    public Integer getMaxSerialNo() {
        return maxSerialNo;
    }

    /**
     * 设置最大序列号
     *
     * @param maxSerialNo 最大序列号
     */
    public void setMaxSerialNo(Integer maxSerialNo) {
        this.maxSerialNo = maxSerialNo;
    }
}