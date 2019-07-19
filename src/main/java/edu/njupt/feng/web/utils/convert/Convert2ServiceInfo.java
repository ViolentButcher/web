package edu.njupt.feng.web.utils.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.utils.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类型转换的工具类
 *      数据库 -> service
 */
public class Convert2ServiceInfo {

    /**
     * 类型转换：
     *      cluster 数据库 -> service
     * @param clusterInfo
     * @return
     */
    public static ClusterServiceInfo clusterInfo2ServiceInfo(ClusterInfo clusterInfo){
        ClusterServiceInfo clusterServiceInfo = new ClusterServiceInfo();

        clusterServiceInfo.setId(clusterInfo.getId());
        clusterServiceInfo.setName(clusterInfo.getName());
        clusterServiceInfo.setConfiguration(clusterInfo.getConfiguration());
        clusterServiceInfo.setNodeNumber(clusterInfo.getNodeNumber());
        clusterServiceInfo.setState(clusterInfo.getState());
        clusterServiceInfo.setCreateTime(clusterInfo.getCreateTime());
        clusterServiceInfo.setModifyTime(clusterInfo.getModifyTime());
        clusterServiceInfo.setType(clusterInfo.getType());

        
        //设置服务属性
        String attributes = clusterInfo.getAttribute();
        if(attributes != null){
            ObjectMapper mapper = new ObjectMapper();
            try{
                clusterServiceInfo.setAttribute(mapper.readValue(attributes, Map.class));
            }catch (Exception e){
                clusterServiceInfo.setAttribute(null);
            }
        }else {
            clusterServiceInfo.setAttribute(null);
        }
        
        //设置服务地址
        clusterServiceInfo.setServiceAddress(Constants.CLUSTER_PREFIX + clusterServiceInfo.getId());
        
        return clusterServiceInfo;
    }

    /**
     * 类型转换：
     *      node 数据库 -> service
     * @param nodeInfo
     * @return
     */
    public static NodeServiceInfo nodeServiceInfo2ServiceInfo(NodeInfo nodeInfo){

        NodeServiceInfo nodeServiceInfo = new NodeServiceInfo();

        nodeServiceInfo.setId(nodeInfo.getId());
        nodeServiceInfo.setName(nodeInfo.getName());
        nodeServiceInfo.setCluster(nodeInfo.getCluster());
        nodeServiceInfo.setLevel(nodeInfo.getLevel());
        nodeServiceInfo.setServiceNumber(nodeInfo.getServiceNumber());
        nodeServiceInfo.setCreateTime(nodeInfo.getCreateTime());
        nodeServiceInfo.setModifyTime(nodeInfo.getModifyTime());
        
        ObjectMapper mapper = new ObjectMapper();
        
        //设置服务属性
        String attributes = nodeInfo.getAttributes();
        if(attributes != null){
            try{
                nodeServiceInfo.setAttributes(mapper.readValue(attributes, Map.class));
            }catch (Exception e){
                nodeServiceInfo.setAttributes(null);
            }
        }else {
            nodeServiceInfo.setAttributes(null);
        }
        
        //设置关联节点
        String associatedNodes = nodeInfo.getAssociatedNodes();
        if(associatedNodes != null){
            try{
                List<AssociatedNodeInfo> associatedNodeInfos = mapper.readValue(associatedNodes, new TypeReference<List<AssociatedNodeInfo>>(){});
                List<AssociatedNodeServiceInfo> associatedNodeServiceInfos = new ArrayList<>();
                for(AssociatedNodeInfo associatedNodeInfo : associatedNodeInfos){
                    AssociatedNodeServiceInfo info = new AssociatedNodeServiceInfo();
                    info.setId(associatedNodeInfo.getId());
                    info.setAssociatedType(associatedNodeInfo.getAssociatedType());
                    info.setServiceAddress(Constants.NODE_PREFIX + info.getId());
                    associatedNodeServiceInfos.add(info);
                }
                nodeServiceInfo.setAssociatedNodeServiceInfos(associatedNodeServiceInfos);
            }catch (Exception e){
                nodeServiceInfo.setAssociatedNodeServiceInfos(null);
            }
        }else {
            nodeServiceInfo.setAssociatedNodeServiceInfos(null);
        }

        //设置关联节点
        String position = nodeInfo.getPosition();
        if(position != null){
            try{
                nodeServiceInfo.setPosition(mapper.readValue(position, Position.class)) ;
            }catch (Exception e){
                nodeServiceInfo.setPosition(null);
            }
        }else {
            nodeServiceInfo.setPosition(null);
        }

        //设置服务地址
        nodeServiceInfo.setServiceAddress(Constants.NODE_PREFIX + nodeServiceInfo.getId());

        return nodeServiceInfo;
        
    }

