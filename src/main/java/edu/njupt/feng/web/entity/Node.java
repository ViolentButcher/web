package edu.njupt.feng.web.entity;

import edu.njupt.feng.web.webservice.NodeWebService;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;


public class Node {

    private Server server;
    private String address;

    public void init(String address, NodeWebService webService){
        JaxWsServerFactoryBean service = new JaxWsServerFactoryBean();
        this.address = address;

        service.setServiceBean(webService);
        service.setAddress(address);
        server = service.create();
    }

    /**
     * 获取服务地址
     * @return
     */
    public String getServiceAddress(){
        return address;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
