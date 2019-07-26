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
    public PageInfo getClusterInfoList(Integer pageNum,String filter,String order,String desc);

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
     * @param clusterName
     * @param clusterAttr
     */
    public boolean addCluster(String clusterName,String clusterAttr);


    /**
     * 删除集群
     * @param clusterID
     * @return
     */
    public String deleteCluster(int clusterID);
}
