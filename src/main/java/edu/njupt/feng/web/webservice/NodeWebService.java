package edu.njupt.feng.web.webservice;

import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService
public interface NodeWebService {

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
     * 在节点上注册服务信息
     * @param service
     */
    public void registerService(NodeServiceListItem service);

    /**
     * 移除节点上的某个服务信息
     * @param serviceID
     */
    public void removeService(Integer serviceID);


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
     * 访问服务地址，获取服务信息
     * @param address
     * @return
     */
    public ServiceServiceInfo getServiceInfo(String address);

    /**
     * 测试搜索示例
     * @param keyword
     * @return
     */
    public List<ServiceServiceInfo> testSearch(String keyword);
}