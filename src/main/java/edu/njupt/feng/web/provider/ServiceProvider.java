package edu.njupt.feng.web.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * 服务provider
 */
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

    /**
     * 获取所有服务列表
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public String getAllServiceList(String filter,String order,String desc){
        if(filter == null || filter.length() == 0){
            return new SQL(){{
                SELECT("*");
                FROM("service");
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }else {
            return new SQL(){{
                SELECT("*");
                FROM("service");
                WHERE(filter);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }
    }
}
