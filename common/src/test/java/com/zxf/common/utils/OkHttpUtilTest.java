package com.zxf.common.utils;

import com.alibaba.fastjson.JSON;
import com.zxf.common.utils.handler.FailureHandler;
import com.zxf.common.utils.handler.SuccessHandler;
import com.zxf.common.utils.model.MethodExecuteMessageRequestDTO;
import com.zxf.common.utils.model.ResponseDTO;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
public class OkHttpUtilTest {

    @SneakyThrows
    @Test
    public void postAsync() {
        MethodExecuteMessageRequestDTO methodExecuteMessageRequestDTO = new MethodExecuteMessageRequestDTO();
        methodExecuteMessageRequestDTO.setTraceId("test");
        methodExecuteMessageRequestDTO.setClassFullPath("test");
        methodExecuteMessageRequestDTO.setMethod("test");
        methodExecuteMessageRequestDTO.setArgs(new String[]{"1", "2"});
        methodExecuteMessageRequestDTO.setReturnObj("test");
        methodExecuteMessageRequestDTO.setCostTime(1000L);
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader", "test");
        OkHttpUtil.postAsync("http://localhost:8080/log/server/method/execute/log/add", headers, methodExecuteMessageRequestDTO);
        //休眠，不然没有效果
        Thread.sleep(1000L);


        OkHttpUtil.postAsync("http://localhost:8080/log/server/method/execute/log/add", headers, methodExecuteMessageRequestDTO, (SuccessHandler) (call, response) -> {
            System.out.println("成功调用了");
        }, (FailureHandler) (call, e) -> {
            System.out.println("失败调用了");
        });
        //休眠，不然没有效果
        Thread.sleep(1000L);
    }

    @SneakyThrows
    @Test
    public void post() {
        MethodExecuteMessageRequestDTO methodExecuteMessageRequestDTO = new MethodExecuteMessageRequestDTO();
        methodExecuteMessageRequestDTO.setTraceId("test");
        methodExecuteMessageRequestDTO.setClassFullPath("test");
        methodExecuteMessageRequestDTO.setMethod("test");
        methodExecuteMessageRequestDTO.setArgs(new String[]{"1", "2"});
        methodExecuteMessageRequestDTO.setReturnObj("test");
        methodExecuteMessageRequestDTO.setCostTime(1000L);
        ResponseDTO responseDTO = OkHttpUtil.post("http://localhost:8080/log/server/method/execute/log/add", null, methodExecuteMessageRequestDTO, ResponseDTO.class);
    }

    @SneakyThrows
    @Test
    public void get(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader", "test");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "xiaoming");
        queryParams.put("age", "18");
        JSON json = OkHttpUtil.get("http://localhost:8080/get/user", headers, queryParams);
        System.out.println(json);
    }

    @SneakyThrows
    @Test
    public void getAsync(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader", "test");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "xiaoming");
        queryParams.put("age", "18");
        OkHttpUtil.getAsync("http://localhost:8080/get/user", headers, queryParams);
        Thread.sleep(1000L);

        OkHttpUtil.getAsync("http://localhost:8080/get/user", headers, queryParams, (SuccessHandler) (call, response) -> {
            System.out.println("成功调用了");
        }, (FailureHandler) (call, e) -> {
            System.out.println("失败调用了");
        });
        Thread.sleep(1000L);
    }

}