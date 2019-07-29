package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.provider.ClusterProvider;
import edu.njupt.feng.web.provider.NodeProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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
     * 更新节点名称
     * @param name
     * @param nodeID
     */
    @Update("UPDATE node SET name = #{name} WHERE id = #{nodeID}")
    public void updateName(@Param("name")String name,@Param("nodeID")Integer nodeID);

    /**
     * 更新节点服务数量
     * @param servcieNumber
     * @param nodeID
     */
    @Update("UPDATE node SET service_number = #{servcieNumber} WHERE id = #{nodeID}")
    public void updateServiceNumber(@Param("servcieNumber")Integer servcieNumber,@Param("nodeID")Integer nodeID);

    /**
     * 更新节点位置
     * @param position
     * @param nodeID
     */
    @Update("UPDATE node SET position = #{position} WHERE id = #{nodeID}")
    public void updatePosition(@Param("position")String position,@Param("nodeID")Integer nodeID);

    /**
     * 更新节点集群
     * @param cluster
     * @param nodeID
     */
    @Update("UPDATE node SET cluster = #{cluster} WHERE id = #{nodeID}")
    public void updateCluster(@Param("cluster")Integer cluster,@Param("nodeID")Integer nodeID);

    /**
     * 更新节点level
     * @param level
     * @param nodeID
     */
    @Update("UPDATE node SET level = #{level} WHERE id = #{nodeID}")
    public void updateLevel(@Param("level")Integer level,@Param("nodeID")Integer nodeID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param nodeID
     */
    @Update("UPDATE node SET modify_time = #{modifyTime} WHERE id = #{nodeID}")
    public void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("nodeID")Integer nodeID);

    /**
     * 更新创建时间
     * @param createTime
     * @param nodeID
     */
    @Update("UPDATE node SET create_time = #{createTime} WHERE id = #{nodeID}")
    public void updateCreateTime(@Param("createTime")Date createTime,@Param("nodeID")Integer nodeID);

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

    /**
     *
     * @param clusterID
     * @return
     */
    @Select("SELECT COUNT(*) FROM node WHERE cluster = #{clusterID}")
    public Integer countClusterNodeNumber(Integer clusterID);
}

