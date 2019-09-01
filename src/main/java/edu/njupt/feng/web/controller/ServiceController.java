package edu.njupt.feng.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * service相关后端接口
 */
@RestController
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ClusterManagement clusterManagement;

    /**
     * 显示所有节点的信息
     * @param pageNum
     * @param filter
     * @param orderBy
     * @param desc
     * @return
     */
    @RequestMapping("/api/service/service_list_all")
    public JsonData getAllServiceList(@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String orderBy, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(serviceService.getAllServicesList(pageNum, filter, orderBy, desc));
        return data;
    }

    /**
     * 获取服务列表
     * @param nodeID
     * @param pageNum
     * @param filter
     * @param orderBy
     * @param desc
     * @return
     */
    @RequestMapping("/api/service/service_list")
    public JsonData getServiceList(Integer nodeID,@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String orderBy, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(serviceService.getServiceListWithParams(nodeID, pageNum, filter, orderBy, desc));
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

    /**
     * 添加服务
     * @param name
     * @param attributes
     * @param content
     * @param node
     * @return
     */
    @RequestMapping("/api/service/add")
    public JsonData addService(String name,String attributes,String content,Integer node){
        JsonData data = new JsonData();
        if(clusterManagement.nodeStart(node)){
            data.setMsg("节点已经启动无法添加服务！");
        }else {
            try{
                Map<String,String> a = new HashMap<>();
                String[] attrrs = attributes.replaceAll(" ","").split(",");
                for (String attr : attrrs){
                    a.put(attr.split(":")[0],attr.split(":")[1]);
                }
                ObjectMapper mapper = new ObjectMapper();

                attributes = mapper.writeValueAsString(a);
            }catch (Exception e){
                data.setMsg("属性格式错误");
            }
            if(serviceService.addService(name,content,attributes,node)){
                data.setMsg("添加服务成功");
            }else {
                data.setMsg("添加服务失败");
            }
        }

        return data;
    }

    /**
     * 标准化
     *  TODO
     * @param serviceList
     * @param delete
     * @param strategy
     * @return
     */
    @RequestMapping("/api/service/standardization")
    public JsonData standardization(String serviceList,Boolean delete,Integer strategy){
        JsonData data = new JsonData();
        data.setMsg("选中标准化的服务列表："  + serviceList +
                "\n选中标准化准则：" + strategy +
                "\n是否删除原始数据：" + delete);

        return data;
    }

    /**
     * 服务分配
     *  TODO
     * @param cluster
     * @param strategy
     * @param reserved
     * @return
     */
    @RequestMapping("/api/service/distribution")
    public JsonData startDistribution(Integer cluster, Integer strategy, Boolean reserved){
        JsonData data = new JsonData();
        data.setMsg("分配集群：" + cluster +
                "\n分配策略：" + strategy +
                "\n已完成分配的服务是否重新分配：" + reserved);
        return data;
    }

    /**
     * 更新服务信息
     * @param serviceID
     * @param name
     * @param attributes
     * @param content
     * @param node
     * @return
     */
    @RequestMapping("/api/service/modify")
    public JsonData modifyService(Integer serviceID,String name,String attributes,String content,Integer node){
        JsonData data = new JsonData();
        String message = "";
        if(clusterManagement.nodeStart(node)){
            message.concat("节点已经启动，无法添加服务");
        }else {
            attributes = attributes.replaceAll(" ","");
            name = name.replaceAll(" ","");
            content = content.replaceAll(" ","");

            try{
                serviceService.updateName(name,serviceID);
                message.concat("\n更新服务名称:" + name);
            }catch (Exception e){
                message.concat("\n更新服务名称失败！");
            }
            Map<String,String> a = new HashMap<>();

            if (attributes != null && attributes.length() != 0){
                try{
                    String[] attrrs = attributes.replaceAll(" ","").split(",");
                    for (String attr : attrrs){
                        a.put(attr.split(":")[0],attr.split(":")[1]);
                    }
                    serviceService.updateAttributes(a,serviceID);

                    ObjectMapper mapper = new ObjectMapper();

                    message.concat("\n更新服务属性：" + mapper.writeValueAsString(a));
                }catch (Exception e){
                    message.concat("\n更新服务属性失败");
                }
            }


            if(content != null && content.length() != 0){
                serviceService.updateContent(content,serviceID);
                message.concat("\n更新服务内容：" + content);
            }

            if(node != null){
                try {
                    serviceService.updateNode(node,serviceID);
                    message.concat("\n更新服务节点" + node);
                }catch (Exception e){
                    message.concat("\n更新服务节点失败！");
                }
            }

        }


        data.setMsg(message);

        return data;
    }


    @RequestMapping("/api/service/service_info")
    public JsonData getServiceInfo(Integer serviceID){
        JsonData data = new JsonData();
        data.setData(serviceService.getServiceInfo(serviceID));
        return data;
    }

}
