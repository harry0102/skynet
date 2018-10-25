package common.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the web package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _ExcuteResponse_QNAME = new QName(
			"http://entranceService.wondersgroup.com/", "excuteResponse");
	private final static QName _Excute_QNAME = new QName(
			"http://entranceService.wondersgroup.com/", "excute");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: web
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link ExcuteResponse }
	 * 
	 */
	public ExcuteResponse createExcuteResponse() {
		return new ExcuteResponse();
	}

	/**
	 * Create an instance of {@link Excute }
	 * 
	 */
	public Excute createExcute() {
		return new Excute();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ExcuteResponse }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://entranceService.wondersgroup.com/", name = "excuteResponse")
	public JAXBElement<ExcuteResponse> createExcuteResponse(ExcuteResponse value) {
		return new JAXBElement<ExcuteResponse>(_ExcuteResponse_QNAME,
				ExcuteResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Excute }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://entranceService.wondersgroup.com/", name = "excute")
	public JAXBElement<Excute> createExcute(Excute value) {
		return new JAXBElement<Excute>(_Excute_QNAME, Excute.class, null, value);
	}

}
