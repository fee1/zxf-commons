package com.zxf.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询 别名 结果返回实体
 *
 * @author zhuxiaofeng
 * @date 2022/2/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAliasResponse {

    private String indexName;

    private List<String> aliases;

}
