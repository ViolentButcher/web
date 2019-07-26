package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.provider.ClusterProvider;
import edu.njupt.feng.web.provider.NodeProvider;
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

    /**
     * 获取节点所在集群
     * @param nodeID
     * @return
     */
    @Select("SELECT cluster FROM node WHERE id = #{nodeID}")
    public Integer getClusterIDByNodeID(Integer nodeID);

    /**
     * 更新关联节点列表
     * @param associatedNodes
     * @param nodeID
     */
    @Update("UPDATE node SET assoicated_nodes = #{associatedNodes} WHERE id = #{nodeID}")
    public void updateAssoicatedNode(@Param("assoicatedNodes")String associatedNodes,@Param("nodeID")Integer nodeID);

    /**
     * 获取节点列表
     * @return
     */
    @SelectProvider(type = NodeProvider.class,method = "getNodeList")
    @Results({
            @Result(column = "service_number",property = "serviceNumber"),
            @Result(column = "associated_nodes",property = "associatedNodes"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<NodeInfo> getNodeListWithParams(Integer clusterID,String filter,String order,String desc);

    /**
     * 统计node中name的数量
     * @param name
     * @return
     */
    @Select("SELECT count(*) FROM node WHERE name = #{name}")
    public Integer countNodesByName(String name);


    /**
     * 添加节点
     * @param nodeInfo
     */
    @Insert("INSERT INTO node(name,attributes,service_number,position,cluster,create_time,modify_time) VALUES (#{name},#{attributes},#{serviceNumber},#{position},#{cluster},#{createTime},#{modifyTime})")
    public void addNode(NodeInfo nodeInfo);

    /**
     * 删除节点
     * @param nodeID
     */
    @Delete("DELETE FROM node WHERE id = #{nodeID}")
    public void deleteNode(Integer nodeID);

    /**
     * 删除集群所属节点
     * @param clusterID
     */
    @Delete("DELETE FROM cluster WHERE cluster = #{clusterID}")
    public void deleteNodesByCluster(Integer clusterID);

    /**
     * 获取所有节点的列表
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @SelectProvider(type = NodeProvider.class,method = "getAllNodeList")
    @Results({
            @Result(column = "service_number",property = "serviceNumber"),
            @Result(column = "associated_nodes",property = "associatedNodes"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<NodeInfo> getAllNodeList(String filter,String order,String desc);
}

