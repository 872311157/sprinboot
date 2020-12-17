package com.example.springboot.demo.util.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密
 */
public class AESUtils {
    /**
     * AES加密
     * @param key
     * @param content
     * @param IV
     * @return
     */
    public static String AESencryptData(String content, String key, String IV) {
        byte[] encryptedBytes = new byte[0];
        // 判断Key是否正确
        if (key == null)
        {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (key.length() != 16)
        {
            System.out.print("Key长度不是16位");
            return null;
        }
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = key.getBytes("ASCII");
            //根据字节数组生成AES密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            //使用CBC工作模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
            // 根据指定算法AES自成密码器   AES/CBC/PKCS5Padding-加密方式/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            //根据密码器的初始化方式--加密：将数据加密
            encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * @param enStr
     * @param _key
     * @param IV
     * @return
     */
    public static String AESdecrypt(String enStr, String _key, String IV)
    {
        // 判断Key是否正确
        if (_key == null)
        {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (_key.length() != 16)
        {
            System.out.print("Key长度不是16位");
            return null;
        }
        try
        {
            byte[] raw = _key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(enStr);// 先用bAES64解密

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e)
        {
            return null;
        }
    }
}
