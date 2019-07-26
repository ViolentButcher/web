package edu.njupt.feng.web.webservice.impl;

import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.management.NodeMap;
import edu.njupt.feng.web.webservice.ClusterWebService;
import java.util.List;
import java.util.Map;

public class ClusterWebServiceImpl implements ClusterWebService {

    private ClusterServiceInfo clusterInfo;

    private List<NodeServiceInfo> nodeServiceInfoList;

    /**
     * 获取集群中所有的节点信息
     * @return
     */
    @Override
    public List<NodeServiceInfo> getNodeListInfo() {
        return nodeServiceInfoList;
    }

    /**
     * 初始化节点信息
     * @param nodeInfos
     */
    @Override
    public void setNodeListInfo(List<NodeServiceInfo> nodeInfos) {
        nodeServiceInfoList = nodeInfos;
    }

    /**
     * 获取集群信息
     * @return
     */
    @Override
    public ClusterServiceInfo getClusterInfo() {
        return clusterInfo;
    }

    /**
     * 设置集群信息
     * @param clusterInfo
     */
    @Override
    public void setClusterInfo(ClusterServiceInfo clusterInfo) {
        this.clusterInfo = clusterInfo;
    }

    /**
     * 更新节点属性
     * @param attributes
     * @param nodeID
     */
    @Override
    public void updateNodeInfo(Map<String, String> attributes, Integer nodeID) {
        for (NodeServiceInfo nodeServiceInfo : nodeServiceInfoList){
            if(nodeID == nodeServiceInfo.getId()){
                nodeServiceInfo.setAttributes(attributes);
            }
        }
    }
}
