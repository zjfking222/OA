/**
 * EosDataLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.eosdata.service;

public class EosDataLocator extends org.apache.axis.client.Service implements com.eosdata.service.EosData {

    public EosDataLocator() {
    }


    public EosDataLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EosDataLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EosDataSoap
    private java.lang.String EosDataSoap_address = "http://122.225.91.27/HuayouEosDataWebService/eosdata.asmx";

    public java.lang.String getEosDataSoapAddress() {
        return EosDataSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EosDataSoapWSDDServiceName = "EosDataSoap";

    public java.lang.String getEosDataSoapWSDDServiceName() {
        return EosDataSoapWSDDServiceName;
    }

    public void setEosDataSoapWSDDServiceName(java.lang.String name) {
        EosDataSoapWSDDServiceName = name;
    }

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EosDataSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEosDataSoap(endpoint);
    }

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.eosdata.service.EosDataSoap_BindingStub _stub = new com.eosdata.service.EosDataSoap_BindingStub(portAddress, this);
            _stub.setPortName(getEosDataSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEosDataSoapEndpointAddress(java.lang.String address) {
        EosDataSoap_address = address;
    }


    // Use to get a proxy class for EosDataSoap12
    private java.lang.String EosDataSoap12_address = "http://122.225.91.27/HuayouEosDataWebService/eosdata.asmx";

    public java.lang.String getEosDataSoap12Address() {
        return EosDataSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EosDataSoap12WSDDServiceName = "EosDataSoap12";

    public java.lang.String getEosDataSoap12WSDDServiceName() {
        return EosDataSoap12WSDDServiceName;
    }

    public void setEosDataSoap12WSDDServiceName(java.lang.String name) {
        EosDataSoap12WSDDServiceName = name;
    }

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EosDataSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEosDataSoap12(endpoint);
    }

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.eosdata.service.EosDataSoap12Stub _stub = new com.eosdata.service.EosDataSoap12Stub(portAddress, this);
            _stub.setPortName(getEosDataSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEosDataSoap12EndpointAddress(java.lang.String address) {
        EosDataSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.eosdata.service.EosDataSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.eosdata.service.EosDataSoap_BindingStub _stub = new com.eosdata.service.EosDataSoap_BindingStub(new java.net.URL(EosDataSoap_address), this);
                _stub.setPortName(getEosDataSoapWSDDServiceName());
                return _stub;
            }
            if (com.eosdata.service.EosDataSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.eosdata.service.EosDataSoap12Stub _stub = new com.eosdata.service.EosDataSoap12Stub(new java.net.URL(EosDataSoap12_address), this);
                _stub.setPortName(getEosDataSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EosDataSoap".equals(inputPortName)) {
            return getEosDataSoap();
        }
        else if ("EosDataSoap12".equals(inputPortName)) {
            return getEosDataSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "EosData");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "EosDataSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "EosDataSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EosDataSoap".equals(portName)) {
            setEosDataSoapEndpointAddress(address);
        }
        else 
if ("EosDataSoap12".equals(portName)) {
            setEosDataSoap12EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
