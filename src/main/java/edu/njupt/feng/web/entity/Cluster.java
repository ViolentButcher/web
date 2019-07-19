package edu.njupt.feng.web.entity;

import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.webservice.ClusterWebService;
import edu.njupt.feng.web.webservice.impl.ClusterWebServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cluster {

    /**
     * 集群信息
     */
    private ClusterServiceInfo clusterInfo = new ClusterServiceInfo();

    /**
     * 集群节点列表
     */
    private List<NodeServiceInfo> nodeServiceInfoList = new ArrayList<>();

    /**
     * 集中式节点的中心服务
     */
    private JaxWsServerFactoryBean centreCluster = new JaxWsServerFactoryBean();

    /**
     * 启动集中式节点的服务
     */
    public void startCentreClusterService(){
        if(clusterInfo.getType() == 1){
            ClusterWebService clusterWebService = new ClusterWebServiceImpl();

            clusterWebService.setClusterInfo(clusterInfo);
            clusterWebService.setNodeListInfo(nodeServiceInfoList);

            centreCluster.setServiceBean(clusterWebService);
            centreCluster.setAddress(clusterInfo.getServiceAddress());
            centreCluster.create();

            System.out.println("启动中心节点服务。。。。。。服务地址：" + clusterInfo.getServiceAddress());
        }

    }

    /**
     * 更新节点attributes
     * @param attributes
     * @param nodeID
     */
    public void updateNodeAttr(Map<String,String> attributes,Integer nodeID){
        for(NodeServiceInfo nodeServiceInfo : nodeServiceInfoList){
            if(nodeServiceInfo.getId() == nodeID){
                nodeServiceInfo.setAttributes(attributes);
                break;
            }
        }

        if(getClusterType() == 1){
            JaxWsProxyFactoryBean factory = new  JaxWsProxyFactoryBean();
            factory.setAddress(clusterInfo.getServiceAddress());
            factory.setServiceClass(ClusterWebService.class);
            ClusterWebService clusterWebService = factory.create(ClusterWebService.class);
            clusterWebService.updateNodeInfo(attributes,nodeID);
        }
    }

    public Integer getClusterType() {
        return clusterInfo.getType();
    }

    public void setClusterType(Integer clusterType) {
        clusterInfo.setType(clusterType);
    }

    public ClusterServiceInfo getClusterInfo() {
        return clusterInfo;
    }

    public void setClusterInfo(ClusterServiceInfo clusterInfo) {
        this.clusterInfo = clusterInfo;
    }

    public List<NodeServiceInfo> getNodeServiceInfoList() {
        return nodeServiceInfoList;
    }

    public void setNodeServiceInfoList(List<NodeServiceInfo> nodeServiceInfoList) {
        this.nodeServiceInfoList = nodeServiceInfoList;
    }
}
