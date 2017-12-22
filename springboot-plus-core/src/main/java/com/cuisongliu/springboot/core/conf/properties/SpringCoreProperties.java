package com.cuisongliu.springboot.core.conf.properties;
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

import com.cuisongliu.springboot.core.util.OSUtil;
import com.cuisongliu.springboot.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static com.cuisongliu.springboot.core.conf.properties.SpringCoreProperties.PROPERTIES_PREFI;

/**
 * spring boot 额外的配置文件
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-06 21:19
 */
@Configuration
@ConfigurationProperties(prefix = PROPERTIES_PREFI)
public class SpringCoreProperties {
    final static String PROPERTIES_PREFI ="mars.core";

    @Value("${spring.http.multipart.location}")
    private String fileUploadPath;

    private Boolean haveCreatePath = false;

    /**
     * 是否为开发模式
     */
    private Boolean enableDev = false;

    /**
     * 获取目录信息
     * @return
     */
    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (StringUtil.isEmpty(fileUploadPath)) {
            return OSUtil.getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public Boolean getEnableDev() {
        return enableDev;
    }

    public void setEnableDev(Boolean enableDev) {
        this.enableDev = enableDev;
    }
}
