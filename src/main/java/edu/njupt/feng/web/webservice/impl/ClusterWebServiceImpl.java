package edu.njupt.feng.web.webservice.impl;

import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.webservice.ClusterWebService;
import java.util.List;
import java.util.Map;

public class ClusterWebServiceImpl implements ClusterWebService {

    private ClusterServiceInfo clusterInfo;

    private List<NodeServiceInfo> nodeServiceInfoList;

    @Override
    public List<NodeServiceInfo> getNodeListInfo() {
        return nodeServiceInfoList;
    }

    @Override
    public void setNodeListInfo(List<NodeServiceInfo> nodeInfos) {
        nodeServiceInfoList = nodeInfos;
    }

    @Override
    public ClusterServiceInfo getClusterInfo() {
        return clusterInfo;
    }

    @Override
    public void setClusterInfo(ClusterServiceInfo clusterInfo) {
        this.clusterInfo = clusterInfo;
    }

    @Override
    public void updateNodeInfo(Map<String, String> attributes, Integer nodeID) {
        for (NodeServiceInfo nodeServiceInfo : nodeServiceInfoList){
            if(nodeID == nodeServiceInfo.getId()){
                nodeServiceInfo.setAttributes(attributes);
            }
        }
    }
}
