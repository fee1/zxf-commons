package com.zxf.common.id;

import org.junit.jupiter.api.Test;


/**
 * @author zhuxiaofeng
 * @date 2023/1/2
 */
public class UUIDTest {

    @Test
    public void builder() {

        UUID uuid = new UUID();
        System.out.println(uuid.builder());

    }
}