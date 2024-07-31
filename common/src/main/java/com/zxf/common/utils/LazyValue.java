package com.zxf.common.utils;

import java.util.function.Supplier;

/**
 * 惰性加载对象（懒汉式单例）
 * @author 朱晓峰
 */
public class LazyValue<T> implements Supplier<T> {

    private volatile T value;

    private final Supplier<T> supplier;

    public LazyValue(Supplier<T> supplier){
        this.supplier = supplier;
    }

    public T doLoad(){
        this.value = this.supplier.get();
        return this.value;
    }

    @Override
    public T get() {
        if (this.value != null){
            return this.value;
        }
        synchronized (this){
            if (this.value == null) {
                this.value = doLoad();
            }
        }
        return this.value;
    }
}
