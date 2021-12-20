package com.zxf.common.log;

import com.zxf.test.MvcBaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 请求日志测试类
 *
 * @author zhuxiaofeng
 * @date 2021/12/16
 */
public class WebLogAspectTest extends MvcBaseTest {

    @SneakyThrows
    @Test
    public void testGetMethod(){
        this.mockMvc.perform(MockMvcRequestBuilders.get("/test/get").param("name", "zxf"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print());
    }

    @SneakyThrows
    @Test
    public void testPostMethod(){
        this.mockMvc.perform(MockMvcRequestBuilders.post("/test/post").param("name", "zxf"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print());
    }

    @SneakyThrows
    @Test
    public void testPutMethod(){
        this.mockMvc.perform(MockMvcRequestBuilders.put("/test/put").param("name", "zxf"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print());
    }

    @SneakyThrows
    @Test
    public void testDeleteMethod(){
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/test/delete").param("name", "zxf"))
                .andExpect(ResultMatcher.matchAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")))
                .andDo(MockMvcResultHandlers.print());
    }

}