package edu.njupt.feng.web;

import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.mapper.TestMapper;
import edu.njupt.feng.web.service.ClusterService;
import edu.njupt.feng.web.service.NodeService;
import edu.njupt.feng.web.test.TestService;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.webservice.NodeWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private ServiceMapper serviceMapper;


    @Test
    public void contextLoads() {
//
//        List<AssociatedNodeInfo> associatedNodeInfos = new ArrayList<>();
//
//        for (int i : nodeMapper.getNodeListsByClusterID(1)){
//            if(i!=1){
//                AssociatedNodeInfo info = new AssociatedNodeInfo();
//
//                info.setId(i);
//                info.setAssociatedType("test");
//
//                associatedNodeInfos.add(info);
//            }
//        }
//
//        nodeService.updateAssoicatedNodes(1,associatedNodeInfos);

//        testService.createServices();
//        clusterService.updateAllNodeNumver();

//        for (ServiceInfo serviceInfo : testMapper.updateNodeGreater1200()){
//            serviceMapper.updateNode(serviceInfo.getNode()-600,serviceInfo.getId());
//        }
//        clusterService.updateAllNodeNumver();
//        for(ClusterInfo clusterInfo : clusterService.getClusterListWithoutPageInfo()){
//            clusterService.deleteCluster(clusterInfo.getId());
//        }
//        testService.createCluster(2,"集中集群2",5);
//        List<AssociatedNodeInfo> associatedNodeInfos = new ArrayList<>();
//
//        for (int i : nodeMapper.getNodeListsByClusterID(2)){
//            if(i!=101){
//                AssociatedNodeInfo info = new AssociatedNodeInfo();
//
//                info.setId(i);
//
//                associatedNodeInfos.add(info);
//            }
//        }
//
//        nodeService.updateAssoicatedNodes(101,associatedNodeInfos);
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(Constants.NODE_PREFIX + 466);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        for (NodeServiceListItem item :service.getNodeServiceList()){
            System.out.println(item.getName());
            System.out.println(item.getAttributes());
        }
    }

}
