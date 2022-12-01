package com.zxf.common.utils;

import com.zxf.common.utils.handler.FailureHandler;
import com.zxf.common.utils.handler.SuccessHandler;
import com.zxf.common.utils.model.MethodExecuteMessageRequestDTO;
import com.zxf.common.utils.model.ResponseDTO;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

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
        OkHttpUtil.postAsync("http://localhost:8080/log/server/method/execute/log/add", null, methodExecuteMessageRequestDTO);
        //休眠，不然没有效果
        Thread.sleep(1000L);


        OkHttpUtil.postAsync("http://localhost:8080/log/server/method/execute/log/add", null, methodExecuteMessageRequestDTO, (SuccessHandler) (call, response) -> {
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

}