package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.provider.ClusterProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * cluster表操作相关集合
 */
@Mapper
public interface ClusterMapper {

    /**
     * 获取数据库中所有集群信息
     * @return
     */
    @Select("SELECT * FROM cluster")
    @Results({
            @Result(column = "create_time" , property = "createTime"),
            @Result(column = "modify_time" , property = "modifyTime"),
            @Result(column = "node_number" , property = "nodeNumber")
    })
    public List<ClusterInfo> getClusterList();

    /**
     * 获取数据库中所有集群信息
     * @return
     */
    @SelectProvider(type = ClusterProvider.class , method = "getClusterList")
    @Results({
            @Result(column = "create_time" , property = "createTime"),
            @Result(column = "modify_time" , property = "modifyTime"),
            @Result(column = "node_number" , property = "nodeNumber")
    })
    public List<ClusterInfo> getClusterListWithParams(String filter,String order,String desc);

    /**
     * 获取数据库中所有集群信息
     * @return
     */
    @Select("SELECT * FROM cluster WHERE id = #{clusterID}")
    @Results({
            @Result(column = "create_time" , property = "createTime"),
            @Result(column = "modify_time" , property = "modifyTime"),
            @Result(column = "node_number" , property = "nodeNumber")
    })
    public ClusterInfo getClusterByID(Integer clusterID);

    /**
     * 获取数据库中同名的集群数量
     * @param name
     * @return
     */
    @Select("SELECT COUNT(*) FROM cluster WHERE name = #{name}")
    public Integer countClustersByName(String name);

    /**
     * 向cluster插入节点信息
     * @param clusterInfo
     */
    @Insert("INSERT INTO cluster(name,attribute,create_time,modify_time,state) VALUES (#{name},#{attribute},#{createTime},#{modifyTime},#{state})")
    public void addCluster(ClusterInfo clusterInfo);

    /**
     * 删除集群
     * @param clusterID
     */
    @Delete("DELETE FROM cluster WHERE id = #{clusterID}")
    public void deleteCluster(int clusterID);


    /**
     * 更新集群名称
     * @param name
     * @param clusterID
     */
    @Update("UPDATE cluster SET name = #{name} WHERE id = #{clusterID}")
    public void updateName(@Param("name")String name,@Param("clusterID")Integer clusterID);

    /**
     * 更新集群节点数量
     * @param nodeNumber
     * @param clusterID
     */
    @Update("UPDATE cluster SET node_number = #{nodeNumber} WHERE id = #{clusterID}")
    public void updateNodeNumber(@Param("nodeNumber")Integer nodeNumber,@Param("clusterID")Integer clusterID);


    /**
     * 更新集群状态
     * @param state
     * @param clusterID
     */
    @Update("UPDATE cluster SET state = #{state} WHERE id = #{clusterID}")
    public void updateState(@Param("state")Integer state,@Param("clusterID")Integer clusterID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param clusterID
     */
    @Update("UPDATE cluster SET modify_time = #{modifyTime} WHERE id = #{clusterID}")
    public void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("clusterID")Integer clusterID);

    /**
     * 更新创建时间
     * @param createTime
     * @param clusterID
     */
    @Update("UPDATE cluster SET create_time = #{createTime} WHERE id = #{clusterID}")
    public void updateCreateTime(@Param("createTime")Date createTime,@Param("clusterID")Integer clusterID);

    /**
     * 更新集群名称
     * @param attributes
     * @param clusterID
     */
    @Update("UPDATE cluster SET attribute = #{attributes} WHERE id = #{clusterID}")
    public void updateAttributes(@Param("attributes")String attributes,@Param("clusterID")Integer clusterID);

    /**
     * 更新集群属性
     * @param configuration
     * @param clusterID
     */
    @Update("UPDATE cluster SET configuration = #{configuration} WHERE id = #{clusterID}")
    public void updateConfiguration(@Param("configuration")String configuration,@Param("clusterID")Integer clusterID);


}
