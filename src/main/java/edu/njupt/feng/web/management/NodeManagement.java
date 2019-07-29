package edu.njupt.feng.web.management;

import edu.njupt.feng.web.entity.Node;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NodeManagement {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ServiceManagement serviceManagement;

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
            webService.setNodeServiceList(Convert2ServiceInfo.listServiceInfo2NodeServiceListItem(serviceMapper.getServicesInfoByNodeCluster(nodeID)));

            node.init(webService.getNodeInfo().getServiceAddress(),webService);

            System.out.println("启动节点服务：" + webService.getNodeInfo().getServiceAddress());
            nodeServices.put(nodeID,node);

            //全局节点字典添加
            NodeMap.addNode(webService.getNodeInfo(),webService.getNodeServiceList());

            for(Integer serviceID : serviceMapper.getServiceIDsByNodeID(nodeID)){
                serviceManagement.startService(serviceID);
            }
        }
    }


    public NodeServiceInfo testGetNodeServiceInfo(Integer nodeID){
        if(nodeServices.get(nodeID) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

            factoryBean.setServiceClass(NodeWebService.class);
            factoryBean.setAddress(nodeServices.get(nodeID).getServiceAddress());
            NodeWebService service = factoryBean.create(NodeWebService.class);
            return service.getNodeServiceInfo("http://localhost:8081/node2");
        }
        return null;
    }

    /**
     * 搜索测试
     * @param nodeId
     * @param keyword
     * @return
     */
    public List<ServiceServiceInfo> testSearch(Integer nodeId,String keyword,Integer type){
        if(nodeServices.get(nodeId) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

            factoryBean.setServiceClass(NodeWebService.class);
            factoryBean.setAddress(nodeServices.get(nodeId).getServiceAddress());
            NodeWebService service = factoryBean.create(NodeWebService.class);
            CXFClientUtil.configTimeout(service);
            return service.testSearch(keyword,type);
        }
        return null;
    }

    /**
     * 推荐测试示例
     * @param nodeId
     * @param keyword
     * @return
     */
    public List<ServiceServiceInfo> testRecommend(Integer nodeId,String keyword,Integer type){
        if(nodeServices.get(nodeId) != null){
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

            factoryBean.setServiceClass(NodeWebService.class);
            factoryBean.setAddress(nodeServices.get(nodeId).getServiceAddress());
            NodeWebService service = factoryBean.create(NodeWebService.class);
            return service.testRecommend(keyword,type);
        }
        return null;
    }

    /**
     * 更新节点属性的测试
     * @param nodeId
     */
    public void testUpdateNodeAttr(Integer nodeId){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setServiceClass(NodeWebService.class);
        factoryBean.setAddress(nodeServices.get(nodeId).getServiceAddress());
        NodeWebService service = factoryBean.create(NodeWebService.class);
        Map<String,String> map = new HashMap<>();
        map.put("test","test");
        service.updateNodeAttributes(map);
    }


}
