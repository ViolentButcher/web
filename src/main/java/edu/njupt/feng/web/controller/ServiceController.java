package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * service相关后端接口
 */
@RestController
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    /**
     * 显示所有节点的信息
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @RequestMapping("/api/service/service_list_all")
    public JsonData getAllServiceList(@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String order, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(serviceService.getAllServicesList(pageNum, filter, order, desc));
        return data;
    }

    @RequestMapping("/api/service/service_list")
    public JsonData getServiceList(Integer nodeID,@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String order, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(serviceService.getServiceListWithParams(nodeID, pageNum, filter, order, desc));
        return data;
    }

    /**
     * 删除服务
     * @param serviceID
     * @return
     */
    @RequestMapping("/api/service/delete")
    public JsonData deleteService(Integer serviceID){
        JsonData data = new JsonData();
        data.setMsg(serviceService.deleteService(serviceID));
        return data;
    }

    @RequestMapping("/api/service/add")
    public JsonData addService(String name,String attributes,String content,Integer node){
        JsonData data = new JsonData();
        data.setMsg("还未实现");
        return data;
    }

}
