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
@XmlRootElement(name = "Object")
public class Object {

    @XmlElement(name = "Property", required = true)
    protected List<Property> property;

    @XmlAttribute(name = "Action", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String action;

    @XmlAttribute(name = "Code", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String code;

    @XmlAttribute(name = "ElementType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String elementType;

    @XmlAttribute(name = "ID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String id;


    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<>();
        }
        return this.property;
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


    public String getID() {
        return id;
    }


    public void setID(String value) {
        this.id = value;
    }

}
