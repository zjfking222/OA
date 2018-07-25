package com.jiuyi.service;

import javax.servlet.ServletConfig;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.jiuyi.service.impl.CertificateImpl;
import com.jiuyi.service.impl.DisqualificationImpl;
import com.jiuyi.service.impl.EquipmentReceiveImpl;
import com.jiuyi.service.impl.SaleAndDeliveryImpl;
import com.jiuyi.service.impl.SellImpl;

public class WebServlet extends CXFNonSpringServlet {

	private static final long serialVersionUID = -5990506353385795605L;

	@Override
	protected void loadBus(ServletConfig sc) {
		super.loadBus(sc);
		Bus bus = getBus();
        BusFactory.setDefaultBus(bus);    
        /*
         * 质量通知单流程生成webservices
         */
        DisqualificationService disqualificationService = new DisqualificationImpl();
        //JaxWsServerFactoryBean
        ServerFactoryBean serverFactoryBean = new ServerFactoryBean(); //server工厂    
        serverFactoryBean.setServiceClass(DisqualificationService.class);// 接口类   
        serverFactoryBean.setAddress("/DisqualificationService"); //服务请求路径    
        serverFactoryBean.setServiceBean(disqualificationService);    
        serverFactoryBean.create();
        /*
         * 物资出门证流程生成webservices
         */
        CertificateService certificateService = new CertificateImpl();
        //JaxWsServerFactoryBean
        ServerFactoryBean serverFactoryBean1 = new ServerFactoryBean(); //server工厂    
        serverFactoryBean1.setServiceClass(CertificateService.class);// 接口类   
        serverFactoryBean1.setAddress("/CertificateService"); //服务请求路径    
        serverFactoryBean1.setServiceBean(certificateService);    
        serverFactoryBean1.create();
        /*
         * 销售发货流程生成webservices
         */
        SaleAndDeliverySevice saleAndDeliverySevice = new SaleAndDeliveryImpl();
        //JaxWsServerFactoryBean
        ServerFactoryBean serverFactoryBean2 = new ServerFactoryBean(); //server工厂    
        serverFactoryBean2.setServiceClass(SaleAndDeliverySevice.class);// 接口类   
        serverFactoryBean2.setAddress("/SaleAndDeliverySevice"); //服务请求路径    
        serverFactoryBean2.setServiceBean(saleAndDeliverySevice);    
        serverFactoryBean2.create();
        /*
         * 销售开票、冲销流程生成webservices
         */
        SellService sellService = new SellImpl();
        //JaxWsServerFactoryBean
        ServerFactoryBean serverFactoryBean3 = new ServerFactoryBean(); //server工厂    
        serverFactoryBean3.setServiceClass(SellService.class);// 接口类   
        serverFactoryBean3.setAddress("/SellService"); //服务请求路径    
        serverFactoryBean3.setServiceBean(sellService);    
        serverFactoryBean3.create();
        /*
         * PM设备领用流程生成webservices
         */
        EquipmentReceiveService equipmentReceiveImpl = new EquipmentReceiveImpl();
        //JaxWsServerFactoryBean
        ServerFactoryBean serverFactoryBean4 = new ServerFactoryBean(); //server工厂    
        serverFactoryBean4.setServiceClass(EquipmentReceiveService.class);// 接口类   
        serverFactoryBean4.setAddress("/EquipmentReceiveService"); //服务请求路径    
        serverFactoryBean4.setServiceBean(equipmentReceiveImpl);    
        serverFactoryBean4.create();
	}
}
