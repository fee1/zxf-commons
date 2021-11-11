package com.zxf.common.utils;

import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;


/**
 * @author zhuxiaofeng
 * @date 2021/9/3
 */
public class SM2UtilTest {

    @Test
    public void encrypt() throws Exception{

        KeyPair keyPair = SM2Util.generateSm2KeyPair();
        //公钥  用来加密
        byte[] publicEncoded = keyPair.getPublic().getEncoded();
        String publicKey = new String(Base64Utils.encode(publicEncoded), StandardCharsets.UTF_8);
        System.out.println("公钥: " + publicKey);
        //私钥  用来解密
        byte[] privateEncoded = keyPair.getPrivate().getEncoded();
        String privateKey = new String(Base64Utils.encode(privateEncoded), StandardCharsets.UTF_8);
        System.out.println("私钥: " + privateKey);
        //明文
        String plaintext = "test";
        //加密
        byte[] ciphertext = Base64Utils.encode(SM2Util.encrypt(plaintext.getBytes(StandardCharsets.UTF_8), Base64Utils.decode(publicKey.getBytes(StandardCharsets.UTF_8))));
        //生成签名
        byte[] signature = Base64Utils.encode(SM2Util.sign(plaintext.getBytes(StandardCharsets.UTF_8),keyPair.getPrivate().getEncoded()));
        System.out.println("密文-ciphertext: " + new String(ciphertext, StandardCharsets.UTF_8));
        System.out.println("签名-signature: " + new String(signature, StandardCharsets.UTF_8));
        //解密
        String decodePlaintext = new String(SM2Util.decrypt(Base64Utils.decode(ciphertext), Base64Utils.decode(privateKey.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        //验签
        boolean result = SM2Util.verify(decodePlaintext.getBytes(StandardCharsets.UTF_8), Base64Utils.decode(signature),Base64Utils.decode(publicKey.getBytes(StandardCharsets.UTF_8)));
        System.out.println("plaintext: " + decodePlaintext);
        System.out.println("verify result: " + result);

    }

}