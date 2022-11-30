package com.zxf.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxf.common.utils.handler.FailureHandler;
import com.zxf.common.utils.handler.Handler;
import com.zxf.common.utils.handler.SuccessHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

/**
 * okhttp
 *
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
@Slf4j
public class OkHttpUtil {

    private static final LazyValue<OkHttpClient> OK_HTTP_CLIENT_INST = new LazyValue<>(OkHttpClient::new);

    private static RequestBody buildRequestBody(JSONObject body){
        return FormBody.create(
                MediaType.parse("application/json"),
                body.toString()
        );
    }

    private static Request buildPostRequest(String url, Map<String, String> headers , JSONObject requestBody){
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(buildRequestBody(requestBody))
                .build();
    }

    /**
     * 同步调用，会阻塞
     * @param request 请求
     * @return response
     * @throws IOException
     */
    private static Response sendRequestSync(Request request) throws IOException {
        return OK_HTTP_CLIENT_INST.get().newCall(request).execute();
    }

    /**
     * 异步调用，不阻塞
     * @param request 请求
     * @return response
     * @throws IOException
     */
    private static void sendRequestAsync(Request request) throws IOException {
        OK_HTTP_CLIENT_INST.get().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("fail request url:{}, request body:{}", call.request().url(), JSON.toJSONString(call.request().body()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.debug("success request url:{}, response body:{}", call.request().url(), JSON.toJSONString(response.body()));
            }
        });
    }

    /**
     * 异步调用，不阻塞
     * @param request 请求
     * @param success 成功的处理
     * @param failure 失败的处理
     * @throws IOException
     */
    private static void sendRequestAsync(Request request, Handler success, Handler failure) throws IOException {
        OK_HTTP_CLIENT_INST.get().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("fail request url:{}, request body:{}", call.request().url(), JSON.toJSONString(call.request().body()));
                if (failure != null) {
                    ((FailureHandler) failure).dealWith(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.debug("success request url:{}, response body:{}", call.request().url(), JSON.toJSONString(response.body()));
                if (success != null){
                    ((SuccessHandler) success).dealWith(call, response);
                }
            }
        });
    }

    /**
     * 同步调用
     * post 请求
     * @param url 请求地址
     * @param headers 请求头
     * @param requestBody 请求体
     */
    public static JSON post(String url, Map<String, String> headers , JSONObject requestBody) throws IOException {
        Request postRequest = buildPostRequest(url, headers, requestBody);
        Response response = sendRequestSync(postRequest);
        if (response.body() == null) {
            return new JSONObject();
        }
        String responseBody = response.body().string();
        return JSON.parseObject(responseBody);
    }

    /**
     * 异步调用
     * post 请求
     * @param url 请求地址
     * @param requestBody 请求体
     */
    public static void postAsync(String url, Map<String, String> headers, JSONObject requestBody) throws IOException {
        Request postRequest = buildPostRequest(url, headers, requestBody);
        sendRequestAsync(postRequest);
    }

    /**
     * 同步调用
     * post 请求
     * 指定返回类型
     * @param url 请求地址
     * @param requestBody 请求体
     * @param tClass 返回类型
     * @param <T> 泛型
     * @return tClass
     * @throws IOException
     */
    public static <T> T post(String url, Map<String, String> headers ,Object requestBody, Class<T> tClass) throws IOException {
        JSONObject responseBody = (JSONObject) post(url, headers, (JSONObject) JSON.toJSON(requestBody));
        return responseBody.toJavaObject(tClass);
    }


}
