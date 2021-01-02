package com.zxf.builder;

import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 构建Query string query
 * @author zxf
 */
public class EsQueryStringBuilder {

    public static EsQueryStringBuilder create(){
        return new EsQueryStringBuilder();
    }

    private List<Criteria> oredCriteria;

    public EsQueryStringBuilder() {
        oredCriteria = new LinkedList<>();
    }

    public Criteria createCriteria() {
        Criteria criteria = new Criteria();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public Criteria or(){
        Criteria criteria = new Criteria();
        oredCriteria.add(criteria);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
    }

    /**
     * 构建 query string
     * @return String
     */
    public String build() {
        String q = this.oredCriteria.stream().map(Criteria::formatQuery).collect(Collectors.joining(") OR ("));
        return String.format("(%s)", q);
    }

    public static class Criteria {
        private List<String> criteria;

        private Criteria() {
            criteria = new LinkedList<>();
        }

        private Criteria addFormatCriteria(String format, String fieldName, Object... values) {
            Assert.isTrue(values.length > 0, "Value for "+ fieldName + "cannot be null");
            Object[] args = new Object[values.length + 1];
            args[0] = fieldName;
            for (int i = 0; i < values.length; i++ ) {
                args[i +1] = values[i];
            }
            criteria.add(String.format(format, args));
            return this;
        }

        /**
         * equals
         * @param fieldName
         * @param value
         * @return Criteria
         */
        public Criteria andEq(String fieldName, Object value) {
            return addFormatCriteria("%s:%s", fieldName, value);
        }

        /**
         * in ()
         * @param fieldName
         * @param value
         * @return Criteria
         */
        public Criteria andIn(String fieldName, String... value) {
            return addFormatCriteria("%s:(%s)", fieldName, String.join("," , value ));
        }

        /**
         * not in ()
         * @param fieldName
         * @param value
         * @return Criteria
         */
        public Criteria andNotIn(String fieldName, String... value) {
            return addFormatCriteria("NOT %s:(%s)", fieldName, String.join("," , value ));
        }

        /**
         * range 包含边界值
         * @param fieldName
         * @param minValue
         * @param maxValue
         * @return Criteria
         */
        public Criteria andRangeEquals(String fieldName, Object minValue, Object maxValue) {
            return this.addFormatCriteria("%s:[%s TO %s]", fieldName, minValue, maxValue);
        }

        /**
         * 包含边界值
         * @param fieldName
         * @param minValue
         * @param maxValue
         * @return Criteria
         */
        public Criteria andNotRangeEquals(String fieldName, Object minValue, Object maxValue) {
            return this.addFormatCriteria("NOT %s:[%s TO %s]", fieldName, minValue, maxValue);
        }

        /**
         * 不包含边界值
         * @param fieldName
         * @param minValue
         * @param maxValue
         * @return Criteria
         */
        public Criteria andRange(String fieldName, Object minValue, Object maxValue){
            return this.addFormatCriteria("%s:{%s TO %s}", fieldName, minValue, maxValue);
        }

        /**
         * 不包含边界值
         * @param fieldName
         * @param minValue
         * @param maxValue
         * @return Criteria
         */
        public Criteria andNotRange(String fieldName, Object minValue, Object maxValue){
            return this.addFormatCriteria("NOT %s:{%s TO %s}", fieldName, minValue, maxValue);
        }

        /**
         * >=
         * @param fieldName
         * @param value
         * @return Criteria
         */
        public Criteria andGteEquals(String fieldName, Object value) {
            return this.andRangeEquals(fieldName, value, "*");
        }

        /**
         * <=
         * @param fieldName
         * @param value
         * @return Criteria
         */
        public Criteria andLteEquals(String fieldName, Object value) {
            return this.andRangeEquals(fieldName, "*", value);
        }

        /**
         * format and query
         * @return String
         */
        public String formatQuery() {
            return String.join(" AND ", this.criteria);
        }

    }

}
