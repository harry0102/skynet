package common.util.xml.c2.adi;

import javax.xml.bind.annotation.XmlRegistry;
import common.util.xml.c2.adi.Objects;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the model.c2.adi package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: model.c2.adi
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Mappings }
     */
    public Mappings createMappings() {
        return new Mappings();
    }

    /**
     * Create an instance of {@link Mapping }
     */
    public Mapping createMapping() {
        return new Mapping();
    }

    /**
     * Create an instance of {@link ADI }
     */
    public ADI createADI() {
        return new ADI();
    }

    /**
     * Create an instance of {@link Objects }
     */
    public Objects createObjects() {
        return new Objects();
    }

    /**
     * Create an instance of {@link Object }
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link Property }
     */
    public Property createProperty() {
        return new Property();
    }

}
