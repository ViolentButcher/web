package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.provider.ServiceProvider;
import org.apache.ibatis.annotations.*;

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
    @Insert("INSERT INTO service(name,attributes,content,node,cluster,create_time,modify_time) VALUES()")
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
}
