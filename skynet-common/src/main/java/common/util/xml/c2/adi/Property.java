package common.util.xml.c2.adi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name"
})
@XmlRootElement(name = "Property")
public class Property {

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
