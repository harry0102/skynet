package common.util.xml.c2.adi;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "mapping"
})
@XmlRootElement(name = "Mappings")
public class Mappings {

    @XmlElement(name = "Mapping", required = true)
    protected List<Mapping> mapping;

    /**
     * Gets the value of the mapping property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mapping property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapping().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Mapping }
     */
    public List<Mapping> getMapping() {
        if (mapping == null) {
            mapping = new ArrayList<Mapping>();
        }
        return this.mapping;
    }

}
