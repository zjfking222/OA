
package com.BFS.action;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "hygyPaymentWebServicePortType", targetNamespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HygyPaymentWebServicePortType {


    /**
     * 
     * @param jsonStr
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "urn:erpPaymentData")
    @WebResult(targetNamespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com")
    @RequestWrapper(localName = "erpPaymentData", targetNamespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com", className = "com.byttersoft.hibernate.erp.hygy.webservice.server.ErpPaymentData")
    @ResponseWrapper(localName = "erpPaymentDataResponse", targetNamespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com", className = "com.byttersoft.hibernate.erp.hygy.webservice.server.ErpPaymentDataResponse")
    public String erpPaymentData(
        @WebParam(name = "jsonStr", targetNamespace = "http://server.webservice.hygy.erp.hibernate.byttersoft.com")
        String jsonStr);

}