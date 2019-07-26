package edu.njupt.feng.web.webservice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.common.NodeMapItem;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.management.NodeMap;
import edu.njupt.feng.web.management.ServiceMap;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.utils.mysql.MySQLUtil;
import edu.njupt.feng.web.webservice.NodeWebService;
import edu.njupt.feng.web.webservice.ServiceWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeWebServiceImpl implements NodeWebService {

    private NodeServiceInfo nodeServiceInfo;

    private Map<Integer,NodeServiceListItem> serviceInfoList = new HashMap<>();

    /**
     * 设置节点信息
     * @param nodeInfo
     */
    @Override
    public void setNodeInfo(NodeServiceInfo nodeInfo) {
        nodeServiceInfo = nodeInfo;
    }

    /**
     * 获取节点信息
     * @return
     */
    @Override
    public NodeServiceInfo getNodeInfo() {
        return nodeServiceInfo;
    }

    /**
     * 获取节点上面的服务列表
     * @return
     */
    @Override
    public List<NodeServiceListItem> getNodeServiceList() {
        return new ArrayList<>(serviceInfoList.values());
    }

    /**
     * 设置节点上的服务列表
     * @param serviceList
     */
    @Override
    public void setNodeServiceList(List<NodeServiceListItem> serviceList) {
        for(NodeServiceListItem serviceInfo : serviceList){
            serviceInfoList.put(serviceInfo.getId(),serviceInfo);
        }
    }

    /**
     * 在节点上注册服务信息
     * @param service
     */
    @Override
    public void registerService(NodeServiceListItem service) {
        serviceInfoList.put(service.getId(),service);
    }

    /**
     * 移除节点上的某个服务信息
     * @param serviceID
     */
    @Override
    public void removeService(Integer serviceID) {
        serviceInfoList.remove(serviceID);
    }

    /**
     * 更新节点属性信息（自身）
     * @param attributes
     */
    @Override
    public void updateNodeAttributes(Map<String, String> attributes) {
        nodeServiceInfo.setAttributes(attributes);

        //全局NodeMap的属性更新
        NodeMap.updateNodeAttributes(nodeServiceInfo.getServiceAddress(),attributes);

        ObjectMapper mapper = new ObjectMapper();
        try{
            MySQLUtil.updateNodeAttributes(mapper.writeValueAsString(attributes),nodeServiceInfo.getId());
        }catch (Exception e){

        }
    }

    /**
     * 更新服务属性
     * @param attributes
     * @param serviceID
     */
    @Override
    public void updateServiceAttributes(Map<String, String> attributes, Integer serviceID) {
        serviceInfoList.get(serviceID).setAttributes(attributes);
    }


    @Override
    public void updateOtherNodeAttributes(Map<String, String> attributes, Integer nodeID) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress(Constants.NODE_PREFIX + nodeID);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateNodeAttributes(attributes);
    }

    /**
     * 访问其它节点，获取其服务列表信息
     * @param address
     * @return
     */
    @Override
    public List<NodeServiceListItem> getServiceList(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeServiceList();
    }

    /**
     * 获取节点信息
     * @param address
     * @return
     */
    @Override
    public NodeServiceInfo getNodeServiceInfo(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeInfo();
    }

    /**
     * 获取服务信息
     * @param address
     * @return
     */
    @Override
    public ServiceServiceInfo getServiceInfo(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(ServiceWebService.class);
        ServiceWebService service = factoryBean.create(ServiceWebService.class);
        return service.getServiceInfo();
    }

    /**
     * 搜索测试
     * @param keyword
     * @return
     */
    @Override
    public List<ServiceServiceInfo> testSearch(String keyword,Integer type) {
        List<ServiceServiceInfo> results = null;
        if(type ==1){
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest01ByWebservice(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }else if (type == 2){
            long startTime = System.currentTimeMillis();
            results =  recommendMethodTest02ByMap(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }

    /**
     * 推荐测试
     * @param keyword
     * @return
     */
    @Override
    public List<ServiceServiceInfo> testRecommend(String keyword,Integer type) {
        List<ServiceServiceInfo> results = null;
        if(type ==1){
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest01ByWebservice(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }else if (type == 2){
            long startTime = System.currentTimeMillis();
            results =  recommendMethodTest02ByMap(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }

    /**
     * 搜索方法测试，通过webservice
     * @param keyword
     * @return
     */
    public List<ServiceServiceInfo> recommendMethodTest01ByWebservice(String keyword){
        List<ServiceServiceInfo> results = new ArrayList<>();

        //首先，检查自己的服务列表有没有符合要求服务
        for(NodeServiceListItem item : serviceInfoList.values()){
            ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
            if(serviceInfo.getContent().contains(keyword)){
                results.add(serviceInfo);
            }
        }

        if ( nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            //遍历关联节点
            for(AssociatedNodeServiceInfo associatedNode : nodeServiceInfo.getAssociatedNodeServiceInfos()){

                //获取关联节点的节点信息
                NodeServiceInfo associatedNodeInfo = getNodeServiceInfo(associatedNode.getServiceAddress());
                //获取关联节点的服务列表
                List<NodeServiceListItem> associatedNodeServicesList = getServiceList(associatedNode.getServiceAddress());

                for(NodeServiceListItem item : associatedNodeServicesList){
                    ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
                    if(serviceInfo.getContent().contains(keyword)){
                        results.add(serviceInfo);
                    }
                }
            }
        }

        return results;
    }

    public List<ServiceServiceInfo> recommendMethodTest02ByMap(String keyword){
        List<ServiceServiceInfo> results = new ArrayList<>();
        //遍历自己管理的服务
        for(NodeServiceListItem item : serviceInfoList.values()){
            ServiceServiceInfo serviceInfo = getServiceInfoByServiceMap(item.getServiceAddress());
            if(serviceInfo.getContent().contains(keyword)){
                results.add(serviceInfo);
            }
        }
        //遍历关联节点
        for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()){
            NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
            //遍历关联节点管理的服务
            for (NodeServiceListItem serviceItem :item.getServiceList()){
                ServiceServiceInfo serviceInfo = getServiceInfoByServiceMap(serviceItem.getServiceAddress());
                if(serviceInfo.getContent().contains(keyword)){
                    results.add(serviceInfo);
                }
            }
        }
        return results;
    }



    /**
     * 从node字典中获取node信息
     * @param address
     * @return
     */
    @Override
    public NodeMapItem getNodeServiceInfoByNodeMap(String address) {
        return NodeMap.getNodeServiceInfo(address);
    }

    /**
     * 从字典获取节点信息
     * @param address
     * @return
     */
    @Override
    public ServiceServiceInfo getServiceInfoByServiceMap(String address) {
        return ServiceMap.getServiceInfo(address);
    }
}
