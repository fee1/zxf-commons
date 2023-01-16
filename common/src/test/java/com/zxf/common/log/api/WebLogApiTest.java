package com.zxf.common.log.api;

import com.zxf.common.log.WebLog;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志测试类
 *
 * @author zhuxiaofeng
 * @date 2021/12/16
 */
@RestController
@RequestMapping("test")
@EnableAspectJAutoProxy
public class WebLogApiTest {

    @GetMapping("get")
    @WebLog
    public String getMethod(@RequestParam String name){
        return String.format("{get:%s}", name);
    }

    @PostMapping("post")
    @WebLog
    public String postMethod(@RequestParam String name){
        return "post:" + name;
    }

    @PutMapping("put")
    @WebLog
    public String putMethod(@RequestParam String name){
        return "put:" + name;
    }

    @DeleteMapping("delete")
    @WebLog
    public String deleteMethod(@RequestParam String name){
        return "delete:" + name;
    }

}
