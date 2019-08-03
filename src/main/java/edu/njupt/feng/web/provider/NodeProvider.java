package edu.njupt.feng.web.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * NodeProvider
 */
public class NodeProvider {

    /**
     * 获取节点列表
     * @param clusterID
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public String getNodeList(Integer clusterID,String filter,String order,String desc){
        if(filter == null || filter.length() == 0){
            return new SQL(){{
                SELECT("*");
                FROM("node");
                WHERE("cluster = " + clusterID);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }else {
            return new SQL(){{
                SELECT("*");
                FROM("node");
                WHERE("cluster = " + clusterID);
                WHERE(filter);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }

    }

    /**
     * 获取所有节点的列表
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public String getAllNodeList(String filter,String order,String desc){
        if(filter == null || filter.length() == 0){
            return new SQL(){{
                SELECT("*");
                FROM("node");
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }else {
            return new SQL(){{
                SELECT("*");
                FROM("node");
                WHERE(filter);
                ORDER_BY(order);

            }}.toString() + " " + desc;
        }

    }
}
