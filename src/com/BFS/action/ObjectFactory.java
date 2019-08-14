
package com.BFS.action;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.BFS.action.*;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.byttersoft.hibernate.erp.hygy.webservice.server package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ErpPaymentDataJsonStr_QNAME = new QName("http://server.webservice.hygy.erp.hibernate.byttersoft.com", "jsonStr");
    private final static QName _ErpPaymentDataResponseReturn_QNAME = new QName("http://server.webservice.hygy.erp.hibernate.byttersoft.com", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.byttersoft.hibernate.erp.hygy.webservice.server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErpPaymentDataResponse }
     * 
     */
    public ErpPaymentDataResponse createErpPaymentDataResponse() {
        return new ErpPaymentDataResponse();
    }

    /**
     * Create an instance of {@link ErpPaymentData }
     * 
     */
    public ErpPaymentData createErpPaymentData() {
        return new ErpPaymentData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com", name = "jsonStr", scope = ErpPaymentData.class)
    public JAXBElement<String> createErpPaymentDataJsonStr(String value) {
        return new JAXBElement<String>(_ErpPaymentDataJsonStr_QNAME, String.class, ErpPaymentData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com", name = "return", scope = ErpPaymentDataResponse.class)
    public JAXBElement<String> createErpPaymentDataResponseReturn(String value) {
        return new JAXBElement<String>(_ErpPaymentDataResponseReturn_QNAME, String.class, ErpPaymentDataResponse.class, value);
    }

}
