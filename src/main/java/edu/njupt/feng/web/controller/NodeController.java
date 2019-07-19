package edu.njupt.feng.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @RequestMapping("/api/test/node/configure")
    public JsonData configureNode(Integer clusterID){

        //配置节点
        nodeService.configureNodes(clusterID);

        //返回节点列表
        JsonData data = new JsonData();
        data.setData(nodeService.getNodeInfos(clusterID));
        return data;
    }

}
