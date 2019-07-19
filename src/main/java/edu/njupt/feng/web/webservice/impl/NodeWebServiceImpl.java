package edu.njupt.feng.web.webservice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
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

    @Override
    public void setNodeInfo(NodeServiceInfo nodeInfo) {
        nodeServiceInfo = nodeInfo;
    }

    @Override
    public NodeServiceInfo getNodeInfo() {
        return nodeServiceInfo;
    }

    @Override
    public List<NodeServiceListItem> getNodeServiceList() {
        return new ArrayList<>(serviceInfoList.values());
    }

    @Override
    public void setNodeServiceList(List<NodeServiceListItem> serviceList) {
        for(NodeServiceListItem serviceInfo : serviceList){
            serviceInfoList.put(serviceInfo.getId(),serviceInfo);
        }
    }

    @Override
    public void registerService(NodeServiceListItem service) {
        serviceInfoList.put(service.getId(),service);
    }

    @Override
    public void removeService(Integer serviceID) {
        serviceInfoList.remove(serviceID);
    }

    @Override
    public void updateNodeAttributes(Map<String, String> attributes) {
        nodeServiceInfo.setAttributes(attributes);
        ObjectMapper mapper = new ObjectMapper();
        try{
            MySQLUtil.updateNodeAttributes(mapper.writeValueAsString(attributes),nodeServiceInfo.getId());
        }catch (Exception e){

        }
    }

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

    @Override
    public List<NodeServiceListItem> getServiceList(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeServiceList();
    }

    @Override
    public NodeServiceInfo getNodeServiceInfo(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeInfo();
    }

    @Override
    public ServiceServiceInfo getServiceInfo(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(ServiceWebService.class);
        ServiceWebService service = factoryBean.create(ServiceWebService.class);
        return service.getServiceInfo();
    }

    @Override
    public List<ServiceServiceInfo> testSearch(String keyword) {
        List<ServiceServiceInfo> results = new ArrayList<>();

        //首先，检查自己的服务列表有没有符合要求服务
        for(NodeServiceListItem item : serviceInfoList.values()){
            ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
            if(serviceInfo.getContent().contains(keyword)){
                results.add(serviceInfo);
            }
        }

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

        return results;
    }
}
