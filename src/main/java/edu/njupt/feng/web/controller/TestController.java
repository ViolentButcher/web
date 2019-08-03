package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.management.NodeManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private NodeManagement nodeManagement;

    @RequestMapping("/test/api/search/test")
    public JsonData testSearch(Integer nodeId,String keyword,Integer type){
        JsonData data = new JsonData();
        data.setData(nodeManagement.testSearch(nodeId,keyword,type));
        return data;
    }

    @RequestMapping("/test/api/recommend/test")
    public JsonData testRecommend(Integer nodeId,String keyword,Integer type){
        JsonData data = new JsonData();
        data.setData(nodeManagement.testRecommend(nodeId,keyword,type));
        return data;
    }


}
