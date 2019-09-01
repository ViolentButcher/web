package edu.njupt.feng.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.service.ClusterServiceInfo;
import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.mapper.ClusterMapper;
import edu.njupt.feng.web.mapper.NodeMapper;
import edu.njupt.feng.web.mapper.ServiceMapper;
import edu.njupt.feng.web.service.ClusterService;
import edu.njupt.feng.web.service.NodeService;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ClusterManagement clusterManagement;

    @Autowired
    private NodeService nodeService;

    /**
     * 获取集群列表
     * @param pageNum
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @Override
    public PageInfo getClusterInfoList(Integer pageNum, String filter, String order, String desc) {
        PageHelper.startPage(pageNum,10);
        System.out.println(filter + "," + order + "," + desc);
        List<ClusterInfo> clusterInfos = clusterMapper.getClusterListWithParams(filter, order, desc);
        PageInfo<ClusterInfo> pageInfo = new PageInfo<ClusterInfo>(clusterInfos);
        return pageInfo;
    }

    /**
     * 获取集群服务列表
     * @param clusterID
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo getClusterNodeList(int clusterID, int pageNum) {
        PageHelper.startPage(pageNum,10);
        List<NodeInfo> nodeInfos = nodeMapper.getNodeInfosListByClusterID(clusterID);
        PageInfo<NodeInfo> pageInfo = new PageInfo<>(nodeInfos);
        return pageInfo;
    }

    /**
     * 添加集群
     * @param clusterName
     * @param clusterAttr
     * @return
     */
    @Override
    public boolean addCluster(String clusterName, String clusterAttr) {
        if(clusterMapper.countClustersByName(clusterName) == 0){
            ClusterInfo clusterInfo = new ClusterInfo();
            clusterInfo.setName(clusterName);
            clusterInfo.setAttribute(clusterAttr);
            clusterInfo.setCreateTime(new Date());
            clusterInfo.setModifyTime(new Date());
            clusterInfo.setState(0);
            clusterMapper.addCluster(clusterInfo);
            return true;
        }
        return false;
    }

    /**
     * 删除集群
     * @param clusterID
     * @return
     */
    @Override
    public String deleteCluster(int clusterID) {
        if(clusterMapper.getClusterByID(clusterID) != null){
            if(clusterManagement.isStart(clusterID)){
                return "对不起，集群正在运行";
            }
            serviceMapper.deleteServiceByCluster(clusterID);
            nodeMapper.deleteNodesByCluster(clusterID);
            clusterMapper.deleteCluster(clusterID);
            return "删除集群成功";
        }
        return "对不起，该集群不存在";

    }

    /**
     * 更新集群属性
     * @param attributes
     * @param clusterID
     */
    @Override
    public void updateAttributes(Map<String, String> attributes, Integer clusterID) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            clusterMapper.updateAttributes(mapper.writeValueAsString(attributes),clusterID);
        }catch (Exception e){

        }

    }

    /**
     * 更新节点名称
     * @param name
     * @param clusterID
     */
    @Override
    public void updateName(String name, Integer clusterID) {
        clusterMapper.updateName(name, clusterID);
    }

    /**
     * 更新配置
     * @param configuration
     * @param clusterID
     */
    @Override
    public void updateConfiguration(String configuration, Integer clusterID) {
        clusterMapper.updateConfiguration(configuration, clusterID);
    }

    /**
     * 更新创建时间
     * @param createTime
     * @param clusterID
     */
    @Override
    public void updateCreateTime(Date createTime, Integer clusterID) {
        clusterMapper.updateCreateTime(createTime, clusterID);
    }

    /**
     * 更新修改时间
     * @param modifyTime
     * @param clusterID
     */
    @Override
    public void updateModifyTime(Date modifyTime, Integer clusterID) {
        clusterMapper.updateModifyTime(modifyTime, clusterID);
    }

    /**
     * 更新状态
     * @param state
     * @param clusterID
     */
    @Override
    public void updateState(Integer state, Integer clusterID) {
        clusterMapper.updateState(state, clusterID);
    }

    /**
     * 更新节点数量
     * @param nodeNumber
     * @param clusterID
     */
    @Override
    public void updateNodeNumber(Integer nodeNumber, Integer clusterID) {
        clusterMapper.updateNodeNumber(nodeNumber, clusterID);
    }

    /**
     * 获取集群信息
     * @param clusterID
     * @return
     */
    @Override
    public ClusterServiceInfo getClusterServiceInfo(Integer clusterID) {
        return Convert2ServiceInfo.clusterInfo2ServiceInfo(clusterMapper.getClusterByID(clusterID));
    }

    /**
     * 更新节点数量
     */
    @Override
    public void updateAllNodeNumver() {
        for(ClusterInfo clusterInfo : clusterMapper.getClusterList()){
            clusterMapper.updateNodeNumber(nodeMapper.countClusterNodeNumber(clusterInfo.getId()),clusterInfo.getId());

            nodeService.updateAllNodeServiceNumber(clusterInfo.getId());
        }
    }

    /**
     * 获取集群列表
     * @return
     */
    @Override
    public List<ClusterInfo> getClusterListWithoutPageInfo() {
        return clusterMapper.getClusterList();
    }

    /**
     * 加载集群
     * @param clusterID
     * @return
     */
    @Override
    public String loadCluster(Integer clusterID) {
        ClusterInfo clusterInfo = clusterMapper.getClusterByID(clusterID);
        System.out.println("clusterID" + clusterID + "-------------" + clusterInfo.getState());
        if (clusterInfo.getState() != null){
            if(clusterInfo.getState() == 1){
                clusterManagement.stopCluster(clusterID);
                clusterMapper.updateState(0,clusterID);
                return "集群卸载成功！";
            }
            else {
                clusterManagement.startCluster(clusterID);
                clusterMapper.updateState(1,clusterID);
                return "集群加载成功！";
            }
        }else {
            clusterManagement.startCluster(clusterID);
            clusterMapper.updateState(1,clusterID);
            return "集群加载成功！";
        }
    }
}
