package edu.njupt.feng.web.entity.common;

import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;

import java.util.List;

public class NodeMapItem {

    private NodeServiceInfo nodeServiceInfo;

    private List<NodeServiceListItem> serviceList;

    public NodeMapItem(NodeServiceInfo nodeServiceInfo, List<NodeServiceListItem> serviceList) {
        this.nodeServiceInfo = nodeServiceInfo;
        this.serviceList = serviceList;
    }

    public NodeServiceInfo getNodeServiceInfo() {
        return nodeServiceInfo;
    }

    public void setNodeServiceInfo(NodeServiceInfo nodeServiceInfo) {
        this.nodeServiceInfo = nodeServiceInfo;
    }

    public List<NodeServiceListItem> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<NodeServiceListItem> serviceList) {
        this.serviceList = serviceList;
    }
}
