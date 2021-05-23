package com.zxf.common;

import java.util.Base64;

/**
 * @author 朱晓峰
 */
public class Base64Util {

    /**
     * base64加密
     * @param encoderStr 加密串
     * @return str
     */
    public static String encrypt(String encoderStr){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encoderStr.getBytes());
    }

    /**
     * base64解密
     * @param decryptStr 解密串
     * @return str
     */
    public static String decrypt(String decryptStr){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(decryptStr);
        return new String(bytes);
    }

}
