/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: C2Property
 * Author:   MingRuiRen
 * Date:     2018/7/19 12:51
 * Description: C2Property
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈C2Property〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name"
})
@XmlRootElement(name = "Property")
public class C2Property {

    //@XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlValue
    protected String content;
    @XmlAttribute(name = "Name", required = true)

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;


    public String getContent() {
        return content;
    }


    public void setContent(String value) {
        this.content = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }


}