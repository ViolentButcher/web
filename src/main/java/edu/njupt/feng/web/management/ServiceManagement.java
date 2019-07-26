package edu.njupt.feng.web.management;

import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.webservice.ServiceWebService;
import edu.njupt.feng.web.webservice.impl.ServiceWebServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceManagement {

    @Autowired
    private ServiceMapper serviceMapper;

    private Map<Integer, JaxWsServerFactoryBean> services = new HashMap<>();

    /**
     * 启动Service服务
     * @param serviceID
     */
    public void startService(Integer serviceID){
        ServiceServiceInfo serviceInfo = Convert2ServiceInfo.serviceInfo2ServiceInfo(serviceMapper.getServiceInfo(serviceID));
        if(services.get(serviceInfo.getId()) == null){

            JaxWsServerFactoryBean serverFactoryBean = new JaxWsServerFactoryBean();
            ServiceWebService webService = new ServiceWebServiceImpl();
            webService.init(serviceInfo);
            serverFactoryBean.setAddress(serviceInfo.getServiceAddress());
            serverFactoryBean.setServiceBean(webService);
            serverFactoryBean.create();

            System.out.println("启动服务。。。。。。服务地址：" + serviceInfo.getServiceAddress());

            //全局service字典添加service
            ServiceMap.addService(serviceInfo);

            services.put(serviceInfo.getId(),serverFactoryBean);
        }
    }

    /**
     * 测试服务属性的更新
     * @param id
     */
    public void testUpdateAttr(Integer id){
        if(services.get(id) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
            factoryBean.setAddress(Constants.SERVICE_PREFIX + id);
            factoryBean.setServiceClass(ServiceWebService.class);
            ServiceWebService webService = factoryBean.create(ServiceWebService.class);
            Map<String,String> map = new HashMap<>();
            map.put("test","test");
            webService.updateServiceAttributes(map);
        }
    }

    /**
     * 服务信息获取的测试
     * @param id
     * @return
     */
    public ServiceServiceInfo testGetServiceInfo(Integer id){
        if(services.get(id) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
            factoryBean.setAddress(Constants.SERVICE_PREFIX + id);
            factoryBean.setServiceClass(ServiceWebService.class);
            ServiceWebService webService = factoryBean.create(ServiceWebService.class);
            return webService.getServiceInfo();
        }
        return null;
    }

}
