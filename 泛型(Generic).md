# 泛型通配符
```text
    可以使用A-Z中任何一个，但是常用的时T、E、K、V、?
        ?: 无界通配符，表示不确定的java类型。
        T(Type): 表示具体的一个java类型。
        K(key) V(value): 分别代表java键中的key value
        E(element): 代表Element元素。
```
# 规定泛型方法或者类使用泛型
```text
    泛型方法: 在方法返回前类型前添加"<>"，里边添加泛型标识。
    泛型类: 在类的名后边添加"<>"，里边添加泛型标识。
```
```java
public class GenericTest {

    //规定T类型必须为 Number的子类
    public static <T extends Number> void testList(List<T> list1, List<T> list2){

    }

    //规定k必须为String类型，v必须为Number类型
    public static <k extends String, v extends Number> void testMap(Map<k, v> mCap){

    }

    public static class MyCollection<T, E, K, V>{
        T t;
        List<E> list;
        Map<K,V> map;
    }

}
```
# 泛型的使用
## 
## 第一种: 在类型创建的时候才确定需要使用的类型，或者规定使用的类型。
```java
public class GenericTest {

    public static void main(String[] args) {
        List<Number> list1 = new ArrayList<>();
        MyCollection<GenericTest, Integer, String, List> myCollection = new MyCollection<>();
    }

    public static class MyCollection<T, E, K, V>{
        T t;
        List<E> list;
        Map<K,V> map;
    }
}
```
## 第二种: 规定使用的类型的范围
### 类型必须为当前规定的类或者他的子类（上界通配符）
```java
public class GenericTest {

    //规定list 参数类型必须类型必须为 Number的子类
    public static <T extends Number> void testList(List<T> list1, List<T> list2){

    }

    //规定key必须为String类型，value必须为Number类型
    public static <K extends String, V extends Number> void testMap(Map<K, V> mCap){

    }

    public static void testLowerBound(List<? extends Number> list){
        
    }


}
```
### 类型必须为当前规定的类型或者父类型（下界通配符）
```java
public class GenericTest {
    
    //必须为T的父类型
    public static <T> void testSuper(List<? super T> list){
        
    }

}
```
# ? 与 T 的区别
```text
    1.? 代表不确定类型，T 代表确定的类型。
    2.? 可以进行两种限定(extends、super)，T 只能使用一种(extends)。
    3.T 使用时必须知道具体的使用的类型，? 使用时不必知道具体类型。常用于Class<T> clazz、Class<?> clazz。
```