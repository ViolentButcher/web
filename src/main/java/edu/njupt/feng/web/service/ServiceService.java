package edu.njupt.feng.web.service;


import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

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
}
