package edu.njupt.feng.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

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
}
