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
package com.cuisongliu.springboot.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * collection util
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2017-05-22 9:51
 */
public class CollectionUtil {

    /**
     * null转成空列表
     *
     * @param param
     */
    public static List null2EmptyArrayList(Object param) {

        if (null == param) {
            param = new ArrayList();
        }
        return (List) param;
    }

    /**
     * 检查一个str是否在list<String>中出现
     *
     * @param str
     * @param list
     */
    public static boolean matchListValue(String str, List list) {
        if (EntityUtil.isNullOrEmpty(list)) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).toString())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 把数组用spilt分割组合成一个string
     * @param spilt
     * @param list
     */
    public static String arrayToString(String spilt  , Object... list){
        StringBuilder sb = new StringBuilder();
        for (Object obj:list) {
            sb.append(obj).append(spilt);
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     *  数组分页并组合成一个string
     * @param ary
     * @param subSize
     */
    public static String[] splitAry(Object[] ary, int subSize, String spilt) {
        int length = ary.length;//原数组长度
        int num = length/subSize;//
        num=length%subSize !=0 ?(num+1) :num;//新数组长度
        String[] result = new String[num];
        int rest = length%subSize;//剩余值
        //按照最大长度的小数组分组
        for (int j = 0; j < num ; j++) {
            String[] tempResult = new String[subSize];
            int d2 =subSize;
            //解决剩余值
            if(j ==num-1 && rest!=0){
                tempResult= new String[rest];
                d2=rest;
            }
            for (int i = 0; i < d2; i++) {
                tempResult[i] = ary[i + j * subSize].toString();
            }
            result[j]=arrayToString(spilt,tempResult);
        }
        return result;
    }
}
