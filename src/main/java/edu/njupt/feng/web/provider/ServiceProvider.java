package edu.njupt.feng.web.provider;

import org.apache.ibatis.jdbc.SQL;

public class ServiceProvider {

    /**
     * 获取服务列表
     * @param nodeID
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public String getServiceList(Integer nodeID,String filter,String order,String desc){
        if(filter == null){
            return new SQL(){{
                SELECT("*");
                FROM("service");
                WHERE("node = " + nodeID);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }else {
            return new SQL(){{
                SELECT("*");
                FROM("service");
                WHERE("node = " + nodeID);
                WHERE(filter);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }

    }
}
