package edu.njupt.feng.web.service;

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
}
