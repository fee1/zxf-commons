# 示例
## 1.查询全部
```text
http://192.168.137.135:9200/twitter/_search
{
    "query":{
        "query_string":{
            "query": "*"
        }
    }
}
```
## 2.分页查询
```text
http://192.168.137.135:9200/twitter/_search
{
    "from":0,
    "size":2,
    "query":{
        "query_string":{
            "query": "*"
        }
    }
}
注意 from 字段是从0开始的
```
## 3.返回指定字段
```text
http://192.168.137.135:9200/twitter/_search
第一种方式：
{
    "query":{
        "query_string":{
            "query": "*"
        }
    },
    "_source": ["address"]
}
结果：
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 6,
            "relation": "eq"
        },
        "max_score": 1.0,
        "hits": [
            {
                "_index": "twitter",
                "_id": "1",
                "_score": 1.0,
                "_source": {
                    "address": "中国北京市海淀区"
                }
            }
        ]
    }
}

第二种方式：
{
    "query":{
        "query_string":{
            "query": "*"
        }
    },
    "_source": false,
    "fields": ["address"]
}
结果：
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 6,
            "relation": "eq"
        },
        "max_score": 1.0,
        "hits": [
            {
                "_index": "twitter",
                "_id": "1",
                "_score": 1.0,
                "fields": {
                    "address": [
                        "中国北京市海淀区"
                    ]
                }
            }
        ]
    }
}

两种方式比较：使用fields来指定返回的字段，而不用_source，因为这样更高效。
```
## 4.统计查询
```text
http://192.168.137.135:9200/twitter/_search
{
    "query":{
        "query_string":{
            "query": "age:20"
        }
    }
}
```
## 5.
## 3.字段匹配查询
```text
查询年龄为20岁的数据
http://192.168.137.135:9200/twitter/_search
{
    "query":{
        "query_string":{
            "query": "age:20"
        }
    }
}
```
```text
查询user字段包含李四的数据
{
    "query":{
        "query_string":{
            "query": "user:李四"
        }
    }
}
```