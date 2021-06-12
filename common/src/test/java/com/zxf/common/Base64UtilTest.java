package com.zxf.common;

import com.zxf.common.utils.Base64Util;
import org.junit.jupiter.api.Test;

public class Base64UtilTest {

    @Test
    void encrypt() {
        String s = "abc123";
        String encryptS = Base64Util.encrypt(s);
        System.out.println(encryptS);
        String decryptS = Base64Util.decrypt(encryptS);
        System.out.println(decryptS);
    }

}