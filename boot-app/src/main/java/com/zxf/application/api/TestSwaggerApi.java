package com.zxf.application.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱晓峰
 */
@RestController
public class TestSwaggerApi {

    @GetMapping("/")
    public String swaggerTest(){
        return "swaggerUI";
    }

}
