package edu.njupt.feng.web.service;

import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 节点相关服务
 */
@Service
public interface NodeService {

    /**
     * 更新节点关联节点属性
     * @param nodeID
     * @param associatedNodeInfos
     */
    public void updateAssoicatedNodes(Integer nodeID, List<AssociatedNodeInfo> associatedNodeInfos);

    /**
     * 更新节点属性
     * @param attributes
     * @param nodeID
     */
    public void updateAttributes(Map<String,String> attributes, Integer nodeID);

    /**
     * 更新节点位置
     * @param position
     * @param nodeID
     */
    public void updatePosition(Position position,Integer nodeID);

    /**
     * 更新所属集群
     * @param cluster
     * @param nodeID
     */
    public void updateCluster(Integer cluster,Integer nodeID);

    /**
     * 更新服务数量
     * @param serviceNumber
     * @param nodeID
     */
    public void updateServiceNumber(Integer serviceNumber,Integer nodeID);

    /**
     * 更新节点level
     * @param level
     * @param nodeID
     */
    public void updateLevel(Integer level,Integer nodeID);

    /**
     * 更新创建时间
     * @param createTime
     * @param nodeID
     */
    public void updateCreateTime(Date createTime, Integer nodeID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param nodeID
     */
    public void updateModifyTime(Date modifyTime,Integer nodeID);

    /**
     * 获取节点列表
     * @param clusterID
     * @return
     */
    public List<NodeInfo> getNodeInfos(Integer clusterID);

    /**
     * 配置节点
     * @param clusterID
     */
    public void configureNodes(Integer clusterID);

    /**
     * 获取节点列表
     * @param clusterID
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public PageInfo getPageNodeListWithParams(Integer clusterID, Integer pageNum, String filter, String order, String desc);

    /**
     * 添加节点
     * @param nodeName
     * @param attributes
     * @return
     */
    public boolean addNode(String nodeName,Integer cluster,String attributes,String position);

    /**
     * 删除节点
     * @param nodeID
     */
    public void deleteNode(Integer nodeID);

    /**
     * 获取所有节点的列表
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public PageInfo getAllNodeList(Integer pageNum, String filter, String order, String desc);

    /**
     * 获取节点信息
     * @param nodeID
     * @return
     */
    public NodeServiceInfo getNodeServiceInfo(Integer nodeID);

    /**
     * 获取集群中节点列表
     * @param clusterID
     * @return
     */
    public List<NodeServiceInfo> getNodeServiceInfoListByClusterID(Integer clusterID);

}
