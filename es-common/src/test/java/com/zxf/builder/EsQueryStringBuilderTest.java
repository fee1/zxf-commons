package com.zxf.builder;
import com.zxf.elastic.builder.EsQueryStringBuilder;
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
        assertEquals("(ID:(123 OR 312))", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andLeftMatch("NAME", "xiao");
        q = esQueryStringBuilder.build();
        assertEquals("(NAME:*xiao)", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andRightMatch("NAME", "xiao");
        q = esQueryStringBuilder.build();
        assertEquals("(NAME:xiao*)", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andMatch("NAME", "xiao");
        q = esQueryStringBuilder.build();
        assertEquals("(NAME:*xiao*)", q);
        esQueryStringBuilder.clear();
    }

    /**
     * 复合查询
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

    @Test
    public void rangeQString(){
        EsQueryStringBuilder esQueryStringBuilder = EsQueryStringBuilder.create();
        esQueryStringBuilder.createCriteria().andNotIn("ID", "123", "312");
        String q = esQueryStringBuilder.build();
        assertEquals("(NOT ID:(123 OR 312))", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andRangeEquals("AGE", 18, 24);
        q = esQueryStringBuilder.build();
        assertEquals("(AGE:[18 TO 24])", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andNotRangeEquals("AGE", 18, 24);
        q = esQueryStringBuilder.build();
        assertEquals("(NOT AGE:[18 TO 24])", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andRange("AGE", 18, 24);
        q = esQueryStringBuilder.build();
        assertEquals("(AGE:{18 TO 24})", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andNotRange("AGE", 18, 24);
        q = esQueryStringBuilder.build();
        assertEquals("(NOT AGE:{18 TO 24})", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andGteEquals("AGE", 18);
        q = esQueryStringBuilder.build();
        assertEquals("(AGE:[18 TO *])", q);
        esQueryStringBuilder.clear();

        esQueryStringBuilder.createCriteria().andLteEquals("AGE", 24);
        q = esQueryStringBuilder.build();
        assertEquals("(AGE:[* TO 24])", q);
        esQueryStringBuilder.clear();
    }

}