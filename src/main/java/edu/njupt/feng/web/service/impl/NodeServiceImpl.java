package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.management.NodeManagement;
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

    @Autowired
    private ClusterManagement clusterManagement;

    @Autowired
    private NodeManagement nodeManagement;

    /**
     * 更新关联节点
     * @param nodeID
     * @param associatedNodeInfos
     */
    @Override
    public void updateAssoicatedNodes(Integer nodeID, List<AssociatedNodeInfo> associatedNodeInfos) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String associatedNodes = mapper.writeValueAsString(associatedNodeInfos);
            nodeMapper.updateAssoicatedNode(associatedNodes,nodeID);
        }catch (Exception e){

        }

    }

    /**
     * 更新节点名称
     * @param name
     * @param nodeID
     */
    @Override
    public void updateName(String name, Integer nodeID) {
        nodeMapper.updateName(name, nodeID);
    }

    /**
     * 更新节点属性
     * @param attributes
     * @param nodeID
     */
    @Override
    public void updateAttributes(Map<String, String> attributes, Integer nodeID) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            nodeMapper.updateNodeAttr(mapper.writeValueAsString(attributes),nodeID);
        }catch (Exception e){

        }
        if(clusterManagement.nodeStart(nodeID)){
            nodeManagement.updateNodeAttributes(attributes,nodeID);
        }
    }

    /**
     * 更新位置信息
     * @param position
     * @param nodeID
     */
    @Override
    public void updatePosition(Position position, Integer nodeID) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            nodeMapper.updatePosition(mapper.writeValueAsString(position),nodeID);
        }catch (Exception e){

        }
    }

    /**
     * 更新节点信息
     * @param cluster
     * @param nodeID
     */
    @Override
    public void updateCluster(Integer cluster, Integer nodeID) {
        nodeMapper.updateCluster(cluster, nodeID);
    }

    /**
     * 更新服务数量
     * @param serviceNumber
     * @param nodeID
     */
    @Override
    public void updateServiceNumber(Integer serviceNumber, Integer nodeID) {
        nodeMapper.updateServiceNumber(serviceNumber, nodeID);
    }

    /**
     * 更新层次
     * @param level
     * @param nodeID
     */
    @Override
    public void updateLevel(Integer level, Integer nodeID) {
        nodeMapper.updateLevel(level, nodeID);
    }

    /**
     * 更新创建时间
     * @param createTime
     * @param nodeID
     */
    @Override
    public void updateCreateTime(Date createTime, Integer nodeID) {
        nodeMapper.updateCreateTime(createTime, nodeID);
    }

    /**
     * 更新修改时间
     * @param modifyTime
     * @param nodeID
     */
    @Override
    public void updateModifyTime(Date modifyTime, Integer nodeID) {
        nodeMapper.updateModifyTime(modifyTime, nodeID);
    }

    /**
     * 获取节点信息
     * @param clusterID
     * @return
     */
    @Override
    public List<NodeInfo> getNodeInfos(Integer clusterID) {
        return nodeMapper.getNodeInfosListByClusterID(clusterID);
    }

    /**
     * 配置节点
     * TODO
     * @param clusterID
     */
    @Override
    public void configureNodes(Integer clusterID) {

    }

    /**
     * 获取集群节点列表
     * @param clusterID
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @Override
    public PageInfo getPageNodeListWithParams(Integer clusterID, Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getNodeListWithParams(clusterID, filter, order, desc);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    /**
     * 添加节点
     * @param nodeName
     * @param cluster
     * @param attributes
     * @param position
     * @return
     */
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

    /**
     * 删除节点
     * @param nodeID
     */
    @Override
    public void deleteNode(Integer nodeID) {
        nodeMapper.deleteNode(nodeID);
    }

    /**
     * 获取所有节点列表
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @Override
    public PageInfo getAllNodeList(Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getAllNodeList(filter, order, desc);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    /**
     * 获取节点服务信息
     * @param nodeID
     * @return
     */
    @Override
    public NodeServiceInfo getNodeServiceInfo(Integer nodeID) {
        return Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(nodeID));
    }

    /**
     * 获取节点服务信息
     * @param clusterID
     * @return
     */
    @Override
    public List<NodeServiceInfo> getNodeServiceInfoListByClusterID(Integer clusterID) {
        return Convert2ServiceInfo.listNodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfosListByClusterID(clusterID));
    }

    /**
     * 更新集群所有节点服务数量
     * @param clusterID
     */
    @Override
    public void updateAllNodeServiceNumber(Integer clusterID) {
        for(Integer nodeID : nodeMapper.getNodeListsByClusterID(clusterID)){
            updateServiceNumber(serviceMapper.countNodeServiceNumber(nodeID),nodeID);
        }
    }


    /**
     * 自动配置节点
     * @param clusterID
     * @param rule
     * @param save
     * @param parameter
     */
    @Override
    public void autoConfigNodes(int clusterID, int rule, boolean save, Map<String, String> parameter) {
        System.out.println("clusterID : " + clusterID
                            + "\nrule : " + rule
                            + "\nsave : " + save
                            + "\nparameter : "  + parameter);

    }
}
