package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.service.NodeAllocationService;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodeAllocationController {

    @Autowired
    private NodeAllocationService nodeAllocationService;

    @Autowired
    private NodeService nodeService;

    @RequestMapping("/api/test/node/allocation")
    public JsonData testNodeAllocation(Integer clusterID){
        JsonData data = new JsonData();

        nodeAllocationService.testNodeAllocation(clusterID);

        data.setData(nodeService.getNodeServiceInfo(4));
        return data;
    }

}
