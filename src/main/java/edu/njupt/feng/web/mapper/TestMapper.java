package edu.njupt.feng.web.mapper;

import edu.njupt.feng.web.entity.common.Article;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("SELECT * FROM dataset_article")
    @Results({
            @Result(column = "publish_time",property = "publishTime")
    })
    public List<Article> getArticles();


    @Insert("INSERT INTO service(name,attributes,content,node,cluster,create_time,modify_time) VALUES(#{name},#{attributes},#{content},#{node},#{cluster},#{createTime},#{modifyTime})")
    public void addService(ServiceInfo serviceInfo);

    @Insert("INSERT INTO node(id,name,attributes,service_number,position,cluster,create_time,modify_time) VALUES (#{id},#{name},#{attributes},#{serviceNumber},#{position},#{cluster},#{createTime},#{modifyTime})")
    public void addNode(NodeInfo nodeInfo);
}
