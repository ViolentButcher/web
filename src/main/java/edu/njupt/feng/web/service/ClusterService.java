package edu.njupt.feng.web.service;

import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 更新属性
     * @param attributes
     * @param clusterID
     */
    public void updateAttributes(Map<String,String> attributes,Integer clusterID);

    /**
     * 更新名称
     * @param name
     * @param clusterID
     */
    public void updateName(String name,Integer clusterID);

    /**
     * 更新配置
     * @param configuration
     * @param clusterID
     */
    public void updateConfiguration(String configuration,Integer clusterID);

    /**
     * 更新创建时间
     * @param createTime
     * @param clusterID
     */
    public void updateCreateTime(Date createTime, Integer clusterID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param clusterID
     */
    public void updateModifyTime(Date modifyTime,Integer clusterID);

    /**
     * 更新状态
     * @param state
     * @param clusterID
     */
    public void updateState(Integer state,Integer clusterID);

    /**
     * 更新节点数量
     * @param nodeNumber
     * @param clusterID
     */
    public void updateNodeNumber(Integer nodeNumber,Integer clusterID);

    /**
     * 获取集群信息
     * @param clusterID
     * @return
     */
    public ClusterServiceInfo getClusterServiceInfo(Integer clusterID);

    /**
     * 更新所有集群的nodeNumber
     */
    public void updateAllNodeNumver();

    /**
     * 获取所有的集群信息，不分页
     * @return
     */
    public List<ClusterInfo> getClusterListWithoutPageInfo();


    /**
     * 加载、卸载集群
     * @param clusterID
     * @return
     */
    public String loadCluster(Integer clusterID);

}
