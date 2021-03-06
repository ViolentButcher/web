package edu.njupt.feng.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 集群相关后端接口
 */
@RestController
public class ClusterController {

    /**
     * 集群相关服务类
     */
    @Autowired
    private ClusterService clusterService;

    /**
     * 集群管理类
     */
    @Autowired
    private ClusterManagement clusterManagement;

    /**
     * 获取集群列表
     * @param pageNum   页码
     * @param filter    筛选条件
     * @param orderBy   排序条件
     * @param desc  升降序
     * @return  集群列表的分页信息
     */
    @RequestMapping("/api/cluster/cluster_list")
    public JsonData getClusterList(@RequestParam(defaultValue = "1")Integer pageNum, String filter,@RequestParam(defaultValue = "id")String orderBy,@RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(clusterService.getClusterInfoList(pageNum,filter,orderBy,desc));
        return data;
    }

    /**
     * 导出集群列表信息
     *    TODO
     * @return
     */
    @RequestMapping("/api/cluster/export")
    public JsonData exportClusterList(){
        JsonData data = new JsonData();

        return data;
    }

    /**
     * 添加集群
     *      首先判断添加属性的格式是否正确；
     *      然后向cluster添加集群信息
     *      返回提示信息
     * @param name  集群名称
     * @param attributes    集群属性
     * @return  提示信息
     */
    @RequestMapping("/api/cluster/add")
    public JsonData addCluster(String name,String attributes){
        JsonData data = new JsonData();

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
        if(clusterService.addCluster(name,attributes)){
            data.setMsg("添加集群成功");
        }else {
            data.setMsg("添加集群失败");
        }
        return data;
    }

    /**
     * 修改集群信息
     *      如果集群启动不允许修改集群信息
     * @param clusterID     集群id，必填
     * @param name      集群名称
     * @param attributes    集群属性
     * @return  提示信息
     */
    @RequestMapping("/api/cluster/modify")
    public JsonData modifyCluster(Integer clusterID,String name,String attributes){
        JsonData data = new JsonData();
        String message = "";

        if(clusterManagement.isStart(clusterID)){
            message = "对不起，集群已经启动，无法修改集群信息！";
        }else {
            if(name != null && name.length() != 0){
                clusterService.updateName(name,clusterID);
                message = "修改集群" + clusterID + "名称为" + name + "\n";
            }
            if (attributes != null && attributes.length() != 0){
                Map<String,String> attr = new HashMap<>();
                String[] attributeWords = attributes.replaceAll(" ","").split(",");
                try{
                    for(String attributeWord : attributeWords){
                        attr.put(attributeWord.split(":")[0],attributeWord.split(":")[1]);
                    }
                }catch (Exception e){
                    message.concat("对不起，输入属性格式有误");
                }

                clusterService.updateAttributes(attr,clusterID);
                message.concat("修改集群" + clusterID + "属性为" + attributes + "\n");
            }
        }
        data.setMsg(message);
        return data;
    }

    /**
     * 删除集群
     *      集群启动的判断在clusterService.deleteCluster()中
     * @param clusterID     集群id
     * @return      提示信息
     */
    @RequestMapping("/api/cluster/delete")
    public JsonData deleteCluster(Integer clusterID){
        JsonData data = new JsonData();
        data.setMsg(clusterService.deleteCluster(clusterID));
        return data;
    }

    /**
     * 获取集群列表
     * @return  集群列表信息
     */
    @RequestMapping("/api/cluster/distribution/cluster_list")
    public JsonData getClusterListForDistribution(){
        JsonData data = new JsonData();
        data.setData(clusterService.getClusterListWithoutPageInfo());
        return data;
    }

    /**
     * 获取集群信息
     * @param clusterID     集群id
     * @return
     */
    @RequestMapping("/api/cluster/cluster_info")
    public JsonData getClusterInfoByClusterID(Integer clusterID){
        JsonData data = new JsonData();
        data.setData(clusterService.getClusterServiceInfo(clusterID));
        return data;
    }

    /**
     * 启动或者卸载集群
     * @param clusterID
     * @return
     */
    @RequestMapping("/api/cluster/load")
    public JsonData loadData(Integer clusterID){
        JsonData data = new JsonData();
        data.setMsg(clusterService.loadCluster(clusterID));
        return data;
    }

}
