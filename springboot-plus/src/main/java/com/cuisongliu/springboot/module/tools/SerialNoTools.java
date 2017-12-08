package com.cuisongliu.springboot.module.tools;
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 cuisongliu@qq.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.cuisongliu.springboot.core.biz.BaseBiz;
import com.cuisongliu.springboot.core.util.DateUtil;
import com.cuisongliu.springboot.core.util.StringUtil;
import com.cuisongliu.springboot.module.dao.SerialTypeDAO;
import com.cuisongliu.springboot.module.entity.SerialNo;
import com.cuisongliu.springboot.module.entity.SerialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 生成序列号的规则
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-07 23:31
 */
@Service
public class SerialNoTools extends BaseBiz<SerialNo> {
    @Autowired
    private SerialTypeDAO serialTypeDAO;

    @Transactional(rollbackFor = {Exception.class})
    public String generatorSerialNo(String serialTypeCode) {
        // 1. 查找满足条件的单据类型
        // 2. 添加前缀
        StringBuffer result = new StringBuffer();
        SerialType serialTypeQuery = new SerialType();
        serialTypeQuery.setSerialTypeCode(serialTypeCode);
        serialTypeQuery.setState(1);
        List<SerialType> listBillType = serialTypeDAO.select(serialTypeQuery);

        if (listBillType == null || listBillType.size() == 0)
            throw new RuntimeException("没有维护该单据信息:" + serialTypeCode);
        if (listBillType.size() > 1)
            throw new RuntimeException("返回多个结果集:" + serialTypeCode);
        SerialType serialType = listBillType.get(0);
        // 获取serialType的前缀
        result.append(serialType.getSerialTypePrefix());
        // 判断是否有时间
        if (StringUtil.notBlank(serialType.getTimestampFormat())) {
            String strDataFormat = serialType.getTimestampFormat();
            String strData = DateUtil.Date2String(new Date(), strDataFormat);
            if (StringUtil.notBlank(serialType.getSplit())) {
                result.append(serialType.getSplit());
            }
            result.append(strData);
        }
        if (serialType.getSerialLen() == null || serialType.getSerialLen() == 0) {
            // 无须序列号递增
            return result.toString();
        }
        String codeItem = result.toString();
        String serialNo = getBillSerial(serialType, codeItem);
        if (StringUtil.notBlank(serialType.getSplit())) {
            result.append(serialType.getSplit());
        }
        result.append(serialNo);
        return result.toString();
    }

    @Transactional(rollbackFor = {Exception.class})
    private String getBillSerial(SerialType serialType, String codePrefix) {
        Integer lSerial = 1;
        Integer lMaxSerial = StringUtil.getMaxSerial(serialType.getSerialLen().intValue());
        SerialNo serialNo = new SerialNo();
        serialNo.setSerialTypeId(serialType.getSerialTypeCode());
        serialNo.setCodePrefix(codePrefix);
        List<SerialNo> listSerial = this.list(serialNo);
        boolean exists = listSerial != null && listSerial.size() > 0;
        SerialNo serialNoTemp;
        if (exists) {
            // 如果存在则更新
            serialNoTemp = listSerial.get(0);
            lSerial = serialNoTemp.getNextSerialNo();
            if (lSerial >= lMaxSerial) {
                throw new RuntimeException("已经达到最大值");
            }
            serialNoTemp.setMaxSerialNo(lMaxSerial);
            serialNoTemp.setNextSerialNo(serialNoTemp.getNextSerialNo() + 1);
            this.updateEntity(serialNoTemp);
        } else {
            // 如果不存在则新增
            serialNoTemp = new SerialNo();
            serialNoTemp.setSerialTypeId(serialType.getSerialTypeCode());
            serialNoTemp.setCodePrefix(codePrefix);
            serialNoTemp.setMaxSerialNo(lMaxSerial);
            serialNoTemp.setBeginSerialNo(1);
            serialNoTemp.setNextSerialNo(2);
            lSerial = 1;
            this.save(serialNoTemp);
        }
        // 格式化
        return StringUtil.addZero(lSerial.toString(), serialType.getSerialLen());
    }
}
