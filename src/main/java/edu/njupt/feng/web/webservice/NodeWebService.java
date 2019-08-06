package edu.njupt.feng.web.webservice;

import edu.njupt.feng.web.entity.common.NodeMapItem;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.common.ResultInfoWithoutContent;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService
public interface NodeWebService {

    /**
     * 更新节点称
     * @param name
     */
    public void updateName(String name);

    /**
     * 更新所属服务名称
     * @param name
     * @param serviceID
     */
    public void updateServiceName(String name,int serviceID);


    /**
     * 更新节点坐标
     * @param position
     */
    public void updatePosition(Position position);

    /**
     * 添加服务
     * @param serviceInfo
     */
    public void addService(ServiceInfo serviceInfo);

    /**
     * 设置节点信息
     * @param nodeInfo
     */
    public void setNodeInfo(NodeServiceInfo nodeInfo);

    /**
     * 获取节点信息
     * @return
     */
    public NodeServiceInfo getNodeInfo();

    /**
     * 获取节点上面的服务列表
     * @return
     */
    public List<NodeServiceListItem> getNodeServiceList();

    /**
     * 设置节点上的服务列表
     * @param serviceList
     */
    public void setNodeServiceList(List<NodeServiceListItem> serviceList);


    /**
     * 更新节点属性信息（自身）
     * @param attributes
     */
    public void updateNodeAttributes(Map<String,String> attributes);

    /**
     * 更新节点属性（其它）
     * @param attributes
     * @param nodeID
     */
    public void updateOtherNodeAttributes(Map<String,String> attributes,Integer nodeID);

    /**
     * 更新服务属性
     * @param attributes
     * @param serviceID
     */
    public void updateServiceAttributes(Map<String,String> attributes,Integer serviceID);

    /**
     * 访问其它节点，获取其服务列表信息
     * @param address
     * @return
     */
    public List<NodeServiceListItem> getServiceList(String address);

    /**
     * 访问其它节点，获取其节点信息
     * @return
     */
    public NodeServiceInfo getNodeServiceInfo(String address);

    /**
     * 测试搜索示例
     * @param keyword
     * @return
     */
    public ResultInfoWithoutContent testSearch(String keyword, Integer type);

    /**
     * 推荐测试示例
     * @param keyword
     * @return
     */
    public ResultInfoWithoutContent testRecommend(String keyword,Integer type);

    /**
     * 根据全局节点字典返回节点信息
     * @param address
     * @return
     */
    public NodeMapItem getNodeServiceInfoByNodeMap(String address);

    /**
     * 从字典获取节点信息
     * @param address
     * @return
     */
    public ServiceServiceInfo getServiceInfoByServiceMap(String address);

}
