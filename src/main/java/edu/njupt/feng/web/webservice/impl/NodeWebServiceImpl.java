package edu.njupt.feng.web.webservice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.common.NodeMapItem;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.common.ResultInfoWithoutContent;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.management.NodeMap;
import edu.njupt.feng.web.management.ServiceMap;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.utils.mysql.MySQLUtil;
import edu.njupt.feng.web.webservice.NodeWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.*;

public class NodeWebServiceImpl implements NodeWebService {

    private NodeServiceInfo nodeServiceInfo;

    private Map<Integer,NodeServiceListItem> serviceInfoList = new HashMap<>();

    /**
     * 更新节点名称
     * @param name
     */
    @Override
    public void updateName(String name) {
        nodeServiceInfo.setName(name);
    }

    /**
     * 更新所属服务的名称
     * @param name
     * @param serviceID
     */
    @Override
    public void updateServiceName(String name,int serviceID) {
        serviceInfoList.get(serviceID).setName(name);
    }

    /**
     * 更新自身修改时间
     * @param modifyTime
     */
    @Override
    public void updateModifyTime(Date modifyTime) {
        nodeServiceInfo.setModifyTime(modifyTime);
    }

    /**
     * 更新节点位置
     * @param position
     */
    @Override
    public void updatePosition(Position position) {
        nodeServiceInfo.setPosition(position);
    }

    /**
     * 添加服务
     * @param serviceInfo
     */
    @Override
    public void addService(ServiceInfo serviceInfo) {
        serviceInfoList.put(serviceInfo.getId(), Convert2ServiceInfo.serviceInfo2NodeServiceListItem(serviceInfo));
    }

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
        ObjectMapper mapper = new ObjectMapper();
        try{
            String json = mapper.writeValueAsString(attributes);
            MySQLUtil.updateServiceAttributes(json.replaceAll("\\\\","\\\\\\\\"),serviceID);
        }catch (Exception e){

        }
        ServiceMap.updateServiceAttributes(Constants.SERVICE_PREFIX + serviceID,attributes);
    }

    /**
     * 更新其它节点的属性
     * @param attributes
     * @param nodeID
     */
    @Override
    public void updateOtherNodeAttributes(Map<String, String> attributes, Integer nodeID) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress(Constants.NODE_PREFIX + nodeID);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateNodeAttributes(attributes);
    }

    /**
     * 更新其它节点所属的服务的属性
     * @param attributes
     * @param serviceID
     * @param nodeID
     */
    @Override
    public void updateOtherServiceAttributes(Map<String, String> attributes, Integer serviceID, Integer nodeID) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress(Constants.NODE_PREFIX + nodeID);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateServiceAttributes(attributes,serviceID);
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
     * 搜索测试
     * @param keyword
     * @return
     */
    @Override
    public ResultInfoWithoutContent testSearch(String keyword,Integer type) {
        ResultInfoWithoutContent results = new ResultInfoWithoutContent();
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
    public ResultInfoWithoutContent testRecommend(String keyword,Integer type) {
        return testSearch(keyword,type);
    }

    /**
     * 搜索方法测试，通过webservice
     * @param keyword
     * @return
     */
    public ResultInfoWithoutContent recommendMethodTest01ByWebservice(String keyword){

        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();

        //首先，检查自己的服务列表有没有符合要求服务
        if(serviceInfoList!= null && serviceInfoList.values() != null){
            System.out.println(nodeServiceInfo.getAttributes());
            resultInfoWithoutContent.add(sortNodeServiceListItem(new ArrayList<>(serviceInfoList.values()),keyword));
        }

        if ( nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            //遍历关联节点
            for(AssociatedNodeServiceInfo associatedNode : nodeServiceInfo.getAssociatedNodeServiceInfos()){

                //获取关联节点的节点信息
                NodeServiceInfo associatedNodeInfo = getNodeServiceInfo(associatedNode.getServiceAddress());

                System.out.println(associatedNodeInfo.getAttributes());
                //获取关联节点的服务列表
                List<NodeServiceListItem> associatedNodeServicesList = getServiceList(associatedNode.getServiceAddress());

                if (associatedNodeServicesList!=null){
                    resultInfoWithoutContent.add(sortNodeServiceListItem(associatedNodeServicesList,keyword));
                }

            }
        }

        return resultInfoWithoutContent;
    }

    public ResultInfoWithoutContent recommendMethodTest02ByMap(String keyword){
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();

        //首先，检查自己的服务列表有没有符合要求服务
        if(serviceInfoList!= null && serviceInfoList.values() != null){
            resultInfoWithoutContent.add(sortNodeServiceListItem(new ArrayList<>(serviceInfoList.values()),keyword));
        }
        //遍历关联节点
        if(nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()){
                NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
                resultInfoWithoutContent.add(sortNodeServiceListItem(item.getServiceList(),keyword));
            }
        }
        return resultInfoWithoutContent;
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


    /**
     * 测试方法：
     * @param serviceList
     * @param keyword
     * @return
     */
    private ResultInfoWithoutContent sortNodeServiceListItem(List<NodeServiceListItem> serviceList,String keyword){

        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        List<NodeServiceListItem> results = new ArrayList<>();

        long startTime = System.nanoTime();
        if(serviceList != null){
            //首先，检查自己的服务列表有没有符合要求服务
            for(NodeServiceListItem item : serviceList){
                //检查服务的属性信息是否包含关键字
                if(item.getAttributes() != null && item.getAttributes().values() != null){
                    for(String attrValue : item.getAttributes().values()){
                        if (attrValue.contains(keyword)){
                            results.add(item);
                            break;
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        resultInfoWithoutContent.setResult(results);
        resultInfoWithoutContent.setCostTime(endTime - startTime);

        return resultInfoWithoutContent;
    }
}
