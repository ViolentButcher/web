package edu.njupt.feng.web.service;


import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务接口
 */
@Service
public interface ServiceService {

    /**
     * 获取节点的服务列表
     * @param nodeID
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public PageInfo getServiceListWithParams(Integer nodeID, Integer pageNum, String filter, String order, String desc);

    /**
     * 添加服务
     * @param name
     * @param content
     * @param attributes
     * @param nodeID
     * @return
     */
    public boolean addService(String name,String content,String attributes,Integer nodeID);

    /**
     * 获取所有的服务信息
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public PageInfo getAllServicesList(Integer pageNum, String filter, String order, String desc);

    /**
     * 删除指定服务
     * @param serviceID
     * @return
     */
    public String deleteService(Integer serviceID);

    /**
     * 更新名称
     * @param name
     * @param serviceID
     */
    public void updateName(String name,Integer serviceID);

    /**
     * 更新属性
     * @param attributes
     * @param serviceID
     */
    public void updateAttributes(Map<String,String> attributes,Integer serviceID);

    /**
     * 更新节点
     * @param cluster
     * @param serviceID
     */
    public void updateCluster(Integer cluster,Integer serviceID);

    /**
     * 更新节点
     * @param node
     * @param serviceID
     */
    public void updateNode(Integer node,Integer serviceID);

    /**
     * 更新内容
     * @param content
     * @param serviceID
     */
    public void updateContent(String content,Integer serviceID);

    /**
     * 更新创建时间
     * @param createTime
     * @param serviceID
     */
    public void updateCreateTime(Date createTime, Integer serviceID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param serviceID
     */
    public void updateModifyTime(Date modifyTime,Integer serviceID);

    /**
     * 获取服务信息
     * @param serviceID
     * @return
     */
    public ServiceServiceInfo getServiceInfo(Integer serviceID);

    /**
     * 获取集群所有服务
     * @param clusterID
     * @return
     */
    public List<ServiceServiceInfo> getServiceInfoByClusterID(Integer clusterID);

    /**
     * 获取节点所有服务
     * @param nodeID
     * @return
     */
    public List<ServiceServiceInfo> getServiceInfoByNodeID(Integer nodeID);

}
