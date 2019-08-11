package feng;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
//import com.hankcs.demo.DemoWord2Vec;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pre {

    //数据库的用户名与密码
    static final String JDBC_Driver = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/web?characterEncoding=utf-8";
    //数据库的用户名与密码
    static final String USER = "root";
    static final String PASS = "root";

    public static ArrayList<Map<String, String>> fetchData(Connection con) {//读取数据
        ArrayList<Map<String, String>> L = new ArrayList<Map<String, String>>();
        try {
            Statement stm = con.createStatement();
            String sql = "select * from service";
            ResultSet re = stm.executeQuery(sql);
            int i = 0;
            //doc2vec时用的
            //DocVectorModel docvectormodel = get_DocVectorModel();

            while (re.next()) {
                int node = re.getInt("node");
                String attribute = re.getString("attributes");
                int id = re.getInt("id");
                //System.out.println(id);

                Map<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(id));
                map.put("att", attribute);
                map.put("node", String.valueOf(node));
                L.add(map);
//                String new_att = doc2evcaddtoatt(docvectormodel, attribute,content);
//                upData(con,id,new_att);
//                contentList[i] = content;
//                System.out.println("new att: " + id + "    " + new_att);
            }
            //System.out.println(i);
            re.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("MySQL数据库成功读取数据！" + "\n");
        }
        return L;
    }

    //插入数据
    public static void insertData(Connection con, String str) {
        try {
            String sql = "insert into web.service (attributes) values(?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, str);

            pst.executeUpdate();
            pst.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("MySQL数据库数据插入成功！" + "\n");
        }
    }

    public static void upData(Connection con, Integer id, String str) {//修改数据
        try {
            String sql = "update service set attributes = ? where id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, str);
            pst.setInt(2, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("MySQL数据库成功修改数据！" + "\n");
        }
    }

    //    public static void deleteData(Connection con) {//删除数据
//        try {
//            String sql = "delete from emp where deptno = ?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setInt(1, 3);
//            pst.executeUpdate();
//            pst.close();
//        }catch(SQLException se) {
//            se.printStackTrace();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }finally {
//            System.out.println("MySQL数据库成功删除数据！" + "\n");
//        }
//    }


    /**
     * 加载预料，获得文档向量模型
     *
     * @return
     * @throws IOException
     */
//    public static DocVectorModel get_DocVectorModel() throws IOException {
//        DemoWord2Vec demoWord2Vec = new DemoWord2Vec();
//        //词向量
//        WordVectorModel wordVectorModel = demoWord2Vec.trainOrLoadModel();
//        //文档向量
//        return new DocVectorModel(wordVectorModel);
//    }

    /**
     * 未加处理的文档 ==> 向量
     *
     * @param doc
     * @param docVectorModel
     * @return float[]
     */
    public static float[] doc2vec(DocVectorModel docVectorModel, String doc) {
        return docVectorModel.query(doc).getElementArray();
    }

    /**
     * 计算两个文本的相似度
     *
     * @param docVectorModel
     * @param doc1
     * @param doc2
     * @return
     */
    public static float similarity(DocVectorModel docVectorModel, String doc1, String doc2) {
        return docVectorModel.similarity(doc1, doc2);
    }

    /**
     * float[] 转 String
     *
     * @param fs
     * @return String s
     */
    public static String floatList_2_String(float[] fs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fs.length; i++) {
            stringBuilder.append(fs[i] + " ");
        }
        String s = stringBuilder.toString();

        return s;
    }

    /**
     * String 转 float[]
     *
     * @param string
     * @return float[]
     */
    public static float[] String_2_floatList(String string) {
        String[] strings = string.split(" ");
        float[] fs = new float[strings.length];
        for (int i = 0; i < strings.length; i++) {
            fs[i] = Float.parseFloat(strings[i]);
        }
        return fs;
    }


    /**
     * json2String
     *
     * @param str_json
     * @return
     */
    public static Map<String, String> json2map(String str_json) {
        Map<String, String> res = null;
        try {
            Gson gson = new Gson();
            res = gson.fromJson(str_json, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }

    /**
     * map2json
     *
     * @param map
     * @return
     */
    public static String map2json(Map<String, String> map) {
        Gson gson = new Gson();
        String str = gson.toJson(map);

        return str;
    }

    public static String doc2evcaddtoatt(DocVectorModel docvectormodel, String att, String content) {
        //json格式的attribute转化为 Map实例
        Map<String, String> attribute = json2map(att);
        //获得当前第i个服务的内容的文档向量（String格式）
        String V_str = "";
        if (content != null) {
            Vector V = docvectormodel.query(content);
            float[] fs = V.getElementArray();
            V_str = floatList_2_String(fs);
        }
        //加入到attribute中
        attribute.put("vec", V_str);
        //attribute Map ==> json
        String str_att = map2json(attribute);
        return str_att;
    }

    public static Connection Con() {
        Connection con = null;
        Statement stmt = null;
        ResultSet re = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//注册JDBC驱动
            con = DriverManager.getConnection(DB_URL, USER, PASS);//利用信息链接数据库
            stmt = con.createStatement();
            if (!con.isClosed()) {
                System.out.println("与数据库成功建立连接！");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("执行结束！");
        }
        return con;
    }
}
//1. 查询
//String[] contentList = fetchData(con, "content");

//2. 插入
//insertData(con);

//3. 更新
//upData(con, 1, str_att);

//4. 删除
//deleteData(con);

//计算两个相似度
//            DocVectorModel docvectormodel = get_DocVectorModel();
//            float sim = docvectormodel.similarity(attList[0], attList[1]);

//生成向量
//            Vector V = docvectormodel.query(contentList[0]);
//            String V_str=floatList_2_String(V.getElementArray());


//            for (int i = 0; i <contentList.length ; i++) {
//                if(contentList[i]!=null){
//                    //json格式的attribute转化为 Map实例
//                    Map<String,String> attribute =json2map(attList[i]);
//                    //获得当前第i个服务的内容的文档向量（String格式）
//                    String V_str="";
//                    if(contentList[i]!="") {
//                        Vector V = docvectormodel.query(contentList[i]);
//                        float[] fs = V.getElementArray();
//                        V_str = floatList_2_String(fs);
//                    }
//                    //加入到attribute中
//                    attribute.put("vec",V_str);
//                    //attribute Map ==> json
//                    String str_att=map2json(attribute);
//
//                    //更新
//                    upData(con, 1, str_att);
//                }
//
//            }