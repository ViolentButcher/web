package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public void updateAssoicatedNodes(Integer nodeID, List<AssociatedNodeInfo> associatedNodeInfos) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String associatedNodes = mapper.writeValueAsString(associatedNodeInfos);
            nodeMapper.updateAssoicatedNode(associatedNodes,nodeID);
        }catch (Exception e){

        }

    }


    @Override
    public List<NodeInfo> getNodeInfos(Integer clusterID) {
        return nodeMapper.getNodeInfosListByClusterID(clusterID);
    }

    @Override
    public void configureNodes(Integer clusterID) {

    }
}
