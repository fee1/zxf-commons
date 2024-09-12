package com.zxf.common.utils.sql;
/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class ConnectSymbols {

    public static final String AND = "AND";

    public static final String OR = "OR";

    public static final String LEFT_PARENTHESIS = "(";

    public static final String RIGHT_PARENTHESIS = ")";

    public static final String SPACE = " ";

    private Criteria criteria;

    private String symbol = "";

    public ConnectSymbols(Criteria criteria){
        this.criteria = criteria;
    }

    public Criteria and(){
        this.symbol = AND;
        this.criteria.setConnectSymbol(this);
        return this.criteria;
    }

    public Criteria or(){
        this.symbol = OR;
        this.criteria.setConnectSymbol(this);
        return this.criteria;
    }

    public Criteria end(){
        return this.criteria;
    }

    protected Criteria getCriteria() {
        return criteria;
    }

    protected String getSymbol() {
        return symbol;
    }

}
