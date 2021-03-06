package com.cuisongliu.springboot.core.annotion;
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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 对于log的切点类
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-12-07 9:28
 */
@Aspect
@Component
public class LogAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    // Controller层切点
    @Pointcut("@annotation(com.cuisongliu.springboot.core.annotion.ControllerLog)")
    public void controllerAspect() {
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint){
        try {
            handle(joinPoint);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }
    }


    private void handle(JoinPoint point) throws Exception {
        //扩展日志方法
    }
}
