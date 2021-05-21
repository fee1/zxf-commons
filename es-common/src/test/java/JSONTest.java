
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;

import java.io.Serializable;
import java.util.List;

/**
 * @author 朱晓峰
 */
public class JSONTest {

    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();

        Student student1 = new Student("xiaoming1", 10);
        Student student2 = new Student("xiaoming2", 12);

        jsonArray.add(student1);
        jsonArray.add(student2);

        System.out.println(student1);
        System.out.println(student2);

        String json = JSON.toJSONString(jsonArray);
        System.out.println(json);

        List<Student> listR = JSONObject.parseArray(json, Student.class);
        System.out.println(listR);
    }

    public static class Student implements Serializable {
        private String name;
        private int age;
        Student(){}

        Student(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
