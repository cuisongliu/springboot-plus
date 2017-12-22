package com.cuisongliu.springboot.shiro.support.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author jerry
 */
public class SerializeUtil {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * io 序列
     */
    public static class IoSerialize {
        /**
         * 反序列化
         *
         * @param bytes
         * @return
         */
        public static Object deserialize(byte[] bytes) {

            Object result = null;

            if (isEmpty(bytes)) {
                return null;
            }

            try {
                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
                    try {
                        result = objectInputStream.readObject();
                    } catch (ClassNotFoundException ex) {
                        throw new Exception("Failed to deserialize object type", ex);
                    }
                } catch (Throwable ex) {
                    throw new Exception("Failed to deserialize", ex);
                }
            } catch (Exception e) {
                logger.error("Failed to deserialize", e);
            }
            return result;
        }


        /**
         * 序列化
         *
         * @param object
         * @return
         */
        public static byte[] serialize(Object object) {

            byte[] result = null;

            if (object == null) {
                return new byte[0];
            }
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
                try {
                    if (!(object instanceof Serializable)) {
                        throw new IllegalArgumentException(SerializeUtil.class.getSimpleName() + " requires a Serializable payload " +
                                "but received an object of type [" + object.getClass().getName() + "]");
                    }
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                    objectOutputStream.writeObject(object);
                    objectOutputStream.flush();
                    result = byteStream.toByteArray();
                } catch (Throwable ex) {
                    throw new Exception("Failed to serialize", ex);
                }
            } catch (Exception ex) {
                logger.error("Failed to serialize", ex);
            }
            return result;
        }
    }
    /**
     * io 序列
     */
    public static class FastJsonSerialize {
        /**
         * 反序列化
         *
         * @param bytes
         * @return
         */
        public static Object deserialize(byte[] bytes) {
            return JSON.parseObject(bytes, Object.class);
        }


        /**
         * 序列化
         *
         * @param object
         * @return
         */
        public static byte[] serialize(Object object) {
            String str = JSON.toJSONString(object);
            return  str.getBytes();
        }
    }

}
