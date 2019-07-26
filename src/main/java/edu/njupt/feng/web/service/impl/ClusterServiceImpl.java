package edu.njupt.feng.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
}
