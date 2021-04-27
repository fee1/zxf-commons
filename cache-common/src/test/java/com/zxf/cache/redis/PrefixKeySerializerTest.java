package com.zxf.cache.redis;


import org.junit.Assert;
import org.junit.Test;

public class PrefixKeySerializerTest {

    @Test
    public void testSerializer(){
        String prefix = "prefix:";
        String value = "value";

        PrefixKeySerializer prefixKeySerializer = new PrefixKeySerializer(prefix);

        String serializeRes = new String(prefixKeySerializer.serialize(value));
        Assert.assertEquals(prefix+value, serializeRes);

        String deserializeRes = prefixKeySerializer.deserialize(serializeRes.getBytes());
        Assert.assertEquals(deserializeRes, value);

        System.out.println("serializeRes: "+serializeRes +"  deserializeRes: "+deserializeRes);
    }

}