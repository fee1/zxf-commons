package com.zxf.common.utils.handler;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
@FunctionalInterface
public interface SuccessHandler extends Handler {

    void dealWith(Call call, Response response);

}
