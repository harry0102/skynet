package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "propertys"
})
@XmlRootElement(name = "Mapping")
public class C2Mapping {


    @XmlAttribute(name = "Action", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String action;

    @XmlAttribute(name = "ID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;

    @XmlAttribute(name = "ElementType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String elementType;

    @XmlAttribute(name = "ElementCode", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String elementCode;



    @XmlAttribute(name = "ElementID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String elementID;

    @XmlAttribute(name = "ParentCode", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String parentCode;

    @XmlAttribute(name = "ParentID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String parentID;

    @XmlAttribute(name = "ParentType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String parentType;



    @XmlElement(name = "Property", required = true)
    protected List<C2Property> propertys;



    public String getAction() {
        return action;
    }


    public void setAction(String value) {
        this.action = value;
    }


    public String getElementCode() {
        return elementCode;
    }


    public void setElementCode(String value) {
        this.elementCode = value;
    }


    public String getElementID() {
        return elementID;
    }


    public void setElementID(String value) {
        this.elementID = value;
    }


    public String getElementType() {
        return elementType;
    }


    public void setElementType(String value) {
        this.elementType = value;
    }


    public String getParentCode() {
        return parentCode;
    }


    public void setParentCode(String value) {
        this.parentCode = value;
    }


    public String getParentID() {
        return parentID;
    }


    public void setParentID(String value) {
        this.parentID = value;
    }


    public String getParentType() {
        return parentType;
    }


    public void setParentType(String value) {
        this.parentType = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<C2Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<C2Property> propertys) {
        this.propertys = propertys;
    }
}
