package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.management.NodeManagement;
import edu.njupt.feng.web.webservice.NodeWebService;
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

    @RequestMapping("/test/api/update")
    public JsonData testUpdate(){
        JsonData data = new JsonData();

        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress("http://localhost:8081/node105");
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);

        Map<String,String> attr = service.getNodeInfo().getAttributes();
        if(attr == null){
            attr = new HashMap<>();
        }
        attr.put("test1","test1");
        service.updateOtherServiceAttributes(attr,104501,105);

        return data;
    }

}
