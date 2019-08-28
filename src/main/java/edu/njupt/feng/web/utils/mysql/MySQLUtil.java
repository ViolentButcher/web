package edu.njupt.feng.web.utils.mysql;

import java.sql.*;

public class MySQLUtil {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/web?characterEncoding=utf-8";
    static final String USER = "root";
    static final String PASS = "10m97y";

    /**
     * 更新服务属性
     * @param attributes
     * @param serviceID
     */
    public static void updateServiceAttributes(String attributes,Integer serviceID){
        Connection conn = null;

        String sql = "update service set attributes = '" + attributes
                        + "' where id = " + serviceID;

        commonUpdate(sql);
    }

    /**
     * 更新节点属性
     * @param attributes
     * @param nodeID
     */
    public static void updateNodeAttributes(String attributes,Integer nodeID){
        Connection conn = null;

        String sql = "update node set attributes = '" + attributes
                + "' where id = " + nodeID;

        commonUpdate(sql);
    }

    /**
     * 执行更新
     * @param sql
     */
    public static void commonUpdate(String sql){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(Exception se){
                se.printStackTrace();
            }
        }
    }

}
