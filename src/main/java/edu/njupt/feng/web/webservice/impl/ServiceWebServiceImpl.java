package edu.njupt.feng.web.webservice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.utils.mysql.MySQLUtil;
import edu.njupt.feng.web.webservice.NodeWebService;
import edu.njupt.feng.web.webservice.ServiceWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.Map;

public class ServiceWebServiceImpl implements ServiceWebService {

    private ServiceServiceInfo serviceServiceInfo;

    @Override
    public void init(ServiceServiceInfo serviceServiceInfo) {
        this.serviceServiceInfo = serviceServiceInfo;
    }

    @Override
    public ServiceServiceInfo getServiceInfo() {
        return serviceServiceInfo;
    }


    @Override
    public void updateServiceAttributes(Map<String, String> attributes) {
        serviceServiceInfo.setAttributes(attributes);

        ObjectMapper mapper = new ObjectMapper();
        try{
            MySQLUtil.updateServiceAttributes(mapper.writeValueAsString(attributes),serviceServiceInfo.getId());
        }catch (Exception e){

        }

        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(Constants.NODE_PREFIX + serviceServiceInfo.getNode());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService webService = factoryBean.create(NodeWebService.class);
        webService.updateServiceAttributes(attributes,serviceServiceInfo.getId());
    }

    @Override
    public String getServiceContent() {
        return serviceServiceInfo.getContent();
    }

    @Override
    public Map<String, String> getServiceAttributes() {
        return serviceServiceInfo.getAttributes();
    }
}
