package edu.njupt.feng.web.management;


import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class ClusterManagement {

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private NodeManagement nodeManagement;


    private Map<Integer, Integer> clusters = new HashMap<>();

    /**
     * 初始化
     */
    public void init(){
        for(ClusterInfo clusterInfo : clusterMapper.getClusterList()){
            if(clusterInfo.getState() == 1){
                startCluster(clusterInfo.getId());
            }
        }
    }


    /**
     * 启动集群
     * @param clusterID
     */
    public void startCluster(Integer clusterID){
        if(clusters.get(clusterID) == null){
            for(Integer nodeID : nodeMapper.getNodeListsByClusterID(clusterID)){
                nodeManagement.startNode(nodeID);
            }
            clusters.put(clusterID,clusterID);
        }
    }

    /**
     * 停止集群
     * @param clusterID
     */
    public void stopCluster(Integer clusterID){
        clusters.remove(clusterID);
        for (int nodeID : nodeMapper.getNodeListsByClusterID(clusterID)){
            nodeManagement.stopNode(nodeID);
        }
    }

    /**
     * 判断集群是否启动
     * @param clusterID
     * @return
     */
    public boolean isStart(Integer clusterID){
        if(clusters.get(clusterID) == null){
            return false;
        }
        return true;
    }

    /**
     * 判断节点是否启动
     * @param nodeID
     * @return
     */
    public boolean nodeStart(Integer nodeID){
        NodeInfo nodeInfo = nodeMapper.getNodeInfoByNodeID(nodeID);

        if(nodeInfo != null){
            if(clusters.get(nodeInfo.getCluster()) == null){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断服务是否启动
     * @param serviceID
     * @return
     */
    public boolean serviceStart(Integer serviceID){
        ServiceInfo serviceInfo =serviceMapper.getServiceInfo(serviceID);

        if(serviceInfo != null){
            if(clusters.get(serviceInfo.getCluster()) == null){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }
}
