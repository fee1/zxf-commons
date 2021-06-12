package com.zxf.common.utils;

import java.util.function.Supplier;

/**
 * 惰性加载对象（懒汉式单例）
 * @author 朱晓峰
 */
public class LazyValue<T> implements Supplier<T> {

    private T value;

    private final Supplier<T> supplier;

    public LazyValue(Supplier<T> supplier){
        this.supplier = supplier;
    }

    public T doLoad(){
        this.value = supplier.get();
        return value;
    }

    //todo 性能稍微浪费
    @Override
    public T get() {
        if (value != null){
            return this.value;
        }
        synchronized (this){
            this.value = doLoad();
        }
        return this.value;
    }
}
