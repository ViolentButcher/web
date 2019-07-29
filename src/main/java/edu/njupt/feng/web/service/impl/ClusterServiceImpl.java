package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.ClusterService;
import edu.njupt.feng.web.service.NodeService;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ClusterManagement clusterManagement;

    @Autowired
    private NodeService nodeService;

    @Override
    public PageInfo getClusterInfoList(Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<ClusterInfo> clusterInfos = clusterMapper.getClusterListWithParams(filter, order, desc);
        PageInfo<ClusterInfo> pageInfo = new PageInfo<ClusterInfo>(clusterInfos);
        return pageInfo;
    }

    @Override
    public PageInfo getClusterNodeList(int clusterID, int pageNum) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getNodeInfosListByClusterID(clusterID);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    @Override
    public boolean loadCluster(int clusterID) {
        return false;
    }

    @Override
    public boolean uninstallCluster(int clusterID) {
        return false;
    }

    @Override
    public boolean addCluster(String clusterName, String clusterAttr) {
        if(clusterMapper.countClustersByName(clusterName) == 0){
            ClusterInfo clusterInfo = new ClusterInfo();
            clusterInfo.setName(clusterName);
            clusterInfo.setAttribute(clusterAttr);
            clusterInfo.setCreateTime(new Date());
            clusterInfo.setModifyTime(new Date());
            clusterInfo.setState(0);
            clusterMapper.addCluster(clusterInfo);
            return true;
        }
        return false;
    }

    @Override
    public String deleteCluster(int clusterID) {
        if(clusterMapper.getClusterByID(clusterID) != null){
            if(clusterManagement.isStart(clusterID)){
                return "对不起，集群正在运行";
            }
            clusterMapper.deleteCluster(clusterID);
            nodeMapper.deleteNodesByCluster(clusterID);
            serviceMapper.deleteServiceByCluster(clusterID);
            return "删除集群成功";
        }
        return "对不起，该集群不存在";

    }

    @Override
    public void updateAttributes(Map<String, String> attributes, Integer clusterID) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            clusterMapper.updateAttributes(mapper.writeValueAsString(attributes),clusterID);
        }catch (Exception e){

        }

    }

    @Override
    public void updateName(String name, Integer clusterID) {
        clusterMapper.updateName(name, clusterID);
    }

    @Override
    public void updateConfiguration(String configuration, Integer clusterID) {
        clusterMapper.updateConfiguration(configuration, clusterID);
    }

    @Override
    public void updateCreateTime(Date createTime, Integer clusterID) {
        clusterMapper.updateCreateTime(createTime, clusterID);
    }

    @Override
    public void updateModifyTime(Date modifyTime, Integer clusterID) {
        clusterMapper.updateModifyTime(modifyTime, clusterID);
    }

    @Override
    public void updateState(Integer state, Integer clusterID) {
        clusterMapper.updateState(state, clusterID);
    }

    @Override
    public void updateNodeNumber(Integer nodeNumber, Integer clusterID) {
        clusterMapper.updateNodeNumber(nodeNumber, clusterID);
    }

    @Override
    public ClusterServiceInfo getClusterServiceInfo(Integer clusterID) {
        return Convert2ServiceInfo.clusterInfo2ServiceInfo(clusterMapper.getClusterByID(clusterID));
    }

    @Override
    public void updateAllNodeNumver() {
        for(ClusterInfo clusterInfo : clusterMapper.getClusterList()){
            clusterMapper.updateNodeNumber(nodeMapper.countClusterNodeNumber(clusterInfo.getId()),clusterInfo.getId());

            nodeService.updateAllNodeServiceNumber(clusterInfo.getId());
        }
    }
}
