package edu.njupt.feng.web.utils.mysql;

import java.sql.*;

public class MySQLUtil {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://47.102.116.221/project?characterEncoding=utf-8";
    static final String USER = "root";
    static final String PASS = "10m97y";

    public static void updateServiceAttributes(String attributes,Integer serviceID){
        Connection conn = null;

        String sql = "update service set attributes = '" + attributes
                        + "' where id = " + serviceID;

        commonUpdate(sql);
    }

    public static void updateNodeAttributes(String attributes,Integer nodeID){
        Connection conn = null;

        String sql = "update node set attributes = '" + attributes
                + "' where id = " + nodeID;

        commonUpdate(sql);
    }

    public static void commonUpdate(String sql){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
            //执行sql语句
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
