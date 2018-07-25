/**
 * EosData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.eosdata.service;

public interface EosData extends javax.xml.rpc.Service {
    public java.lang.String getEosDataSoapAddress();

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap() throws javax.xml.rpc.ServiceException;

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getEosDataSoap12Address();

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap12() throws javax.xml.rpc.ServiceException;

    public com.eosdata.service.EosDataSoap_PortType getEosDataSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
