package edu.njupt.feng.web.service.impl;

import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.service.ClusterService;
import edu.njupt.feng.web.service.NodeAllocationService;
import edu.njupt.feng.web.service.NodeService;
import edu.njupt.feng.web.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class NodeAllocationServiceImpl implements NodeAllocationService {

    /**
     * 集群相关服务
     */
    @Autowired
    private ClusterService clusterService;

    /**
     * 节点相关服务
     */
    @Autowired
    private NodeService nodeService;

    /**
     * 服务相关服务
     */
    @Autowired
    private ServiceService serviceService;

    @Override
    public void testNodeAllocation(Integer clusterID) {
        Random random = new Random();
        Integer num = random.nextInt(30) + 1;
        Map<String,String> attr = nodeService.getNodeServiceInfo(1).getAttributes();
        if(attr != null){
            attr.put(String.valueOf(num),String.valueOf(num));
        }else {
            attr = new HashMap<>();
            attr.put(String.valueOf(num),String.valueOf(num));
        }

        nodeService.updateAttributes(attr,1);
    }
}
