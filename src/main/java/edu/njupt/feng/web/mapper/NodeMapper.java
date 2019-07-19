package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.NodeInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NodeMapper {

    /**
     * 根据节点ID获取节点信息
     * @param nodeID
     * @return
     */
    @Select("SELECT * FROM node WHERE id = #{nodeID}")
    @Results({
            @Result(column = "service_number",property = "serviceNumber"),
            @Result(column = "associated_nodes",property = "associatedNodes"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public NodeInfo getNodeInfoByNodeID(Integer nodeID);

    /**
     * 获取节点id对应的node
     * @param clusterID
     * @return
     */
    @Select("SELECT id FROM node WHERE cluster = #{clusterID}")
    public List<Integer> getNodeListsByClusterID(Integer clusterID);

    /**
     * 获取节点id对应的node信息
     * @param clusterID
     * @return
     */
    @Select("SELECT * FROM node WHERE cluster = #{clusterID}")
    @Results({
            @Result(column = "service_number",property = "serviceNumber"),
            @Result(column = "associated_nodes",property = "associatedNodes"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<NodeInfo> getNodeInfosListByClusterID(Integer clusterID);

    /**
     * 更新attributes属性
     * @param attributes
     * @param nodeID
     */
    @Update("UPDATE node SET attributes = #{attributes} WHERE id = #{nodeID}")
    public void updateNodeAttr(@Param("attributes")String attributes,@Param("nodeID") Integer nodeID);

    @Select("SELECT cluster FROM node WHERE id = #{nodeID}")
    public Integer getClusterIDByNodeID(Integer nodeID);

    @Update("UPDATE node SET assoicated_nodes = #{associatedNodes} WHERE id = #{nodeID}")
    public void updateAssoicatedNode(@Param("assoicatedNodes")String associatedNodes,@Param("nodeID")Integer nodeID);
}

