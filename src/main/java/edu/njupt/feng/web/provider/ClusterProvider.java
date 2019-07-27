package edu.njupt.feng.web.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * cluster sql provider
 */
public class ClusterProvider {

    /**
     * 获取cluster列表的sql语句构建
     * @param filter
     * @param order
     * @param desc
     * @return
     */
    public String getClusterList(String filter,String order,String desc){
        return new SQL(){{
            SELECT("*");
            FROM("cluster");
            if(filter != null) {
                WHERE(filter);
            }
            ORDER_BY(order);

        }}.toString() + " " + desc;
    }


}
