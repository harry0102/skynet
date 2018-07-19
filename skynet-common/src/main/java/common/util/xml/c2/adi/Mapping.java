package common.util.xml.c2.adi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "property"
})
@XmlRootElement(name = "Mapping")
public class Mapping {

    @XmlElement(name = "Property", required = true)
    protected List<Property> property;
    @XmlAttribute(name = "Action", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String action;
    @XmlAttribute(name = "ElementCode", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String elementCode;
    @XmlAttribute(name = "ElementID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String elementID;
    @XmlAttribute(name = "ElementType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String elementType;
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

    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        return this.property;
    }
}
