package com.zxf.cache;

/**
 * 抽象类产品类
 * @author 朱晓峰
 */
public abstract class AbstractCache implements ICache{

    private String name;

    public final String getName(){
        return this.name;
    }



}
