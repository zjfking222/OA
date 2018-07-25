/**
 * EosDataSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.eosdata.service;

public interface EosDataSoap_PortType extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String supplierSave(java.lang.String xmlData) throws java.rmi.RemoteException;
    public java.lang.String supplierDelete(java.lang.String xmlData) throws java.rmi.RemoteException;
    public java.lang.String materielSave(java.lang.String xmlData) throws java.rmi.RemoteException;
    public java.lang.String materielDelete(java.lang.String xmlData) throws java.rmi.RemoteException;
}
