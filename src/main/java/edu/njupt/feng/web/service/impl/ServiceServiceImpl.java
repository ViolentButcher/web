package edu.njupt.feng.web.service.impl;

import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.service.ServiceService;

public class ServiceServiceImpl implements ServiceService {

    @Override
    public PageInfo getServiceListWithParams(Integer nodeID, Integer pageNum, String filter, String order, String desc) {
        return null;
    }

    @Override
    public boolean addService(String name, String content, String attributes, Integer nodeID) {
        return false;
    }
}
