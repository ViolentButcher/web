package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.common.Article;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("SELECT * FROM dataset_article")
    @Results({
            @Result(column = "publish_time",property = "publishTime")
    })
    public List<Article> getArticles();

    @Select("SELECT * FROM dataset_article WHERE id = #{id}")
    @Results({
            @Result(column = "publish_time",property = "publishTime")
    })
    public Article getArticleByID(int id);

    @Select("SELECT * FROM node")
    @Results({
            @Result(column = "service_number",property = "serviceNumber"),
            @Result(column = "associated_nodes",property = "associatedNodes"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "modify_time",property = "modifyTime"),
    })
    public List<NodeInfo> getNodes();


    @Insert("INSERT INTO service(id,name,attributes,content,node,cluster,create_time,modify_time) VALUES(#{id},#{name},#{attributes},#{content},#{node},#{cluster},#{createTime},#{modifyTime})")
    public void addService(ServiceInfo serviceInfo);

    @Insert("INSERT INTO node(id,name,attributes,service_number,position,cluster,create_time,modify_time) VALUES (#{id},#{name},#{attributes},#{serviceNumber},#{position},#{cluster},#{createTime},#{modifyTime})")
    public void addNode(NodeInfo nodeInfo);

    /**
     * 向cluster插入节点信息
     * @param clusterInfo
     */
    @Insert("INSERT INTO cluster(id,name,attribute,create_time,modify_time,state) VALUES (#{id},#{name},#{attribute},#{createTime},#{modifyTime},#{state})")
    public void addCluster(ClusterInfo clusterInfo);

    @Select("SELECT * FROM service WHERE node >= 1200")
    public List<ServiceInfo> updateNodeGreater1200();
}
