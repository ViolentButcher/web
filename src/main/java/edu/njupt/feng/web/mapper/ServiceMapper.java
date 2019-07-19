package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.database.ServiceInfo;
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

    @Select("SELECT * from service WHERE id = #{serviceID}")
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public ServiceInfo getServiceInfo(Integer serviceID);

    @Update("UPDATE service SET attributes = #{attributes} WHERE id = #{serviceID}")
    public void updateServiceAttr(@Param("attributes")String attributes,@Param("serviceID")Integer serviceID);
}
