package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.provider.ClusterProvider;
import org.apache.ibatis.annotations.*;

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
    public List<ClusterInfo> getClusterListWithParams(@Param("filter")String filter,@Param("order")String order,@Param("desc")String desc);

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


}
