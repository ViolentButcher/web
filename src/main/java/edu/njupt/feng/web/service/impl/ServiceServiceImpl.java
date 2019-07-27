package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.management.NodeMap;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ClusterManagement clusterManagement;

    @Override
    public PageInfo getServiceListWithParams(Integer nodeID, Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<ServiceInfo> serviceInfos = serviceMapper.getServiceListWithParams(nodeID, filter, order, desc);
        PageInfo<ServiceInfo> serviceInfoPageInfo = new PageInfo<>(serviceInfos);
        return serviceInfoPageInfo;
    }

    @Override
    public boolean addService(String name, String content, String attributes, Integer nodeID) {
        return false;
    }

    @Override
    public PageInfo getAllServicesList(Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        List<ServiceInfo> serviceInfos = serviceMapper.getAllServiceListWithParams(filter, order, desc);
        PageInfo<ServiceInfo> serviceInfoPageInfo = new PageInfo<>(serviceInfos);
        return serviceInfoPageInfo;
    }

    @Override
    public String deleteService(Integer serviceID) {
        if(clusterManagement.serviceStart(serviceID)){
            return "对不起，服务正在运行，不能删除";
        }
        serviceMapper.deleteServiceByServiceID(serviceID);
        return "服务删除成功";
    }

    @Override
    public void updateName(String name,Integer serviceID) {
        serviceMapper.updateName(name, serviceID);
    }

    @Override
    public void updateAttributes(Map<String, String> attributes, Integer serviceID) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            serviceMapper.updateServiceAttr(mapper.writeValueAsString(attributes),serviceID);
        }catch (Exception e){

        }
    }

    @Override
    public void updateCluster(Integer cluster, Integer serviceID) {
        serviceMapper.updateCluster(cluster, serviceID);
    }

    @Override
    public void updateNode(Integer node, Integer serviceID) {
        serviceMapper.updateNode(node,serviceID);
        updateCluster(nodeMapper.getNodeInfoByNodeID(node).getCluster(),serviceID);
    }

    @Override
    public void updateContent(String content, Integer serviceID) {
        serviceMapper.updateContent(content,serviceID);
    }

    @Override
    public void updateCreateTime(Date createTime, Integer serviceID) {
        serviceMapper.updateCreateTime(createTime, serviceID);
    }

    @Override
    public void updateModifyTime(Date modifyTime, Integer serviceID) {
        serviceMapper.updateModifyTime(modifyTime, serviceID);
    }
}
