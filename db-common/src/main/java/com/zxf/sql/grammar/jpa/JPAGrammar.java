package com.zxf.sql.grammar.jpa;

import com.zxf.sql.grammar.Criteria;
import com.zxf.sql.grammar.IGrammer;
import com.zxf.sql.grammar.JoinType;
import com.zxf.sql.grammar.SFunction;
import com.zxf.sql.grammar.jpa.m.SelectColumn;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA 方式实现SQL语法
 *
 * @author zhuxiaofeng
 * @date 2024/9/18
 */
public class JPAGrammar implements IGrammer {

    private List<SelectColumn<?>> selectColumns;

    private Class<?> rootTableClass;

    private CriteriaBuilder cb;

    private CriteriaQuery<?> cq;

    /**
     *
     * @param entityManager
     * @param returnType 返回接收的对象类型
     */
    public JPAGrammar(EntityManager entityManager, Class<?> returnType) {
        this.cb = entityManager.getCriteriaBuilder();
        this.cq = cb.createQuery(returnType);
        selectColumns = new ArrayList<>();
    }

    public Criteria build() {
        return new Criteria(false);
    }


    @Override
    public <T> IGrammer select(SFunction<T, ?>... columns) {
        for (SFunction<T, ?> column : columns) {
            SelectColumn<T> selectColumn = new SelectColumn<>();
            selectColumn.setColumn(column);
            this.selectColumns.add(selectColumn);
        }
        return this;
    }

    @Override
    public <T> IGrammer select(SFunction<T, ?> column, String alias) {
        SelectColumn<T> selectColumn = new SelectColumn<>();
        selectColumn.setColumn(column);
        selectColumn.setAlias(alias);
        this.selectColumns.add(selectColumn);
        return this;
    }

    @Override
    public IGrammer from(Class<?> rootTable) {
        this.rootTableClass = rootTable;
        return this;
    }

    @Override
    public IGrammer join(JoinType joinType, Class<?> joinTable, Criteria criteria) {
        return null;
    }
}
