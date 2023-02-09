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
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
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

    /**
     * 构建请求体
     * @param body json body
     * @return RequestBody
     */
    private static RequestBody buildRequestBody(JSONObject body){
        return FormBody.create(
                MediaType.parse("application/json"),
                body.toString()
        );
    }

    /**
     * builder
     * @param url 请求url
     * @param headers 请求头
     * @return
     */
    private static Request.Builder builder(String url, Map<String, String> headers){
        if (headers == null){
            headers = new HashMap<>();
        }
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers));
    }

    /**
     * builder
     * @param url 请求url
     * @param headers 请求头
     * @return
     */
    private static Request.Builder builder(HttpUrl url, Map<String, String> headers){
        if (headers == null){
            headers = new HashMap<>();
        }
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(headers));
    }

    /**
     * 构建delete请求
     * @param url 请求url
     * @param headers 请求头
     * @param requestBody 请求体
     * @return
     */
    private static Request buildDeleteRequest(String url, Map<String, String> headers , JSONObject requestBody){
        return builder(url, headers)
                .delete(buildRequestBody(requestBody))
                .build();
    }

    /**
     * 构建put请求
     * @param url 请求url
     * @param headers 请求头
     * @param requestBody 请求提
     * @return
     */
    private static Request buildPutRequest(String url, Map<String, String> headers , JSONObject requestBody){
        return builder(url, headers)
                .put(buildRequestBody(requestBody))
                .build();
    }

    /**
     * 构建post请求
     * @param url 请求url
     * @param headers 请求头
     * @param requestBody 请求提
     * @return
     */
    private static Request buildPostRequest(String url, Map<String, String> headers , JSONObject requestBody){
        return builder(url, headers)
                .post(buildRequestBody(requestBody))
                .build();
    }

    /**
     * 构建get请求
     * @param url 请求url
     * @param headers 请求头
     * @param queryParams url 参数
     * @return
     */
    private static Request buildGetRequest(String url, Map<String, String> headers, Map<String,String> queryParams){
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        queryParams.forEach(builder::addQueryParameter);
        return builder(builder.build(), headers)
                .get()
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
                log.error("fail request url:{}, request body:{}", call.request().url(),
                        JSON.toJSONString(call.request().body()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.debug("success request url:{}, response body:{}", call.request().url(),
                        JSON.toJSONString(response.body()));
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
                log.error("fail request url:{}, request body:{}", call.request().url(),
                        JSON.toJSONString(call.request().body()));
                if (failure != null) {
                    ((FailureHandler) failure).dealWith(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.debug("success request url:{}, response body:{}", call.request().url(),
                        JSON.toJSONString(response.body()));
                if (success != null){
                    ((SuccessHandler) success).dealWith(call, response);
                }
            }
        });
    }

    /**
     * Response body string transform JSON
     * @param response 响应体
     * @return JSON
     */
    private static JSON toJSON(Response response) throws IOException {
        String responseBody = response.body().string();
        return JSON.parseObject(responseBody);
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
        return toJSON(response);
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
    public static <T> T post(String url, Map<String, String> headers ,Object requestBody,
                             Class<T> tClass) throws IOException {
        JSONObject responseBody = (JSONObject) post(url, headers, (JSONObject) JSON.toJSON(requestBody));
        return responseBody.toJavaObject(tClass);
    }

    /**
     * 异步调用 ，启动另一个线程去调用处理
     * post 请求
     * @param url 请求地址
     * @param headers 请求头
     * @param requestBody 请求体
     */
    public static void postAsync(String url, Map<String, String> headers, Object requestBody) throws IOException {
        Request postRequest = buildPostRequest(url, headers, (JSONObject) JSON.toJSON(requestBody));
        sendRequestAsync(postRequest);
    }

    /**
     * 异步调用 ，启动另一个线程去调用处理
     * @param url 请求地址
     * @param headers 请求头
     * @param requestBody 请求体
     * @param success 成功调用完成的后置处理
     * @param failure 失败后调用的后置处理
     */
    public static void postAsync(String url, Map<String, String> headers, Object requestBody, Handler success,
                                 Handler failure) throws IOException {
        Request postRequest = buildPostRequest(url, headers, (JSONObject) JSON.toJSON(requestBody));
        sendRequestAsync(postRequest, success, failure);
    }

    /**
     * 同步 get 请求
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param queryParams url参数
     * @return JSON
     */
    public static JSON get(String url, Map<String, String> headers, Map<String,String> queryParams) throws IOException {
        Request getRequest = buildGetRequest(url, headers, queryParams);
        Response response = sendRequestSync(getRequest);
        if (response.body() == null){
            return new JSONObject();
        }
        return toJSON(response);
    }

    /**
     * 同步get请求
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param queryParams url参数
     * @param tClass 返回类型
     * @param <T> 泛型
     * @return
     * @throws IOException
     */
    public static <T> T get(String url, Map<String, String> headers, Map<String,String> queryParams,
                            Class<T> tClass) throws IOException {
        JSONObject responseBody = (JSONObject) get(url, headers, queryParams);
        return responseBody.toJavaObject(tClass);
    }

    /**
     * 异步 get 请求
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param queryParams url参数
     * @throws IOException
     */
    public static void getAsync(String url, Map<String, String> headers, Map<String,String> queryParams) throws IOException {
        Request getRequest = buildGetRequest(url, headers, queryParams);
        sendRequestAsync(getRequest);
    }

    /**
     * 异步 get 请求
     *
     * @param url 请求地址
     * @param headers 请求头
     * @param queryParams url参数
     * @param success 成功调用完成的后置处理
     * @param failure 失败后调用的后置处理
     * @throws IOException
     */
    public static void getAsync(String url, Map<String, String> headers, Map<String,String> queryParams, Handler success,
                                Handler failure) throws IOException {
        Request getRequest = buildGetRequest(url, headers, queryParams);
        sendRequestAsync(getRequest, success, failure);
    }

//    put
//    delete
//    sync
//    async
}
