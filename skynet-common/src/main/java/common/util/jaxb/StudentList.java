/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: StudentList
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:51
 * Description: StudentList
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈StudentList〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
@XmlRootElement(name="list")
public class StudentList {

    List<Student> students;

    @XmlElement(name = "student")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}