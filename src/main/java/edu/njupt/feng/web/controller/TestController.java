package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.management.NodeManagement;
import edu.njupt.feng.web.management.ServiceManagement;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.webservice.NodeWebService;
import edu.njupt.feng.web.webservice.ServiceWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private NodeManagement nodeManagement;

    @Autowired
    private ServiceManagement serviceManagement;

    @Autowired
    private ClusterManagement clusterManagement;

    @Autowired
    private NodeMapper nodeMapper;

    @RequestMapping("/test/api/node/service")
    public JsonData testClusterConvert(Integer id){
        JsonData data = new JsonData();
        data.setData(nodeManagement.testGetNodeServiceInfo(id));
        return data;
    }


    @RequestMapping("/test/api/convert/associatednode")
    public JsonData testClusterConvert(){
        JsonData data = new JsonData();
        NodeServiceInfo nodeInfo = Convert2ServiceInfo.nodeServiceInfo2ServiceInfo(nodeMapper.getNodeInfoByNodeID(1));
        System.out.println(nodeInfo);
        data.setData(nodeInfo.getAssociatedNodeServiceInfos());
        return data;
    }

    @RequestMapping("/test/api/search/test")
    public JsonData testSearch(Integer nodeId,String keyword){
        JsonData data = new JsonData();
        data.setData(nodeManagement.testSearch(nodeId,keyword));
        return data;
    }

    @RequestMapping("/test/api/search/stress")
    public JsonData testStress(Integer nodeId){
        JsonData data = new JsonData();
        for(int i=0;i<100;i++){
            nodeManagement.testSearch(nodeId,String.valueOf(i));
        }
        data.setData(nodeManagement.testSearch(nodeId,String.valueOf(100)));
        return data;
    }

    @RequestMapping("/test/api/node/update")
    public JsonData testUpdateNodeAttr(Integer id){
        JsonData data = new JsonData();

        nodeManagement.testUpdateNodeAttr(id);
        data.setData(nodeManagement.testGetNodeServiceInfo(id));

        return data;
    }

    @RequestMapping("/test/api/service/update")
    public JsonData testUpdateServiceAttr(Integer id){
        JsonData data = new JsonData();

        serviceManagement.testUpdateAttr(id);
        data.setData(serviceManagement.testGetServiceInfo(id));

        return data;
    }




}
