package com.zxf.common;


//import cn.mezeron.jianzan.domain.employee.model.Employee;
//import cn.mezeron.jianzan.domain.org.model.Organization;
//import cn.mezeron.jianzan.utils.sql.ConnectSymbols;
//import cn.mezeron.jianzan.utils.sql.SQLGrammar;
//import cn.mezeron.jianzan.utils.sql.e.JoinType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 加了什么功能首选需要跑简单用例代码，确保不影响之前到之前的功能
 *
 * @author zhuxiaofeng
 * @date 2024/8/29
 */
public class SQLGrammarTest {

//    /**
//     * 简单使用
//     */
//    @Test
//    public void simpleTest() {
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class)
//                .where()
//                .equalTo(Employee::getId, "1")
//                .and().equalTo(Employee::getName, "2")
//                .or().equalTo(Employee::getName, "3")
//                // 带复合条件的查询
//                .and().complex(sqlGrammar.createCriteria().equalTo(Employee::getName, "4").and().equalTo(Employee::getName, "5"));
//        System.out.println(sqlGrammar.generatorSql());
//
//        Assert.assertEquals("SELECT jz_employee.id,jz_employee.name FROM jz_employee " +
//                "WHERE (  id = :id AND name = :name OR name = :name AND (  name = :name AND name = :name))", sqlGrammar.generatorSql());
//    }
//
//    @Test
//    public void wrongUsageTest(){
//        Map<String, String> params = new HashMap<>();
//        params.put("id", "1");
//        params.put("orgId", "2");
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class);
//        sqlGrammar.where().equalTo(params.get("id") != null,Employee::getId, params.get("id"));
//        sqlGrammar.where().equalTo(params.get("name") != null,Employee::getName, params.get("name"));
//        sqlGrammar.where().equalTo(params.get("orgId") != null,Employee::getOrgId, params.get("orgId"));
//        System.out.println(sqlGrammar.generatorSql());
//    }
//
//    @Test
//    public void correctUsageTest(){
//        Map<String, String> params = new HashMap<>();
//        params.put("id", "1");
//        params.put("orgId", "2");
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class);
//        ConnectSymbols connectSymbols = sqlGrammar.where().equalTo(params.get("id") != null, Employee::getId, params.get("id"));
//        connectSymbols.and().equalTo(params.get("name") != null,Employee::getName, params.get("name"));
//        connectSymbols.and().equalTo(params.get("orgId") != null,Employee::getOrgId, params.get("orgId"));
//        System.out.println(sqlGrammar.generatorSql());
//    }
//
//    /**
//     *  join 使用
//     */
//    @Test
//    public void joinTest(){
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .select(Organization::getName)
//                .from(Employee.class)
//                .join( JoinType.LEFT_JOIN, Organization.class, sqlGrammar.createCriteria().equalTo(Employee::getOrgId, Organization::getId).end())
//                .join( JoinType.RIGHT_JOIN, Organization.class, sqlGrammar.createCriteria().equalTo(Employee::getOrgId, Organization::getId).end())
//                .where()
//                .equalTo(Employee::getId, "1");
//        System.out.println(sqlGrammar.generatorSql());
//        Assert.assertEquals("SELECT jz_employee.id,jz_employee.name,jz_org.name " +
//                "FROM jz_employee " +
//                "LEFT JOIN jz_org ON    jz_employee.org_id = jz_org.id " +
//                "RIGHT JOIN jz_org ON    jz_employee.org_id = jz_org.id " +
//                "WHERE (  jz_employee.id = :jz_employee.id)", sqlGrammar.generatorSql());
//
//        sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class)
//                .join( JoinType.LEFT_JOIN, Organization.class,
//                        sqlGrammar.createCriteria().equalTo(Employee::getOrgId, Organization::getId).and().equalTo(Employee::getStatus, 1).end())
//                .join( JoinType.RIGHT_JOIN, Organization.class,
//                        sqlGrammar.createCriteria().equalTo(Employee::getOrgId, Organization::getId).end())
//                .where()
//                .equalTo(Employee::getId, "1");
//        System.out.println(sqlGrammar.generatorSql());
//        Assert.assertEquals("SELECT jz_employee.id,jz_employee.name FROM jz_employee " +
//                "LEFT JOIN jz_org ON    jz_employee.org_id = jz_org.id AND status = :status " +
//                "RIGHT JOIN jz_org ON    jz_employee.org_id = jz_org.id " +
//                "WHERE (  jz_employee.id = :jz_employee.id)", sqlGrammar.generatorSql());
//    }
//
//    /**
//     * 当涉及多张表，有些查询字段重名时怎么办？
//     *
//     * 如何自定义查询字段的别名, 不需要别名  查询的方式统一为统一格式  [表明.字段  --->  jz_employee.org_id ]
//     */
//    @Test
//    public void selectAliasTest(){
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getName).select(Employee::getId, "myId")
//                .from(Employee.class)
//                .join(JoinType.LEFT_JOIN, Organization.class, sqlGrammar.createCriteria().equalTo(Employee::getOrgId, Organization::getId).end())
//                .where()
//                .equalTo(Employee::getId, "1");
//        System.out.println(sqlGrammar.generatorSql());
//        Assert.assertEquals("SELECT jz_employee.name,jz_employee.id AS myId FROM jz_employee " +
//                "LEFT JOIN jz_org ON    jz_employee.org_id = jz_org.id " +
//                "WHERE (  jz_employee.id = :jz_employee.id)", sqlGrammar.generatorSql());
//    }
//
//    @Test
//    public void orderByTest(){
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class)
//                .where()
//                .equalTo(Employee::getId, "1");
//        sqlGrammar.orderByAsc(Employee::getId);
//        sqlGrammar.orderByDesc(Employee::getName);
//        sqlGrammar.orderByDesc(Employee::getNickname);
//        sqlGrammar.orderByAsc(Employee::getOrgId);
//        System.out.println(sqlGrammar.generatorSql());
//        Assert.assertEquals("SELECT jz_employee.id,jz_employee.name FROM jz_employee WHERE (  id = :id) ORDER BY jz_employee.id ASC ,jz_employee.name DESC ,jz_employee.nickname DESC ,jz_employee.org_id ASC ", sqlGrammar.generatorSql());
//    }
//
//    @Test
//    public void conditionTest(){
//        Map<String, String> params = new HashMap<>();
//        params.put("id", "1");
//        params.put("orgId", "2");
//        SQLGrammar sqlGrammar = new SQLGrammar();
//        sqlGrammar.select(Employee::getId, Employee::getName)
//                .from(Employee.class)
//                .where()
//                .equalTo(params.get("id") != null,Employee::getId, params.get("id"))
//                .and().equalTo(params.get("name") != null,Employee::getName, params.get("name"))
//                .and().equalTo(params.get("orgId") != null,Employee::getOrgId, params.get("orgId"));
//        System.out.println(sqlGrammar.generatorSql());
//        Assert.assertEquals("SELECT jz_employee.id,jz_employee.name FROM jz_employee WHERE (  id = :id AND org_id = :org_id)", sqlGrammar.generatorSql());
//    }

}