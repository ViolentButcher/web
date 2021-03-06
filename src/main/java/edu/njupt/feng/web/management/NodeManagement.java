package edu.njupt.feng.web.management;

import edu.njupt.feng.web.entity.Node;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.common.ResultInfo;
import edu.njupt.feng.web.entity.common.ResultInfoWithoutContent;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.utils.CXFClientUtil;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.webservice.NodeWebService;
import edu.njupt.feng.web.webservice.impl.NodeWebServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NodeManagement {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    private Map<Integer, Node> nodeServices = new HashMap<>();

    /**
     * 启动节点
     * @param nodeID
     */
    public void startNode(Integer nodeID){
        if(nodeServices.get(nodeID) == null){
            Node node = new Node();

            NodeWebService webService = new NodeWebServiceImpl();
            //设置节点信息
            webService.setNodeInfo(Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID)));
            //设置节点上的服务信息

            List<ServiceInfo> s = serviceMapper.getServicesInfoByNodeCluster(nodeID);

            webService.setNodeServiceList(Convert2ServiceInfo.listServiceInfo2NodeServiceListItem(s));

            node.init(webService.getNodeInfo().getServiceAddress(),webService);

            System.out.println("启动节点服务：" + webService.getNodeInfo().getServiceAddress());
            nodeServices.put(nodeID,node);

            //全局节点字典添加
            NodeMap.addNode(webService.getNodeInfo(),webService.getNodeServiceList());

            for (ServiceInfo serviceInfo : s){
                ServiceMap.addService(Convert2ServiceInfo.serviceInfo2ServiceInfo(serviceInfo));
            }
        }
    }

    /**
     * 停止节点
     * @param nodeID
     */
    public void stopNode(Integer nodeID){
        nodeServices.get(nodeID).getServer().destroy();
        nodeServices.remove(nodeID);

        NodeMap.removeNode(nodeID);
        for (int serviceID : serviceMapper.getServiceIDsByNodeID(nodeID)){
            ServiceMap.removeService(serviceID);
        }

    }


    /**
     * 搜索测试
     * @param nodeId
     * @param keyword
     * @return
     */
    public ResultInfo testSearch(Integer nodeId,String keyword,Integer type){


        if(nodeServices.get(nodeId) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

            factoryBean.setServiceClass(NodeWebService.class);
            factoryBean.setAddress(nodeServices.get(nodeId).getServiceAddress());
            NodeWebService service = factoryBean.create(NodeWebService.class);
            CXFClientUtil.configTimeout(service);
            ResultInfoWithoutContent results = null;
            try {
                results = service.testSearch(keyword,type);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return addContent(results);
        }
        return null;
    }

    /**
     * 推荐测试
     * @param nodeId
     * @param keyword
     * @param type
     * @return
     */
    public ResultInfo testRecommend(Integer nodeId,String keyword,Integer type){
        if(nodeServices.get(nodeId) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

            factoryBean.setServiceClass(NodeWebService.class);
            factoryBean.setAddress(nodeServices.get(nodeId).getServiceAddress());
            NodeWebService service = factoryBean.create(NodeWebService.class);
            CXFClientUtil.configTimeout(service);
            ResultInfoWithoutContent results = service.testRecommend(keyword,type);

            return addContent(results);
        }
        return null;
    }

    /**
     * 整合搜索结果
     * @param results
     * @return
     */
    public ResultInfo addContent(ResultInfoWithoutContent results){
        List<ServiceServiceInfo> serviceInfos = new ArrayList<>();
        if (results.getResult() != null){
            for (NodeServiceListItem item : results.getResult()){
                serviceInfos.add(Convert2ServiceInfo.serviceInfo2ServiceInfo(serviceMapper.getServiceInfo(item.getId())));
            }
        }

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(serviceInfos);
        DecimalFormat df = new DecimalFormat("###.000");
        resultInfo.setCostTime(df.format(Double.valueOf(results.getCostTime())/1000000));
        return resultInfo;
    }

    /**
     * 更新节点名称
     * @param name
     * @param nodeID
     */
    public void updateNodeName(String name,int nodeID){
        NodeServiceInfo nodeServiceInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID));
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(nodeServiceInfo.getServiceAddress());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateName(name);
    }

    /**
     * 更新节点位置信息
     * @param position
     * @param nodeID
     */
    public void updateNodePosition(Position position, int nodeID){
        NodeServiceInfo nodeServiceInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID));
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(nodeServiceInfo.getServiceAddress());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updatePosition(position);
    }

    /**
     * 更细节点属性
     * @param attr
     * @param nodeID
     */
    public void updateNodeAttributes(Map<String,String> attr,int nodeID){
        NodeServiceInfo nodeServiceInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID));
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(nodeServiceInfo.getServiceAddress());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateNodeAttributes(attr);

        NodeMap.getNodeServiceInfo(nodeServiceInfo.getServiceAddress()).getNodeServiceInfo().setAttributes(attr);
    }

    /**
     * 为运行的节点添加服务
     * @param serviceInfo
     */
    public void startService(ServiceInfo serviceInfo){
        NodeServiceInfo nodeServiceInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(serviceInfo.getNode()));
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(nodeServiceInfo.getServiceAddress());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);

        NodeServiceListItem item = Convert2ServiceInfo.serviceInfo2NodeServiceListItem(serviceInfo);
        if(service.getNodeServiceInfoByNodeMap(item.getServiceAddress()) == null){
            service.addService(serviceInfo);
        }
        ServiceMap.addService(Convert2ServiceInfo.serviceInfo2ServiceInfo(serviceInfo));
    }

    /**
     * 更新服务名称
     * @param name
     * @param serviceID
     */
    public void updateServiceName(String name,int serviceID){
        ServiceServiceInfo serviceInfo = Convert2ServiceInfo.serviceInfo2ServiceInfo(serviceMapper.getServiceInfo(serviceID));
        NodeServiceInfo nodeServiceInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(serviceInfo.getNode()));
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(nodeServiceInfo.getServiceAddress());
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateServiceName(name,serviceID);

        for(NodeServiceListItem item : NodeMap.getNodeServiceInfo(nodeServiceInfo.getServiceAddress()).getServiceList()){
            if (item.getId() == serviceID){
                item.setName(name);
            }
        }
        ServiceMap.getServiceInfo(serviceInfo.getServiceAddress()).setName(name);
    }
}
