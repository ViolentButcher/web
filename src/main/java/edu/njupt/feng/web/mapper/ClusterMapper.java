package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ClusterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT * FROM cluster WHERE id = #{clusterID}")
    @Results({
            @Result(column = "create_time" , property = "createTime"),
            @Result(column = "modify_time" , property = "modifyTime"),
            @Result(column = "node_number" , property = "nodeNumber")
    })
    public ClusterInfo getClusterByID(Integer clusterID);
}
