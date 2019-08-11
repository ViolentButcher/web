package edu.njupt.feng.web.webservice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import edu.njupt.feng.web.webservice.ServiceWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feng.util;



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
            results = recommendMethodTest01ByWebservice(keyword,0.5f);
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
    public ResultInfoWithoutContent recommendMethodTest01ByWebservice(String keyword,float limit){

        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        if(serviceInfoList!= null && serviceInfoList.values() != null){

            //初始化attributes中的————temp_Nextlist:[{},{},~~~]
            for(NodeServiceListItem service_item : serviceInfoList.values()){
                service_item.getAttributes().put("temp_Nextlist","");
                updateServiceAttributes(service_item.getAttributes(),service_item.getId());
            }
            //用于存储所有服务的邻接表 service：service邻接表
            //service邻接表格式：ArrayList<Map<String,String>>
            //Map<String,String>———— "id":id,"Sim":sim
            Map<NodeServiceListItem,ArrayList<Map<String,String>>> L =new HashMap<>();
            for(NodeServiceListItem service_item : serviceInfoList.values()){
                ArrayList<Map<String,String>> item_Nextlist=new ArrayList<>();
                item_Nextlist.clear();
                L.put(service_item,item_Nextlist);
            }

            //计算Sim
            for(NodeServiceListItem service_item : serviceInfoList.values())
            {
                int id=service_item.getId();
                float[] vec=util.String_2_floatList(service_item.getAttributes().get("vec"));
                for(NodeServiceListItem service_item_2 : serviceInfoList.values())
                {
                    int id_2=service_item_2.getId();
                    float[] vec_2=util.String_2_floatList(service_item_2.getAttributes().get("vec"));
                    if(id != id_2)
                    {
                        float Sim=util.dot(vec,vec_2);
                        if(Sim>limit)
                        {
                            //向service_item中添加邻接顶点（服务）
                            Map<String,String> next_service2=new HashMap<>();
                            next_service2.put("id",Integer.toString(id_2));
                            next_service2.put("Sim",Float.toString(Sim));
                            L.get(service_item).add(next_service2);

                            //向service_item_2中添加邻接顶点（服务）
                            Map<String,String> next_service1=new HashMap<>();
                            next_service1.put("id",Integer.toString(id));
                            next_service1.put("Sim",Float.toString(Sim));
                            L.get(service_item_2).add(next_service1);
                        }
                    }
                }
            }

            Gson gson=new Gson();
            //对L中的所有邻接表添加到相应service的attributes中。
            for(NodeServiceListItem service_item : serviceInfoList.values()){
                service_item.getAttributes().put("temp_Nextlist",gson.toJson(L.get(service_item)));
                updateServiceAttributes(service_item.getAttributes(),service_item.getId());
            }
        }
        return resultInfoWithoutContent;
    }

    public ResultInfoWithoutContent recommendMethodTest02ByMap(String keyword){
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();

        //首先，检查自己的服务列表有没有符合要求服务
        if(serviceInfoList!= null && serviceInfoList.values() != null){
            //resultInfoWithoutContent.add(sortNodeServiceListItem(new ArrayList<>(serviceInfoList.values()),keyword));
            for(NodeServiceListItem service_item : serviceInfoList.values()){
                //service_item   id : {journal=中国矿业大学(北京), author=潘莉, publish_time=2016年, title=南水北调北京受水区供水调适与管理, keyword=北京市}
                System.out.print(service_item.getId());
                System.out.println(service_item.getAttributes());
            }

//            for (int i = 1; i < serviceInfoList.size()+1; i++) {
//                System.out.println(serviceInfoList.get(i).getId());
//            }
        }
        //遍历关联节点
        if(nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()){
                NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
                //resultInfoWithoutContent.add(sortNodeServiceListItem(item.getServiceList(),keyword));

                if (item.getServiceList()!=null){
                    System.out.println("\n当前节点为："+nodeServiceInfo.getId());
                    for(NodeServiceListItem service_item : item.getServiceList()){
                        service_item.getId();
                        //System.out.print(item.getId());
                    }
//                    resultInfoWithoutContent.add(sortNodeServiceListItem(associatedNodeServicesList,keyword));
                }
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
