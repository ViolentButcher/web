package edu.njupt.feng.web.management;

import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.utils.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局服务的管理
 */
public class ServiceMap {

    //全局服务
    private static Map<String,ServiceServiceInfo> serviceMap = new HashMap<>();

    /**
     * 获取全局服务
     * @return
     */
    public static Map<String, ServiceServiceInfo> getServiceMap() {
        return serviceMap;
    }

    /**
     * 更新服务属性
     * @param address
     * @param attributes
     */
    public static void updateServiceAttributes(String address,Map<String,String> attributes){
        serviceMap.get(address).setAttributes(attributes);
    }

    /**
     * 获取服务信息
     * @param address
     * @return
     */
    public static ServiceServiceInfo getServiceInfo(String address){
        return serviceMap.get(address);
    }

    /**
     * 添加服务信息
     * @param serviceServiceInfo
     */
    public static void addService(ServiceServiceInfo serviceServiceInfo){
        serviceMap.put(serviceServiceInfo.getServiceAddress(),serviceServiceInfo);
    }

    /**
     * 删除服务
     * @param serviceID
     */
    public static void removeService(Integer serviceID){
        serviceMap.remove(Constants.SERVICE_PREFIX + serviceID);
    }
}
