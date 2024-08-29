package com.zxf.common.utils.sql;


import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class Criteria<T> extends GeneratedCriteria<T> {

    private ConnectSymbols<T> connectSymbol;

    protected Criteria() {
        super();
    }

    protected void setConnectSymbol(ConnectSymbols<T> connectSymbol) {
        this.connectSymbol = connectSymbol;
    }

    protected ConnectSymbols<T> getConnectSymbol() {
        return connectSymbol;
    }

//    public Criteria complex(Criteria criteria){
//        List<Criterion> criterionList = criteria.getCriteria();
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
//        this.addCriterion("( " +complexCondition +" )");
//        return (Criteria) this;
//    }

    public Criteria<T> complex(ConnectSymbols<T> connectSymbol){
        Criteria<T> criteria = connectSymbol.getCriteria();
        List<Criterion<T>> criterionList = criteria.getCriteria();
        StringBuilder complexCondition = new StringBuilder();
        for (Criterion<T> criterion : criterionList) {
            String symbol = criterion.getConnectSymbol() == null ? ConnectSymbols.SPACE : criterion.getConnectSymbol().getSymbol();
            complexCondition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
        }
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
        this.addCriterion("( " +complexCondition +" )");
        this.params.putAll(criteria.getParams());
        return (Criteria<T>) this;
    }

}
