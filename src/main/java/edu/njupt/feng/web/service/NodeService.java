package edu.njupt.feng.web.service;

import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
