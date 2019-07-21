package edu.njupt.feng.web.service;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * cluster服务接口
 */
@Service
public interface ClusterService {

    /**
     * 获取cluster列表信息
     * @return
     */
    public PageInfo getClusterInfoList(int pageNum);

    /**
     * 获取集群的节点的分页信息
     * @param clusterID
     * @param pageNum
     * @return
     */
    public PageInfo getClusterNodeList(int clusterID,int pageNum);

    /**
     * 加载集群
     * @param clusterID
     */
    public boolean loadCluster(int clusterID);

    /**
     * 卸载集群
     * @param clusterID
     * @return
     */
    public boolean uninstallCluster(int clusterID);

    /**
     * 添加集群
     * @param clusterID
     * @param clusterName
     * @param clusterAttr
     */
    public boolean addCluster(int clusterID,String clusterName,String clusterAttr);


    /**
     * 删除集群
     * @param clusterID
     * @return
     */
    public boolean deleteCluster(int clusterID);
}
