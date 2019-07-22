package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 集群相关后端接口
 */
@RestController
public class ClusterController {

    //集群相关服务类
    @Autowired
    private ClusterService clusterService;

    /**
     * 获取集群列表
     * @param pageNum
     * @return
     */
    @RequestMapping("/api/cluster/cluster_list")
    public JsonData getClusterList(@RequestParam(defaultValue = "1")Integer pageNum, String filter,@RequestParam(defaultValue = "id")String order,@RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(clusterService.getClusterInfoList(pageNum,filter,order,desc));
        return data;
    }

    /**
     * 导出集群列表信息
     * @return
     */
    @RequestMapping("/api/cluster/export")
    public JsonData exportClusterList(){
        JsonData data = new JsonData();

        return data;
    }

    /**
     * 添加集群
     * @return
     */
    @RequestMapping("/api/cluster/add")
    public JsonData addCluster(String name,String attributes){
        JsonData data = new JsonData();
        if(clusterService.addCluster(name,attributes)){
            data.setMsg("添加集群成功！！！");
        }else {
            data.setMsg("对不起，该名称已被占用！！！");
        }
        return data;
    }

    /**
     * 修改集群信息
     * @return
     */
    @RequestMapping("/api/cluster/modify")
    public JsonData modifyCluster(){
        JsonData data = new JsonData();

        return data;
    }

    /**
     * 删除集群
     * @return
     */
    @RequestMapping("/api/cluster/delete")
    public JsonData deleteCluster(){
        JsonData data = new JsonData();

        return data;
    }

}
