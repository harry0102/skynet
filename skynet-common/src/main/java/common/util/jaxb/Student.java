/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: Student
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:45
 * Description: Student
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.jaxb;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈Student〉
 * JAXB 实现java对象与xml之间互相转换
 首先熟悉一下JAXB实现对象与xml互转时常用的一些注解使用：

 1.@XmlRootElement，用于类级别的注解，对应xml的跟元素。通过name属性定义这个根节点的名称。

 2.@XmlAccessorType，定义映射这个类中的何种类型都需要映射到xml。(如果不存在@XmlAccessorType,默认使用XmlAccessType.PUBLIC_MEMBER注解)

 　　参数：XmlAccessType.FIELD: java对象中的所有成员变量。

 　　XmlAccessType.PROPERTY：java对象中所有通过getter/setter方式访问的成员变量。

 　　XmlAccessType.PUBLIC_MEMBER：java对象中所有的public访问权限的成员变量和通过getter/setter方式访问的成员变量。

 　　XmlAccessType.NONE: java对象的所有属性都不映射为xml的元素。

 3.@XmlAttribute，用于把java对象的属性映射为xml的属性,并可通过name属性为生成的xml属性指定别名。

 4.@XmlElement，指定一个字段或get/set方法映射到xml的节点。通过name属性定义这个根节点的名称。

 5.@XmlElementWrapper，为数组或集合定义一个父节点。通过name属性定义这个父节点的名称。
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class Student {

    String name;
    String sex;
    int number;
    String className;
    List<String> hobby;

    public Student() {
    }

    public Student(String name, String sex, int number, String className, List<String> hobby) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.className = className;
        this.hobby = hobby;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @XmlAttribute(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @XmlElement(name = "className")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @XmlElementWrapper(name = "hobbys")
    @XmlElement(name = "hobby")
    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", number=").append(number);
        sb.append(", className='").append(className).append('\'');
        sb.append(", hobby=").append(hobby);
        sb.append('}');
        return sb.toString();
    }
}