    /**
     * 类型转换：
     *      service 数据库 -> service
     * @param serviceInfo
     * @return
     */
    public static ServiceServiceInfo serviceInfo2ServiceInfo(ServiceInfo serviceInfo){
        ServiceServiceInfo serviceServiceInfo = new ServiceServiceInfo();

        serviceServiceInfo.setId(serviceInfo.getId());
        serviceServiceInfo.setName(serviceInfo.getName());
        serviceServiceInfo.setCreateTime(serviceInfo.getCreateTime());
        serviceServiceInfo.setModifyTime(serviceInfo.getModifyTime());
        serviceServiceInfo.setNode(serviceInfo.getNode());
        serviceServiceInfo.setCluster(serviceInfo.getCluster());

        ObjectMapper mapper = new ObjectMapper();
        serviceServiceInfo.setContent(serviceInfo.getContent());

        //设置服务属性
        String attributes = serviceInfo.getAttributes();
        if(attributes != null){
            try{
                serviceServiceInfo.setAttributes(mapper.readValue(attributes, Map.class) );
            }catch (Exception e){
                serviceServiceInfo.setAttributes(null);
            }
        }else {
            serviceServiceInfo.setAttributes(null);
        }

        //设置服务地址
        serviceServiceInfo.setServiceAddress(Constants.SERVICE_PREFIX + serviceServiceInfo.getId());

        return serviceServiceInfo;
    }

    public static NodeServiceListItem serviceInfo2NodeServiceListItem(ServiceInfo serviceInfo){
        NodeServiceListItem nodeServiceListItem = new NodeServiceListItem();
        nodeServiceListItem.setId(serviceInfo.getId());
        nodeServiceListItem.setName(serviceInfo.getName());
        nodeServiceListItem.setCreateTime(serviceInfo.getCreateTime());
        nodeServiceListItem.setModifyTime(serviceInfo.getModifyTime());
        nodeServiceListItem.setNode(serviceInfo.getNode());
        nodeServiceListItem.setCluster(serviceInfo.getCluster());
        ObjectMapper mapper = new ObjectMapper();
        //设置服务属性
        String attributes = serviceInfo.getAttributes();
        if(attributes != null){
            try{
                nodeServiceListItem.setAttributes(mapper.readValue(attributes, Map.class) );
            }catch (Exception e){
                nodeServiceListItem.setAttributes(null);
            }
        }else {
            nodeServiceListItem.setAttributes(null);
        }

        //设置服务地址
        nodeServiceListItem.setServiceAddress(Constants.SERVICE_PREFIX + nodeServiceListItem.getId());


        return nodeServiceListItem;
    }

    public static List<ClusterServiceInfo> listClusterInfo2ServiceInfo(List<ClusterInfo> clusterInfos){
        List<ClusterServiceInfo> clusterServiceInfos = new ArrayList<>();
        for(ClusterInfo clusterInfo : clusterInfos){
            clusterServiceInfos.add(clusterInfo2ServiceInfo(clusterInfo));
        }
        return clusterServiceInfos;
    }

    public static List<NodeServiceInfo> listNodeServiceInfo2ServiceInfo(List<NodeInfo> nodeInfos){
        List<NodeServiceInfo> nodeServiceInfos = new ArrayList<>();
        for(NodeInfo nodeInfo : nodeInfos){
            nodeServiceInfos.add(nodeServiceInfo2ServiceInfo(nodeInfo));
        }
        return nodeServiceInfos;
    }

    public static List<ServiceServiceInfo> listServiceInfo2ServiceInfo(List<ServiceInfo> serviceInfos){
        List<ServiceServiceInfo> serviceServiceInfos = new ArrayList<>();
        for(ServiceInfo serviceInfo : serviceInfos){
            serviceServiceInfos.add(serviceInfo2ServiceInfo(serviceInfo));
        }
        return serviceServiceInfos;
    }

    public static List<NodeServiceListItem> listServiceInfo2NodeServiceListItem(List<ServiceInfo> serviceInfos){
        List<NodeServiceListItem> nodeServiceListItems = new ArrayList<>();
        for(ServiceInfo serviceInfo : serviceInfos){
            nodeServiceListItems.add(serviceInfo2NodeServiceListItem(serviceInfo));
        }
        return nodeServiceListItems;
    }
}
