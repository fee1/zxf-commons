package com.zxf.builder;

import com.zxf.BaseTest;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * es string 测试类
 * @author zxf
 */
public class EsQueryStringBuilderTest {

    /**
     * 简单查询
     */
    @Test
    public void simpleQString(){
        EsQueryStringBuilder esQueryStringBuilder = EsQueryStringBuilder.create();
        esQueryStringBuilder.createCriteria().andEq("ID", "123");
        String q = esQueryStringBuilder.build();
        assertEquals("(ID:123)", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andIn("ID", "123", "312");
        q = esQueryStringBuilder.build();
        assertEquals("(ID:(123,312))", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andNotIn("ID", "123", "312");
        q = esQueryStringBuilder.build();
        assertEquals("(NOT ID:(123,312))", q);
        esQueryStringBuilder.clear();
    }

    /**
     * 符合查询
     */
    @Test
    public void complexQString(){
        // (x1 and x2) or x3
        EsQueryStringBuilder esQueryStringBuilder = EsQueryStringBuilder.create();
        esQueryStringBuilder.createCriteria().andEq("NAME", "xiaoming").andGteEquals("AGE", 18);
        esQueryStringBuilder.or().andGteEquals("HEIGHT", 180);
        String q = esQueryStringBuilder.build();
        assertEquals("(NAME:xiaoming AND AGE:[18 TO *]) OR (HEIGHT:[180 TO *])", q);
        esQueryStringBuilder.clear();

        // (x1 or x2) and x3   ---->  x1 and x3 union x2 and x3
        esQueryStringBuilder.createCriteria().andEq("NAME", "xiaoming").andGteEquals("AGE", 18);
        esQueryStringBuilder.or().andGteEquals("HEIGHT", 180).andGteEquals("AGE", 18);
        q = esQueryStringBuilder.build();
        assertEquals("(NAME:xiaoming AND AGE:[18 TO *]) OR (HEIGHT:[180 TO *] AND AGE:[18 TO *])", q);
        esQueryStringBuilder.clear();

        // (x1 or x2) and (x3 or x4) ---->  (x1 or x2) and x3 union (x1 or x2) and x4
    }

}