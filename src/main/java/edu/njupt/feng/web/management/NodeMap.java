package edu.njupt.feng.web.management;

import edu.njupt.feng.web.entity.common.NodeMapItem;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局Node字典
 */
public class NodeMap {
    //全局的节点字典变量
    private static Map<String, NodeMapItem> nodeMap = new HashMap<>();


    /**
     * 添加节点
     * @param nodeServiceInfo
     */
    public static void addNode(NodeServiceInfo nodeServiceInfo, List<NodeServiceListItem> serviceList){
        nodeMap.put(nodeServiceInfo.getServiceAddress(),new NodeMapItem(nodeServiceInfo, serviceList));
    }

    /**
     * 更新节点属性
     * @param nodeAddress
     * @param attributes
     */
    public static void updateNodeAttributes(String nodeAddress,Map<String,String> attributes){
        nodeMap.get(nodeAddress).getNodeServiceInfo().setAttributes(attributes);
    }

    /**
     * 获取节点信息
     * @param address
     * @return
     */
    public static NodeMapItem getNodeServiceInfo(String address){
        return nodeMap.get(address);
    }
}
