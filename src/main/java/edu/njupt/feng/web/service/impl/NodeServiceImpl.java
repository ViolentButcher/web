package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.NodeService;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public void updateAssoicatedNodes(Integer nodeID, List<AssociatedNodeInfo> associatedNodeInfos) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String associatedNodes = mapper.writeValueAsString(associatedNodeInfos);
            System.out.println(associatedNodes + "-----------------");
            nodeMapper.updateAssoicatedNode(associatedNodes,nodeID);
        }catch (Exception e){

        }

    }

    @Override
    public void updateAttributes(Map<String, String> attributes, Integer nodeID) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            nodeMapper.updateNodeAttr(mapper.writeValueAsString(attributes),nodeID);
        }catch (Exception e){

        }
    }

    @Override
    public void updatePosition(Position position, Integer nodeID) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            nodeMapper.updatePosition(mapper.writeValueAsString(position),nodeID);
        }catch (Exception e){

        }
    }

    @Override
    public void updateCluster(Integer cluster, Integer nodeID) {
        nodeMapper.updateCluster(cluster, nodeID);
    }

    @Override
    public void updateServiceNumber(Integer serviceNumber, Integer nodeID) {
        nodeMapper.updateServiceNumber(serviceNumber, nodeID);
    }

    @Override
    public void updateLevel(Integer level, Integer nodeID) {
        nodeMapper.updateLevel(level, nodeID);
    }

    @Override
    public void updateCreateTime(Date createTime, Integer nodeID) {
        nodeMapper.updateCreateTime(createTime, nodeID);
    }

    @Override
    public void updateModifyTime(Date modifyTime, Integer nodeID) {
        nodeMapper.updateModifyTime(modifyTime, nodeID);
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
            nodeInfo.setPosition(position);
            nodeInfo.setCreateTime(new Date());
            nodeInfo.setModifyTime(new Date());
            nodeInfo.setServiceNumber(0);
            nodeMapper.addNode(nodeInfo);
            return true;
        }
        return false;
    }

    @Override
    public void deleteNode(Integer nodeID) {
        nodeMapper.deleteNode(nodeID);
    }

    @Override
    public PageInfo getAllNodeList(Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getAllNodeList(filter, order, desc);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    @Override
    public NodeServiceInfo getNodeServiceInfo(Integer nodeID) {
        return Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID));
    }

    @Override
    public List<NodeServiceInfo> getNodeServiceInfoListByClusterID(Integer clusterID) {
        return Convert2ServiceInfo.listNodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfosListByClusterID(clusterID));
    }

    @Override
    public void updateAllNodeServiceNumber(Integer clusterID) {
        for(Integer nodeID : nodeMapper.getNodeListsByClusterID(clusterID)){
            updateServiceNumber(serviceMapper.countNodeServiceNumber(nodeID),nodeID);
        }
    }
}
