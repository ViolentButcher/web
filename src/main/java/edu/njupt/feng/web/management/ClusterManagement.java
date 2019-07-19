package edu.njupt.feng.web.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.Cluster;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.webservice.ClusterWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
