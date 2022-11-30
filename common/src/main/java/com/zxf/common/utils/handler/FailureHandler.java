package com.zxf.common.utils.handler;

import okhttp3.Call;

import java.io.IOException;

/**
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
@FunctionalInterface
public interface FailureHandler extends Handler {

    void dealWith(Call call, IOException e);

}
