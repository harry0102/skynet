package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "objects",
        "mappings"
})
@XmlRootElement(name = "ADI")
public class C2ADI {

    @XmlAttribute(name = "xmlns:xsi", required = true)
    protected String xmlns;

    @XmlAttribute(name = "StaffID", required = true)
    protected String StaffID;

    @XmlAttribute(name = "Priority", required = true)
    protected String priority;

    @XmlElement(name = "Objects", required = true)
    protected C2Objects objects;

    @XmlElement(name = "Mappings", required = true)
    protected C2Mappings mappings;

    public C2ADI() {
        this.xmlns = "http://www.w3.org/2001/XMLSchema-instance";
        StaffID = "48";
    }



    public C2Objects getObjects() {
        return objects;
    }

    public void setObjects(C2Objects objects) {
        this.objects = objects;
    }

    public C2Mappings getMappings() {
        return mappings;
    }

    public void setMappings(C2Mappings mappings) {
        this.mappings = mappings;
    }

    public String getPriority() {
        return priority;
    }


    public void setPriority(String value) {
        this.priority = value;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
}
