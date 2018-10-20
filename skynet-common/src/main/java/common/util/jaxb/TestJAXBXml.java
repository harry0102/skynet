/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: JAXBDemo
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:43
 * Description: JAXBDemo用于学习理解JAXB生成xml
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.jaxb;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈JAXBDemo用于学习理解JAXB生成xml〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class TestJAXBXml {

    public static void main(String[] args) {

    }

    /**
     * @param
     * @return void
     * @descrption 测试java对象转xml
     * @author MingRuiRen
     * @date 2018/7/19 11:10
     */
    @Test
    public void testBeanToXml() throws JAXBException, IOException {

        List<String> hobby = new ArrayList<>();
        hobby.add("篮球");
        hobby.add("音乐");
        hobby.add("乒乓球");

        List<Student> studentList = new ArrayList<>();
        Student st = new Student("张三", "男", 10001, "尖子班", hobby);
        Student st1 = new Student("李四", "男", 10002, "普通班", hobby);
        Student st2 = new Student("莉莉", "女", 10003, "普通班", hobby);
        studentList.add(st);
        studentList.add(st1);
        studentList.add(st2);

        StudentList students = new StudentList();
        students.setStudents(studentList);
        String str = beanToXml(students, StudentList.class);

        System.out.println(str);

        //写入到xml文件中
        String xmlPath = "D:/testConfig.xml";
        BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(xmlPath)));
        bfw.write(str);
        bfw.close();

    }

    /**
     * @param
     * @return void
     * @descrption 测试xml转Java对象
     * @author MingRuiRen
     * @date 2018/7/19 11:12
     */
    @Test
    public void testXmlToBean() throws JAXBException, IOException {
        String xmlPath = "D:/testConfig.xml";
        Object object = xmlToBean(xmlPath, StudentList.class);
        StudentList students = (StudentList) object;
        List<Student> studentList = students.getStudents();

        for (Student s : studentList) {
            System.out.println(s.toString());
        }
    }

    /**
     * java对象转换为xml文件
     *
     * @param load java对象.Class
     * @return xml文件的String
     * @throws JAXBException
     */
    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();

    }

    /**
     * xml文件配置转换为对象
     *
     * @param xmlPath xml文件路径
     * @param load    java对象.Class
     * @return java对象
     * @throws JAXBException
     * @throws IOException
     */
    public static Object xmlToBean(String xmlPath, Class<?> load) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new File(xmlPath));
        return object;
    }


}