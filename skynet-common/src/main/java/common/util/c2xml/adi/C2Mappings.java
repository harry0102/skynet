package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "mappings"
})
@XmlRootElement(name = "Mappings")
public class C2Mappings {

    @XmlElement(name = "Mapping", required = true)
    protected List<C2Mapping> mappings;

    public List<C2Mapping> getMapping() {
        return mappings;
    }

    public void setMapping(List<C2Mapping> mapping) {
        this.mappings = mapping;
    }
}
