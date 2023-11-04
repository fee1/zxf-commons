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


```text
1.直接deploy的jar可以直接使用

2.install不能使用，会报错
Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
Failed to find Premain-Class manifest attribute in /opt/evo-station/trace-agent.jar
Error occurred during initialization of VM
agent library failed to init: instrument

3.可以尝试使用package 打包
```