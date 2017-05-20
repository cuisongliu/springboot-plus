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


import com.cuisongliu.springboot.annotation.FieldType;
import com.cuisongliu.springboot.annotation.Validation;
import com.cuisongliu.springboot.annotation.ValidationNum;
import com.cuisongliu.springboot.annotation.ValidationStr;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射工具类
 */
public class ReflectionUtil {
    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        try {
            if (null != method) {

                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @return 父类中的属性对象
     */

    public static List<Field> getDeclaredFieldNames(Class clazz) {
        Set<Field> fieldSet = new HashSet<Field>();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return new ArrayList<Field>(fieldSet);
    }

    public static String isValidation(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            //字段不存在
            return "";
        } else {
           com.cuisongliu.springboot.annotation.Field f = field.getAnnotation(com.cuisongliu.springboot.annotation.Field.class);
            if (f != null) {
                String name = f.name();//名称
                Class clazz = field.getClass();
                FieldType type = f.value();
                if (type == FieldType.NOTNULLABLE) {
                    String tips = "不能为空";
                    if (clazz.equals(String.class)) {
                        //字符串
                        String str = getFieldValue(object, fieldName, String.class);
                        if (StringUtil.isEmpty(str)) return name + tips;
                    } else if (clazz.equals(List.class)) {
                        //数组
                        List list = getFieldValue(object, fieldName, List.class);
                        if (list == null || list.isEmpty()) return name + tips;
                    } else {
                        Object obj = getFieldValue(object, fieldName);
                        if (obj == null) return name + tips;
                    }

                }
                Validation val = field.getAnnotation(Validation.class);
                if(val !=null){
                    if (clazz.equals(String.class)) {
                        //字符串
                        String str = getFieldValue(object, fieldName, String.class);
                        ValidationStr fstr = field.getAnnotation(ValidationStr.class);
                        if (str.length() > fstr.maxlength()) return name + "的最大长度为" + fstr.maxlength() + ".";
                    } else if (clazz.equals(BigDecimal.class)) {
                        //数字
                        BigDecimal bg = getFieldValue(object, fieldName, BigDecimal.class);
                        ValidationNum fnum = field.getAnnotation(ValidationNum.class);
                        int integet = fnum.integer();
                        int floater = fnum.floater();
                        if (numberValidation(bg, integet, floater))
                            return name + "最多输入" + integet + "位整数和" + floater + "位小数.";
                    }
                }
            }
        }
        return "";
    }

    private static boolean numberValidation(BigDecimal bigDecimal, int integer, int floater) {
        Pattern p = Pattern.compile("^([1-9]\\d{0," + (integer - 1) + "}|0)(\\.\\d{1," + floater + "})?$");
        Matcher m = p.matcher(bigDecimal.toString() + "");
        return !m.matches();
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value) {

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        try {
            //抑制Java对其的检查
            field.setAccessible(true);
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (Exception e) {
           //e.printStackTrace();
        }

    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static Object getFieldValue(Object object, String fieldName) {
        try {
            //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
            Field field = getDeclaredField(object, fieldName);

            //抑制Java对其的检查
            field.setAccessible(true);
            //获取 object 中 field 所代表的属性值
            return field.get(object);

        } catch (Exception e) {
            //e.printStackTrace() ;
        }

        return null;
    }


    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @return : 父类中的属性值
     */

    public static Map<String,Object> objToMap(Object object) {
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            List<Field> fields = getDeclaredFieldNames(object.getClass());
            for (Field f:fields){
                //抑制Java对其的检查
                f.setAccessible(true);
                map.put(f.getName(),f.get(object));
            }
        } catch (Exception e) {
            //e.printStackTrace() ;
        }
        return map;
    }


    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static <T> T getFieldValue(Object object, String fieldName, Class<T> clazz) {
        try {
            //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
            Field field = getDeclaredField(object, fieldName);
            //抑制Java对其的检查
            field.setAccessible(true);
            //获取 object 中 field 所代表的属性值

            return (T) field.get(object);

        } catch (Exception e) {
            //e.printStackTrace() ;
        }

        return null;
    }


    public static Class<?> getClazz(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Exception e) {
        }
        return clazz;
    }


    public static Object newInstance(String className) {
        Class<?> clazz = getClazz(className);
        try {
            return clazz.newInstance();
        } catch (Exception e) {
        }
        return null;
    }

}
