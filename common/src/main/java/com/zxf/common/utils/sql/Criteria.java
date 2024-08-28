package com.zxf.common.utils.sql;


import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class Criteria extends GeneratedCriteria {

    private ConnectSymbols connectSymbol;

    protected Criteria() {
        super();
    }

    protected void setConnectSymbol(ConnectSymbols connectSymbol) {
        this.connectSymbol = connectSymbol;
    }

    protected ConnectSymbols getConnectSymbol() {
        return connectSymbol;
    }

//    public Criteria complex(Criteria criteria){
//        List<Criterion> criterionList = criteria.getCriteria();
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
//        this.addCriterion("( " +complexCondition +" )");
//        return (Criteria) this;
//    }

    public Criteria complex(ConnectSymbols connectSymbol){
        Criteria criteria = connectSymbol.getCriteria();
        List<Criterion> criterionList = criteria.getCriteria();
        StringBuilder complexCondition = new StringBuilder();
        for (Criterion criterion : criterionList) {
            String symbol = criterion.getConnectSymbol() == null ? ConnectSymbols.SPACE : criterion.getConnectSymbol().getSymbol();
            complexCondition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
        }
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
        this.addCriterion("( " +complexCondition +" )");
        this.params.putAll(criteria.getParams());
        return (Criteria) this;
    }

}
