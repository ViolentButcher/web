package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.provider.ServiceProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * service表相关操作
 */
@Mapper
public interface ServiceMapper {
    /**
     * 获取所有的service信息
     * @return
     */
    @Select("SELECT * from service")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<ServiceInfo> getServiceInfoList();

    @Select("SELECT id FROM service WHERE node = #{nodeID}")
    public List<Integer> getServiceIDsByNodeID(Integer nodeID);


    /**
     * 获取某节点的service列表
     * @param nodeID
     * @return
     */
    @Select("SELECT * FROM service WHERE node = #{nodeID}")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<ServiceInfo> getServicesInfoByNodeCluster(@Param("nodeID") Integer nodeID);

    @Select("SELECT * FROM service WHERE cluster = #{clusterID}")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<ServiceInfo> getServicesInfoByCluster(@Param("clusterID") Integer clusterID);

    /**
     * 根据服务id获取服务信息
     * @param serviceID
     * @return
     */
    @Select("SELECT * from service WHERE id = #{serviceID}")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public ServiceInfo getServiceInfo(Integer serviceID);

    /**
     * 更新服务属性
     * @param attributes
     * @param serviceID
     */
    @Update("UPDATE service SET attributes = #{attributes} WHERE id = #{serviceID}")
    public void updateServiceAttr(@Param("attributes")String attributes,@Param("serviceID")Integer serviceID);

    /**
     * 更新名称
     * @param name
     * @param serviceID
     */
    @Update("UPDATE service SET name = #{name} WHERE id = #{serviceID}")
    public void updateName(@Param("name")String name,@Param("serviceID")Integer serviceID);

    /**
     * 更新节点
     * @param node
     * @param serviceID
     */
    @Update("UPDATE service SET node = #{node} WHERE id = #{serviceID}")
    public void updateNode(@Param("node")Integer node,@Param("serviceID")Integer serviceID);

    /**
     * 更新集群
     * @param cluster
     * @param serviceID
     */
    @Update("UPDATE service SET cluster = #{cluster} WHERE id = #{serviceID}")
    public void updateCluster(@Param("cluster")Integer cluster,@Param("serviceID")Integer serviceID);

    /**
     * 更新服务内容
     * @param content
     * @param serviceID
     */
    @Update("UPDATE service SET attributes = #{content} WHERE id = #{serviceID}")
    public void updateContent(@Param("content")String content,@Param("serviceID")Integer serviceID);

    /**
     * 更新修改时间
     * @param modifyTime
     * @param serviceID
     */
    @Update("UPDATE service SET modify_time = #{modifyTime} WHERE id = #{serviceID}")
    public void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("serviceID")Integer serviceID);

    /**
     * 更新创建时间
     * @param createTime
     * @param serviceID
     */
    @Update("UPDATE service SET create_time = #{createTime} WHERE id = #{serviceID}")
    public void updateCreateTime(@Param("createTime")Date createTime,@Param("serviceID")Integer serviceID);

    /**
     * 获取服务列表
     * @param nodeID
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @SelectProvider(type = ServiceProvider.class,method = "getServiceList")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    List<ServiceInfo> getServiceListWithParams(Integer nodeID,String filter,String order,String desc);

    /**
     * 统计service的数量，根据name
     * @param serviceName
     * @return
     */
    @Select("SELECT count(*) FROM service WHERE name = #{serviceName}")
    public Integer countServiceByName(String serviceName);

    /**
     * 添加Service
     * @param serviceInfo
     */
    @Insert("INSERT INTO service(name,attributes,content,node,cluster,create_time,modify_time) VALUES(#{name},#{attributes},#{content},#{node},#{cluster},#{createTime},#{modifyTime})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public void addService(ServiceInfo serviceInfo);

    /**
     * 删除集群的服务
     * @param clusterID
     */
    @Delete("DELETE FROM service WHERE cluster = #{clusterID}")
    public void deleteServiceByCluster(Integer clusterID);

    /**
     * 获取服务列表
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    @SelectProvider(type = ServiceProvider.class,method = "getAllServiceList")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    List<ServiceInfo> getAllServiceListWithParams(String filter,String order,String desc);

    /**
     * 根据服务id删除服务
     * @param serviceID
     */
    @Delete("DELETE FROM service WHERE id = #{serviceID}")
    public void deleteServiceByServiceID(Integer serviceID);

    /**
     * 统计节点的服务数量
     * @param nodeID
     * @return
     */
    @Select("SELECT COUNT(*) FROM service WHERE node = #{nodeID}")
    public Integer countNodeServiceNumber(Integer nodeID);
}
