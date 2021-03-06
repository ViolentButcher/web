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
        String sql =  new SQL(){{
            SELECT("*");
            FROM("cluster");
            if(filter != null && filter.length() > 0) {
                WHERE(filter);
            }
            ORDER_BY(order);

        }}.toString() + " " + desc;
        System.out.println(sql);
        return sql;
    }


}
