package edu.njupt.feng.web.webservice;


import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import javax.jws.WebService;
import java.util.Map;

@WebService
public interface ServiceWebService {

    /**
     * 初始化服务信息
     * @param serviceServiceInfo
     */
    public void init(ServiceServiceInfo serviceServiceInfo);

    /**
     * 获取服务信息
     * @return
     */
    public ServiceServiceInfo getServiceInfo();


    /**
     * 更新服务属性
     * @param attributes
     */
    public void updateServiceAttributes(Map<String,String> attributes);

    /**
     * 获取服务内容
     * @return
     */
    public String getServiceContent();

    /**
     * 获取服务属性
     * @return
     */
    public Map<String,String> getServiceAttributes();

}
