package com.zxf.common;

import java.util.function.Supplier;

/**
 * 惰性加载对象
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

    @Override
    public synchronized T get() {
        if (value != null){
            return this.value;
        }
        this.value = doLoad();
        return this.value;
    }
}
