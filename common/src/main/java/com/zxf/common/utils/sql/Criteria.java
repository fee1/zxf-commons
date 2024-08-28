package com.zxf.common.utils.sql;


import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class Criteria extends GeneratedCriteria {

    private cn.mezeron.jianzan.utils.sql.ConnectSymbols connectSymbol;

    protected Criteria() {
        super();
    }

    protected void setConnectSymbol(cn.mezeron.jianzan.utils.sql.ConnectSymbols connectSymbol) {
        this.connectSymbol = connectSymbol;
    }

    protected cn.mezeron.jianzan.utils.sql.ConnectSymbols getConnectSymbol() {
        return connectSymbol;
    }

//    public Criteria complex(Criteria criteria){
//        List<Criterion> criterionList = criteria.getCriteria();
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
//        this.addCriterion("( " +complexCondition +" )");
//        return (Criteria) this;
//    }

    public Criteria complex(cn.mezeron.jianzan.utils.sql.ConnectSymbols connectSymbol){
        Criteria criteria = connectSymbol.getCriteria();
        List<cn.mezeron.jianzan.utils.sql.Criterion> criterionList = criteria.getCriteria();
        StringBuilder complexCondition = new StringBuilder();
        for (cn.mezeron.jianzan.utils.sql.Criterion criterion : criterionList) {
            String symbol = criterion.getConnectSymbol() == null ? cn.mezeron.jianzan.utils.sql.ConnectSymbols.SPACE : criterion.getConnectSymbol().getSymbol();
            complexCondition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
        }
//        String complexCondition = criterionList.stream().map(Criterion::getCondition).collect(Collectors.joining(" or "));
        this.addCriterion("( " +complexCondition +" )");
        this.params.putAll(criteria.getParams());
        return (Criteria) this;
    }

}
