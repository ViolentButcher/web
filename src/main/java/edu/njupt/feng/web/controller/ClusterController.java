package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClusterController {

    @Autowired
    private ClusterService clusterService;

    @RequestMapping("/api/cluster/cluster_list")
    public JsonData getClusterList(@RequestParam(defaultValue = "1")Integer pageNum){
        JsonData data = new JsonData();
        data.setData(clusterService.getClusterInfoList(pageNum));
        return data;
    }

}
