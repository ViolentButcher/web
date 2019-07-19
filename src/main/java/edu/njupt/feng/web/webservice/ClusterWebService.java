package edu.njupt.feng.web.webservice;


import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService
public interface ClusterWebService {

    /**
     * 获取集群中所有的节点信息
     * @return
     */
    public List<NodeServiceInfo> getNodeListInfo();

    /**
     * 初始化节点信息
     * @param nodeInfos
     */
    public void setNodeListInfo(List<NodeServiceInfo> nodeInfos);

    /**
     * 获取集群信息
     * @return
     */
    public ClusterServiceInfo getClusterInfo();

    /**
     * 设置集群信息
     * @param clusterInfo
     */
    public void setClusterInfo(ClusterServiceInfo clusterInfo);

    /**
     * 更新节点属性
     * @param attributes
     * @param nodeID
     */
    public void updateNodeInfo(Map<String,String> attributes,Integer nodeID);

}
