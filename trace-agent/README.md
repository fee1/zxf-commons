# TraceId 生成agent
```text
    以线程run方法为切入点的，在run方法执行前将TraceId赋予log4j的MDC中。
```
## 如何测试
```text
    使用MAVEN打包生成jar
    启动参数添加:-javaagent:trace-agent-1.0-SNAPSHOT.jar
    直接运行此测试类:com.zxf.trace.AgentTest.main
```
## 如何使用
```text
    将com.zxf.trace.TraceAgent代码调整，如图:
```
![images](./images/1678168688520.jpg)
```text
    使用MAVEN打包生成jar
    将此jar运用在启动参数上[-javaagent:trace-agent-1.0-SNAPSHOT.jar]
```