package edu.njupt.feng.web.service;


import com.github.pagehelper.PageInfo;

/**
 * 服务接口
 */
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

}
