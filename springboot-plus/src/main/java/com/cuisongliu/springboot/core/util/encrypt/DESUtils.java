package com.cuisongliu.springboot.core.util.encrypt;

import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class DESUtils {

    public static final Logger LOGGER = Logger.getLogger(DESUtils.class);
	
    private final static String DES = "DES";

    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
    	Cipher cipher = null;
    	try {  
    	    cipher = Cipher.getInstance(DES);
    	    cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
    	      
    	} catch (NoSuchAlgorithmException e) {
            LOGGER.error("",e);
    	} catch (NoSuchPaddingException e) {
            LOGGER.error("",e);
    	}catch(InvalidKeyException e){
            LOGGER.error("",e);
    	} catch (InvalidKeySpecException e) {
            LOGGER.error("",e);
		}
    	  
    	try {  
    	    // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，  
    	    // 不能把加密后的字节数组直接转换成字符串  
            byte[] buf = new byte[0];
            if (cipher != null) {
                buf = cipher.doFinal(data.getBytes());
            }

            return Base64Utils.encode(buf);
    	      
    	} catch (IllegalBlockSizeException e) {
            LOGGER.error("",e);
    	    throw new Exception("IllegalBlockSizeException", e);
    	} catch (BadPaddingException e) {
            LOGGER.error("",e);
    	    throw new Exception("BadPaddingException", e);
    	}  

    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {

    	Cipher cipher = null;
     try {  
         cipher = Cipher.getInstance(DES);
         cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
           
     } catch (NoSuchAlgorithmException e) {
         throw new Exception("NoSuchAlgorithmException", e);
     } catch (NoSuchPaddingException e) {
         throw new Exception("NoSuchPaddingException", e);
     }catch(InvalidKeyException e){
         throw new Exception("InvalidKeyException", e);
           
     }  
       
     try {  
           
         byte[] buf = cipher.doFinal(Base64Utils.decode(data.toCharArray()));  
           
         return new String(buf);
           
     } catch (IllegalBlockSizeException e) {
         throw new Exception("IllegalBlockSizeException", e);
     } catch (BadPaddingException e) {
         throw new Exception("BadPaddingException", e);
     }  

    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    
    
    /** 
     * 获得密钥 
     *  
     * @param secretKey 
     * @return 
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */  
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException,InvalidKeyException,InvalidKeySpecException {
          
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);  
        return keyFactory.generateSecret(keySpec);  
    }  

    
    	    
    
    static class Base64Utils {  	  
        static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();  
        static private byte[] codes = new byte[256];  
        static {  
            for (int i = 0; i < 256; i++)  
                codes[i] = -1;  
            for (int i = 'A'; i <= 'Z'; i++)  
                codes[i] = (byte) (i - 'A');  
            for (int i = 'a'; i <= 'z'; i++)  
                codes[i] = (byte) (26 + i - 'a');  
            for (int i = '0'; i <= '9'; i++)  
                codes[i] = (byte) (52 + i - '0');  
            codes['+'] = 62;  
            codes['/'] = 63;  
        }  
          
        /** 
         * 将原始数据编码为base64编码 
         */  
        static public String encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];  
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {  
                boolean quad = false;  
                boolean trip = false;  
                int val = (0xFF & (int) data[i]);  
                val <<= 8;  
                if ((i + 1) < data.length) {  
                    val |= (0xFF & (int) data[i + 1]);  
                    trip = true;  
                }  
                val <<= 8;  
                if ((i + 2) < data.length) {  
                    val |= (0xFF & (int) data[i + 2]);  
                    quad = true;  
                }  
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];  
                val >>= 6;  
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];  
                val >>= 6;  
                out[index + 1] = alphabet[val & 0x3F];  
                val >>= 6;  
                out[index + 0] = alphabet[val & 0x3F];  
            }  
              
            return new String(out);
        }  
  
        /** 
         * 将base64编码的数据解码成原始数据 
         */  
        static public byte[] decode(char[] data) {  
            int len = ((data.length + 3) / 4) * 3;  
            if (data.length > 0 && data[data.length - 1] == '=')  
                --len;  
            if (data.length > 1 && data[data.length - 2] == '=')  
                --len;  
            byte[] out = new byte[len];  
            int shift = 0;  
            int accum = 0;  
            int index = 0;  
            for (int ix = 0; ix < data.length; ix++) {  
                int value = codes[data[ix] & 0xFF];  
                if (value >= 0) {  
                    accum <<= 6;  
                    shift += 6;  
                    accum |= value;  
                    if (shift >= 8) {  
                        shift -= 8;  
                        out[index++] = (byte) ((accum >> shift) & 0xff);  
                    }  
                }  
            }  
            if (index != out.length)  
                throw new Error("miscalculated data length!");
            return out;  
        }  
    }  
	

}
