package common.util.xml.c2.adi;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "objects",
        "mappings"
})
@XmlRootElement(name = "ADI", namespace = "http://www.w3.org/2001/XMLSchema-instance")
public class ADI {

    @XmlElement(name = "Objects", required = true)
    protected Objects objects;
    @XmlElement(name = "Mappings", required = true)
    protected Mappings mappings;
    @XmlAttribute(name = "Priority", required = true)
    protected String priority;

    @XmlAttribute(name = "StaffID", required = true)
    protected String StaffID;


    public Objects getObjects() {
        return objects;
    }


    public void setObjects(Objects value) {
        this.objects = value;
    }


    public Mappings getMappings() {
        return mappings;
    }


    public void setMappings(Mappings value) {
        this.mappings = value;
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
}
