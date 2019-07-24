package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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


    @Override
    public PageInfo getPageNodeListWithParams(Integer clusterID, Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getNodeListWithParams(clusterID, filter, order, desc);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    @Override
    public boolean addNode(String nodeName, Integer cluster,String attributes,String position) {
        if(nodeMapper.countNodesByName(nodeName) == 0){
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setName(nodeName);
            nodeInfo.setAttributes(attributes);
            nodeInfo.setCluster(cluster);
            nodeInfo.setCreateTime(new Date());
            nodeInfo.setModifyTime(new Date());
            nodeMapper.addNode(nodeInfo);
            return true;
        }
        return false;
    }

    @Override
    public void deleteNode(Integer nodeID) {
        nodeMapper.deleteNode(nodeID);
    }
}
