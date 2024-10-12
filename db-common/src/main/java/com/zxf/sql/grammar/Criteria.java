package com.zxf.sql.grammar;


import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class Criteria extends GeneratedCriteria {

    private ConnectSymbols connectSymbol;

    protected Criteria(boolean multiTableQuery) {
        super(multiTableQuery);
    }

    protected void setConnectSymbol(ConnectSymbols connectSymbol) {
        this.connectSymbol = connectSymbol;
    }

    protected ConnectSymbols getConnectSymbol() {
        return connectSymbol;
    }

    public ConnectSymbols complex(ConnectSymbols connectSymbol){
        Criteria criteria = connectSymbol.getCriteria();
        List<Criterion> criterionList = criteria.getCriteria();
        StringBuilder complexCondition = new StringBuilder();
        for (Criterion criterion : criterionList) {
            String symbol = criterion.getConnectSymbol() == null ? "" : criterion.getConnectSymbol().getSymbol();
            complexCondition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
        }
        this.addCriterion("(" +complexCondition +")");
        this.params.putAll(criteria.getParams());
        return this.getConnectSymbol();
    }

    public ConnectSymbols complex(boolean condition,ConnectSymbols connectSymbol){
        if (condition) {
            return complex(connectSymbol);
        }
        return this.getConnectSymbol();
    }

}
