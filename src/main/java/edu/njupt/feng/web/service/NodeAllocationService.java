package edu.njupt.feng.web.service;

import org.springframework.stereotype.Service;

/**
 * 节点分配服务
 */
@Service
public interface NodeAllocationService {

    /**
     * 节点分配简单测试
     * @param clusterID
     */
    public void testNodeAllocation(Integer clusterID);

}
