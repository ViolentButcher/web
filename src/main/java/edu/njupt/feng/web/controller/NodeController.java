package edu.njupt.feng.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.AssociatedNodeInfo;
import edu.njupt.feng.web.entity.common.JsonData;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.service.ClusterService;
import edu.njupt.feng.web.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * node相关后端接口
 */
@RestController
public class NodeController {

    //node相关服务
    @Autowired
    private NodeService nodeService;

    @Autowired
    private ClusterService clusterService;
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

        Map<String,String> a = new HashMap<>();
        try{
            String[] attrrs = attributes.replaceAll(" ","").split(",");
            for (String attr : attrrs){
                a.put(attr.split(":")[0],attr.split(":")[1]);
            }
            ObjectMapper mapper = new ObjectMapper();
            attributes = mapper.writeValueAsString(a);
        }catch (Exception e){
            attributes = null;
        }

        Position p = new Position();
        try{
            String[] positionItem = position.replaceAll(" ","").split(",");
            p.setLongitude(Float.valueOf(positionItem[0]));
            p.setLatitude(Float.valueOf(positionItem[1]));
            p.setHeight(Float.valueOf(positionItem[2]));

            ObjectMapper mapper = new ObjectMapper();
            position = mapper.writeValueAsString(p);
        }catch (Exception e){
            position = null;
        }

        if(nodeService.addNode(name,cluster,attributes,position)){
            data.setMsg("插入节点成功！！！\n"
                        +  "name:" + name
                        + "\ncluster:" + cluster
                        + "\nattributes:" + attributes
                        + "\nposition:" + position);
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
     * 修改节点信息
     * @param nodeID
     * @param name
     * @param attributes
     * @param position
     * @param cluster
     * @return
     */
    @RequestMapping("/api/node/modify")
    public JsonData modifyNode(Integer nodeID,String name,String attributes,String position,Integer cluster){
        JsonData data = new JsonData();
        String message = "";

        try{

        }catch (Exception e){

        }
        Map<String,String> a = new HashMap<>();

        if (attributes != null && attributes.length() != 0){
            try{
                String[] attrrs = attributes.replaceAll(" ","").split(",");
                for (String attr : attrrs){
                    a.put(attr.split(":")[0],attr.split(":")[1]);
                }
                nodeService.updateAttributes(a,nodeID);

                ObjectMapper mapper = new ObjectMapper();

                message.concat("\n更新节点属性为：" + mapper.writeValueAsString(a));
            }catch (Exception e){
            }
        }

        Position p = new Position();

        if(position != null && position.length() != 0){
            try{
                String[] positionItem = position.replaceAll(" ","").split(",");
                p.setLongitude(Float.valueOf(positionItem[0]));
                p.setLatitude(Float.valueOf(positionItem[1]));
                p.setHeight(Float.valueOf(positionItem[2]));

                nodeService.updatePosition(p,nodeID);
                ObjectMapper mapper = new ObjectMapper();
                message.concat("\n更新节点位置为：" + mapper.writeValueAsString(p));
            }catch (Exception e){
            }
        }

        if(cluster != null && clusterService.getClusterServiceInfo(cluster) != null){
            nodeService.updateCluster(cluster,nodeID);
            message.concat("\n更新节点集群为：" + cluster);
        }


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

    @RequestMapping("/api/node/node_list/cluster")
    public JsonData getNodeListByClusterID(Integer clusterID){
        JsonData data = new JsonData();
        data.setData(nodeService.getNodeInfos(clusterID));
        return data;
    }

}
