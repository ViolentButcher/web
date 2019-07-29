package edu.njupt.feng.web.controller;

import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * node相关后端接口
 */
@RestController
public class NodeController {

    //node相关服务
    @Autowired
    private NodeService nodeService;

    /**
     * 获取节点列表
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @RequestMapping("/api/node/node_list")
    public JsonData getNodeList(Integer clusterID,@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String order, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(nodeService.getPageNodeListWithParams(clusterID, pageNum, filter, order, desc));
        return data;
    }

    /**
     * 手动配置节点信息
     * @return
     */
    @RequestMapping("/api/node/configure/manual")
    public JsonData configureNodeWithManual(Integer nodeID,String associatedNode,Integer level,String associatedType){
        JsonData data = new JsonData();

        boolean associatedNodeFlag = true;
        String message = null;

        if(associatedNode != null){
            String[] associatedNodeListStr = associatedNode.split(",");
            List<AssociatedNodeInfo> toUpdateAssocaitedNodeList = new ArrayList<>();

            for(String node : associatedNodeListStr){
                if(nodeService.getNodeServiceInfo(Integer.valueOf(node)) != null){
                    AssociatedNodeInfo associatedNodeInfo = new AssociatedNodeInfo();
                    associatedNodeInfo.setId(Integer.valueOf(node));
                    associatedNodeInfo.setAssociatedType(associatedType);
                    toUpdateAssocaitedNodeList.add(associatedNodeInfo);
                }else {
                    associatedNodeFlag = false;
                    break;
                }
            }

            if(associatedNodeFlag){
                nodeService.updateAssoicatedNodes(nodeID,toUpdateAssocaitedNodeList);
                message = "更新节点" + nodeID + "关联节点：" + associatedNode + "，类型：" + associatedType;
            }
        }

        if(level != null){
            nodeService.updateLevel(level,nodeID);
            message.concat("\n更新节点" + nodeID + " level：" + level);
        }
        data.setMsg(message);
        return data;
    }

    /**
     * 自动配置节点信息
     * @return
     */
    @RequestMapping("/api/node/configure/auto")
    public JsonData configureNodeWithAuto(){
        JsonData data = new JsonData();

        return data;
    }

    /**
     * 添加集群
     * @param name
     * @param attributes
     * @param position
     * @param cluster
     * @return
     */
    @RequestMapping("/api/node/add")
    public JsonData addNode(String name,String attributes,String position,Integer cluster){
        JsonData data = new JsonData();
        if(nodeService.addNode(name,cluster,attributes,position)){
            data.setMsg("插入节点成功！！！");
        }else {
            data.setMsg("插入节点失败！！！");
        }
        return data;
    }

    /**
     * 删除节点
     * @param nodeID
     * @return
     */
    @RequestMapping("/api/node/delete")
    public JsonData deleteNode(Integer nodeID){
        JsonData data = new JsonData();

        return data;
    }

    /**
     * 测试用节点配置
     * @param clusterID
     * @return
     */
    @RequestMapping("/api/test/node/configure")
    public JsonData configureNode(Integer clusterID){

        //配置节点
        nodeService.configureNodes(clusterID);

        //返回节点列表
        JsonData data = new JsonData();
        data.setData(nodeService.getNodeInfos(clusterID));
        return data;
    }

    @RequestMapping("/api/node/node_list_all")
    public JsonData getAllNodeList(@RequestParam(defaultValue = "1")Integer pageNum, String filter, @RequestParam(defaultValue = "id")String order, @RequestParam(defaultValue = "asc")String desc){
        JsonData data = new JsonData();
        data.setData(nodeService.getAllNodeList(pageNum, filter, order, desc));
        return data;
    }


}
