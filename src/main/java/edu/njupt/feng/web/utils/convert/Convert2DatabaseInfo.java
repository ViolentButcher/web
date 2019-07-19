package edu.njupt.feng.web.utils.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;

/**
 * 类型转换的工具类
 *      数据库 -> service
 */
public class Convert2DatabaseInfo {

    public static ClusterInfo clusterServiceInfo2Database(ClusterServiceInfo clusterServiceInfo){

        ClusterInfo clusterInfo = new ClusterInfo();

        clusterInfo.setId(clusterServiceInfo.getId());
        clusterInfo.setName(clusterServiceInfo.getName());
        clusterInfo.setConfiguration(clusterServiceInfo.getConfiguration());
        clusterInfo.setCreateTime(clusterServiceInfo.getCreateTime());
        clusterInfo.setModifyTime(clusterServiceInfo.getModifyTime());
        clusterInfo.setNodeNumber(clusterServiceInfo.getNodeNumber());
        clusterInfo.setState(clusterServiceInfo.getState());
        clusterInfo.setType(clusterServiceInfo.getType());

        ObjectMapper mapper = new ObjectMapper();
        try{
            clusterInfo.setAttribute(mapper.writeValueAsString(clusterServiceInfo.getAttribute()));
        }catch (Exception e){
            clusterInfo.setAttribute(null);
        }

        return clusterInfo;
    }

    public static NodeInfo nodeServiceInfo2DataBase(NodeServiceInfo nodeServiceInfo){
        NodeInfo nodeInfo = new NodeInfo();

        nodeInfo.setId(nodeServiceInfo.getId());
        nodeInfo.setName(nodeServiceInfo.getName());
        nodeInfo.setServiceNumber(nodeServiceInfo.getServiceNumber());
        nodeInfo.setCluster(nodeServiceInfo.getCluster());
        nodeInfo.setLevel(nodeServiceInfo.getLevel());
        nodeInfo.setCreateTime(nodeServiceInfo.getCreateTime());
        nodeInfo.setModifyTime(nodeServiceInfo.getModifyTime());

        ObjectMapper mapper = new ObjectMapper();
        try{
            nodeInfo.setAttributes(mapper.writeValueAsString(nodeServiceInfo.getAttributes()));
        }catch (Exception e){
            nodeInfo.setAttributes(null);
        }

        try{
            nodeInfo.setAssociatedNodes(mapper.writeValueAsString(nodeServiceInfo.getAssociatedNodeServiceInfos()));
        }catch (Exception e){
            nodeInfo.setAssociatedNodes(null);
        }

        try{
            nodeInfo.setPosition(mapper.writeValueAsString(nodeServiceInfo.getPosition()));
        }catch (Exception e){
            nodeInfo.setPosition(null);
        }
        return nodeInfo;
    }

    public static ServiceInfo serviceServiceInfo2Database(ServiceServiceInfo serviceServiceInfo){
        ServiceInfo serviceInfo = new ServiceInfo();

        serviceInfo.setId(serviceServiceInfo.getId());
        serviceInfo.setName(serviceServiceInfo.getName());
        serviceInfo.setCluster(serviceServiceInfo.getCluster());
        serviceInfo.setNode(serviceServiceInfo.getNode());
        serviceInfo.setCreateTime(serviceServiceInfo.getCreateTime());
        serviceInfo.setModifyTime(serviceServiceInfo.getModifyTime());
        serviceInfo.setContent(serviceServiceInfo.getContent());
        ObjectMapper mapper = new ObjectMapper();
        try{
            serviceInfo.setAttributes(mapper.writeValueAsString(serviceServiceInfo.getAttributes()));
        }catch (Exception e){
            serviceInfo.setAttributes(null);
        }
        return serviceInfo;
    }
}
