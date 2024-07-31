package com.zxf.application.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuxiaofeng
 * @date 2023/1/16
 */
@RestController
@Slf4j
public class TestApi {

    @GetMapping("get/string")
    @ApiOperation("测试入参")
    public String getString(@RequestParam String str){
        log.debug(str);
        return str;
    }

}
