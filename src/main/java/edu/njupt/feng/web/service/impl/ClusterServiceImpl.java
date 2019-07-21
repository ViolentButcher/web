package edu.njupt.feng.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public PageInfo getClusterInfoList(int pageNum) {
        PageHelper.startPage(pageNum,10);
        List<ClusterInfo> clusterInfos = clusterMapper.getClusterList();
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
    public boolean addCluster(int clusterID, String clusterName, String clusterAttr) {
        return false;
    }

    @Override
    public boolean deleteCluster(int clusterID) {
        return false;
    }
}
