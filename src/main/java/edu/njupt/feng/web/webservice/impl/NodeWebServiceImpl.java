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
        /*
        该环境（某个节点）下拥有的属性说明：
        serviceInfoList：字典类型，键为从1开始的整数，值为NodeServiceListItem类的对象，
                         该属性表示了模拟节点中的服务列表，每一个NodeServiceListItem类的对象
                         都是一条服务的“索引”，所谓“索引”是指NodeServiceListItem类里面不包括
                         服务的内容，只包括属性等数据（目前最大长度为1000）
        nodeServiceInfo：NodeServiceInfo类的对象，用来存储当前节点的各种属性
        该环境下拥有的方法：
        getServiceInfo：输入服务的地址（可从NodeServiceListItem类的对象
                        中获得），返回ServiceServiceInfo的对象。其中ServiceServiceInfo类
                        与NodeServiceListItem类类似，区别在于其中包含服务内容（该方法需要消
                        耗大量时间，一般只在确定目标服务后再用该方法获取服务内容）
        getNodeServiceInfo：输入节点的地址，返回NodeServiceInfo对象
        getServiceList：输入节点的地址，返回NodeServiceListItem类组成的列表
        常用类的说明：
        NodeServiceListItem：该类对应不包含内容的服务，节点的服务目录中用的就是这个类的对象
        ServiceServiceInfo类：该类对应包含内容的服务，是搜索和推荐返回的结果
        NodeServiceInfo类：该类用于存储节点的各种信息
        AssociatedNodeServiceInfo类：保存当前节点的一个关联节点的信息，包括了关联节点id、关联类
                                     型（例如是parent还是child）、关联节点的地址。在
                                     nodeServiceInfo属性中，associatedNodeServiceInfos属性
                                     用于存放关联节点的列表，而列表中的元素就是这个类

         */


        long startTime =  System.currentTimeMillis();

        List<ServiceServiceInfo> results = new ArrayList<>();

        //首先，检查自己的服务列表有没有符合要求服务
        // getServiceInfo方法慎用，一旦调用了这个方法，就会访问服务内容，消耗很多时间
        for(NodeServiceListItem item : serviceInfoList.values()){
//            ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
//            if(serviceInfo.getContent().contains(keyword)){
//                results.add(serviceInfo);
//            }
        }
//
//        if ( nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
//            //遍历关联节点
//            for(AssociatedNodeServiceInfo associatedNode : nodeServiceInfo.getAssociatedNodeServiceInfos()){
//
//                //获取关联节点的节点信息
//                NodeServiceInfo associatedNodeInfo = getNodeServiceInfo(associatedNode.getServiceAddress());
//                //获取关联节点的服务列表
//                List<NodeServiceListItem> associatedNodeServicesList = getServiceList(associatedNode.getServiceAddress());
//
//                for(NodeServiceListItem item : associatedNodeServicesList){
//                    ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
//                    if(serviceInfo.getContent().contains(keyword)){
//                        results.add(serviceInfo);
//                    }
//                }
//            }
//        }
        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime)/1000;
        System.out.println("Search usedTime: " + usedTime);

        return results;
    }

    @Override
    public List<ServiceServiceInfo> testRecommend(String keyword) {
        long startTime =  System.currentTimeMillis();

        List<ServiceServiceInfo> results = new ArrayList<>();

        //首先，检查自己的服务列表有没有符合要求服务
        for(NodeServiceListItem item : serviceInfoList.values()){
//            ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
//            if(serviceInfo.getContent().contains(keyword)){
//                results.add(serviceInfo);
//            }
        }

        if ( nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            //遍历关联节点

            for(AssociatedNodeServiceInfo associatedNode : nodeServiceInfo.getAssociatedNodeServiceInfos()){

                //获取关联节点的节点信息
                NodeServiceInfo associatedNodeInfo = getNodeServiceInfo(associatedNode.getServiceAddress());
                //获取关联节点的服务列表
                List<NodeServiceListItem> associatedNodeServicesList = getServiceList(associatedNode.getServiceAddress());

                for(NodeServiceListItem item : associatedNodeServicesList){
//                    ServiceServiceInfo serviceInfo = getServiceInfo(item.getServiceAddress());
//                    if(serviceInfo.getContent().contains(keyword)){
//                        results.add(serviceInfo);
//                    }
                }
            }
        }

        long endTime =  System.currentTimeMillis();
        double usedTime = (double)(endTime-startTime)/1000;
        System.out.println("Recommend usedTime: " + usedTime);

        return results;
    }
}
