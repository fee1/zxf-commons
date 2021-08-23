#### Lambda表达式
##### 简介
```text
	Lambda表达式，也成为闭包。允许将函数作为方法的参数传过去。让我们能够写出更简洁的代码。
有利有弊，权衡利弊使用。
```
##### 写法

```text
(参数) -> {代码逻辑}
若只有一句简单的代码
参数-> sout(参数)
```
##### 语法

```text
	1.不需要声明参数类型。
	2.可选返回的关键字。
	3.lambda函数只能使用方法域内的变量，或者定义为static的共享全局变量属性。
```
##### 例子
###### 集合迭代
```java
public static void testCollection(){

        List<Object> list = new ArrayList<Object>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.forEach( n -> System.out.println(n) );
        //还能这样写
        list.forEach(System.out::println);
//        for (Object o : list) {
//            System.out.println(o);
//        }
        list = new LinkedList<>();
        list.add("1111-1");
        list.add("2222-1");
        list.add("3333-1");
        list.forEach(System.out::println);

        list = new Vector<>();
        list.add("1111-2");
        list.add("2222-2");
        list.add("3333-2");
        list.forEach(System.out::println);

        Set<Object> set = new TreeSet<>();
        set.add("1111-3");
        set.add("2222-3");
        set.add("3333-3");
        set.forEach(System.out::println);

        Map<Object, Object> map = new HashMap<>();
        map.put("qwer-key", "qwer-value");
        map.put("asdf-key", "asdf-value");
        map.put("zxcv-key", "zxcv-value");
        map.forEach( (k, v) -> System.out.println( k+"   "+v ) );

//        Set<Object> keySet = map.keySet();
//        for (Object key : keySet){
//            System.out.println(map.get(key));
//        }

    }
```
###### 线程实现

```java
    public static void testThread(){
        //之前的线程写法
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        //现在
        new Thread(() -> System.out.println("aaaa")).start();
    }
```
###### Lambda变量使用

```java
public class TestLambdaInterfaceRun {

    static String s2 = "aaaa";

    public static void main(String[] args) {
        String s1 = "ffff";
        TestLambda testLambda = s -> {
            System.out.println(s + s1 + s2);
        };
        testLambda.testLambda("testtesttest");
    }

    interface TestLambda{
        void testLambda(String s);
    }

}
```
#### 函数式接口
##### 定义
```text
①接口类，有且仅有一个抽象方法。
②一个接口带有@FunctionalInterface注解的都是函数式接口。默认情况下，一个抽象方法只有一个
方法也默认是函数式接口。@FunctionalInterface注解用于编译器检查类是否为函数式接口。
③将函数式接口传入方法时，表接口类实现传入。
```
##### 例子

```java
public class TestLambdaInterfaceRun {

    static String s2 = "aaaa";

    public static void main(String[] args) {
        String s1 = "ffff";
        TestLambda testLambda = s -> {
            System.out.println(s + s1 + s2);
        };
        testLambda.testLambda("testtesttest");
    }
    
    @FunctionalInterface
    interface TestLambda{
        void testLambda(String s);
    }
}
```
##### 用于支持函数式编程的类(java.util.function包下都是)
###### 例子

```java
public class TestLambdaMethod {

    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap<>();
        map.put("qwer-key", "qwer-value");
        map.put("asdf-key", "asdf-value");
        map.put("zxcv-key", "zxcv-value");
        testBiConsumer(map, (k, v) -> {
            System.out.println("key:"+k+"     value:"+v);
        });

        testPredicate(map, k -> {
            return k.equals("qwer-key");
        });
    }

    //无返回值的函数式接口
    public static void testBiConsumer(Map<Object, Object> map, BiConsumer<Object, Object> biConsumer){
        map.forEach((k, v) -> {
            biConsumer.accept(k, v);
        });
    }

    //判断型函数式接口
    public static void testPredicate(Map<Object, Object> map, Predicate<Object> predicate){
        map.forEach((k, v) -> {
            if (predicate.test(k)){
                System.out.println(v);
            }
        });
    }

}
```

#### 默认方法（接口中的default定义方法）
接口的默认实现方法，接口的实现类不需要实现此方法，可以直接使用。
##### 例子
```java
@FunctionalInterface
    interface TestLambda{
        void testLambda(String s);

        default void print(){
            System.out.println("afad");
        }
    }
```
#### Stream
##### 简介
```text
	Stream流也是java8api新添加的一种处理数据方式的新特性，能让程序员能够写出更加干净高效、整洁的代码。他的
使用就好似我们在写sql的查数据，通过筛选、排序、聚合等方式查询出数据。
```
##### 概念
```text
	数据源：集合、数据、I/O channel、产生器genertor等。
	聚合操作：filter(过滤)、map(操作元素)、reduce(l累加器迭代，返回参数供下次使用)、find(查找某个元素)、match（匹配）、sorter（排序）
	parallel：并行流。stream为串行流。
	内部迭代：stream的流迭代都是在内部进行的。
```
##### 使用例子

```java
	public static void testRemoveEmptyString(){
        List<String> oldList = Arrays.asList("111", "", "222", "", "333");
        List<String> filterList = oldList.stream().filter(current -> !current.isEmpty()).map(current -> current + "HH").collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonPrettyStr(filterList));
    }
    /*[
    "111HH",
    "222HH",
    "333HH"
	]*/
	
	//输出十个随机数
    public static void testPrintTenInt(){
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

 //list stream 分组
    private static void testGroupList() {

        List<Map<String, String>> list = new LinkedList<>();
        Map<String , String> map1 = new HashMap<>();
        map1.put("id", "a");
        Map<String , String> map2 = new HashMap<>();
        map2.put("id", "b");
        list.add(map1);
        list.add(map2);

        //stream 流分组
        Map<Object, List<Map<String, String>>> map = list.stream().collect(Collectors.groupingBy(item -> {return ((Map)item).get("id");}));
        System.out.println(JSONUtil.toJsonPrettyStr(map));
    }
```