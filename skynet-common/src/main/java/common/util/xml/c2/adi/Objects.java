package common.util.xml.c2.adi;

import javax.xml.bind.annotation.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "object"
})
@XmlRootElement(name = "Objects")
public class Objects {

    @XmlElement(name = "Object", required = true)
    protected List<Object> object;


    public List<Object> getObject() {
        if (object == null) {
            object = new ArrayList<Object>();
        }
        return this.object;
    }

}
