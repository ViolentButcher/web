package edu.njupt.feng.web.entity;

import edu.njupt.feng.web.webservice.NodeWebService;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;



public class Node {

    private JaxWsServerFactoryBean service = new JaxWsServerFactoryBean();
    private String address;

    public void init(String address, NodeWebService webService){
        this.address = address;

        service.setServiceBean(webService);
        service.setAddress(address);
        service.create();

    }

    /**
     * 获取服务地址
     * @return
     */
    public String getServiceAddress(){
        return address;
    }


}
