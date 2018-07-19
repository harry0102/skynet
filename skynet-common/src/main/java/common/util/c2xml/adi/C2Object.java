/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: C2Object
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:33
 * Description: C2Object
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈C2Object〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "propertys"
})
@XmlRootElement(name = "Object")
public class C2Object {

    @XmlAttribute(name = "Code", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String code;

    @XmlAttribute(name = "Action", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String action;

    @XmlAttribute(name = "ID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    @XmlAttribute(name = "ElementType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String elementType;

    @XmlElement(name = "Property", required = true)
    protected List<C2Property> propertys;


    public List<C2Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<C2Property> propertys) {
        this.propertys = propertys;
    }

    public String getAction() {
        return action;
    }


    public void setAction(String value) {
        this.action = value;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String value) {
        this.code = value;
    }


    public String getElementType() {
        return elementType;
    }


    public void setElementType(String value) {
        this.elementType = value;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}