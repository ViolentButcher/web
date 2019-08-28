package edu.njupt.feng.web.webservice.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.common.NodeMapItem;
import edu.njupt.feng.web.entity.common.Position;
import edu.njupt.feng.web.entity.common.ResultInfoWithoutContent;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceInfo;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;
import edu.njupt.feng.web.entity.service.ServiceServiceInfo;
import edu.njupt.feng.web.management.NodeMap;
import edu.njupt.feng.web.management.ServiceMap;
import edu.njupt.feng.web.utils.constants.Constants;
import edu.njupt.feng.web.utils.convert.Convert2ServiceInfo;
import edu.njupt.feng.web.utils.model.MatrixFactorization;
import edu.njupt.feng.web.utils.mysql.MySQLUtil;
import edu.njupt.feng.web.webservice.NodeWebService;
import feng.pre;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;

import java.io.IOException;
import java.util.*;

import java.io.*;
import java.util.*;
import feng.util;
import feng.Temp;


public class NodeWebServiceImpl implements NodeWebService {

    private NodeServiceInfo nodeServiceInfo;

    private Map<Integer, NodeServiceListItem> serviceInfoList = new HashMap<>();

    /**
     * 更新节点名称
     *
     * @param name
     */
    @Override
    public void updateName(String name) {
        nodeServiceInfo.setName(name);
    }

    /**
     * 更新所属服务的名称
     *
     * @param name
     * @param serviceID
     */
    @Override
    public void updateServiceName(String name, int serviceID) {
        serviceInfoList.get(serviceID).setName(name);
    }

    /**
     * 更新自身修改时间
     *
     * @param modifyTime
     */
    @Override
    public void updateModifyTime(Date modifyTime) {
        nodeServiceInfo.setModifyTime(modifyTime);
    }

    /**
     * 更新节点位置
     *
     * @param position
     */
    @Override
    public void updatePosition(Position position) {
        nodeServiceInfo.setPosition(position);
    }

    /**
     * 添加服务
     *
     * @param serviceInfo
     */
    @Override
    public void addService(ServiceInfo serviceInfo) {
        serviceInfoList.put(serviceInfo.getId(), Convert2ServiceInfo.serviceInfo2NodeServiceListItem(serviceInfo));
    }

    /**
     * 设置节点信息
     *
     * @param nodeInfo
     */
    @Override
    public void setNodeInfo(NodeServiceInfo nodeInfo) {
        nodeServiceInfo = nodeInfo;
    }

    /**
     * 获取节点信息
     *
     * @return
     */
    @Override
    public NodeServiceInfo getNodeInfo() {
        return nodeServiceInfo;
    }

    /**
     * 获取节点上面的服务列表
     *
     * @return
     */
    @Override
    public List<NodeServiceListItem> getNodeServiceList() {
        return new ArrayList<>(serviceInfoList.values());
    }

    /**
     * 设置节点上的服务列表
     *
     * @param serviceList
     */
    @Override
    public void setNodeServiceList(List<NodeServiceListItem> serviceList) {
        for (NodeServiceListItem serviceInfo : serviceList) {
            serviceInfoList.put(serviceInfo.getId(), serviceInfo);
        }
    }


    /**
     * 更新节点属性信息（自身）
     *
     * @param attributes
     */
    @Override
    public void updateNodeAttributes(Map<String, String> attributes) {
        nodeServiceInfo.setAttributes(attributes);

        //全局NodeMap的属性更新
        NodeMap.updateNodeAttributes(nodeServiceInfo.getServiceAddress(), attributes);

        ObjectMapper mapper = new ObjectMapper();
        try {
            MySQLUtil.updateNodeAttributes(mapper.writeValueAsString(attributes), nodeServiceInfo.getId());
        } catch (Exception e) {

        }
    }

    /**
     * 更新服务属性
     *
     * @param attributes
     * @param serviceID
     */
    @Override
    public void updateServiceAttributes(Map<String, String> attributes, Integer serviceID) {
        serviceInfoList.get(serviceID).setAttributes(attributes);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(attributes);
            MySQLUtil.updateServiceAttributes(json.replaceAll("\\\\", "\\\\\\\\"), serviceID);
        } catch (Exception e) {

        }
        ServiceMap.updateServiceAttributes(Constants.SERVICE_PREFIX + serviceID, attributes);
    }


    /**
     * 更新其它节点的属性
     *
     * @param attributes
     * @param nodeID
     */
    @Override
    public void updateOtherNodeAttributes(Map<String, String> attributes, Integer nodeID) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress(Constants.NODE_PREFIX + nodeID);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateNodeAttributes(attributes);
    }

    /**
     * 更新其它节点所属的服务的属性
     *
     * @param attributes
     * @param serviceID
     * @param nodeID
     */
    @Override
    public void updateOtherServiceAttributes(Map<String, String> attributes, Integer serviceID, Integer nodeID) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress(Constants.NODE_PREFIX + nodeID);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        service.updateServiceAttributes(attributes, serviceID);
    }

    /**
     * 访问其它节点，获取其服务列表信息
     *
     * @param address
     * @return
     */
    @Override
    public List<NodeServiceListItem> getServiceList(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeServiceList();
    }

    /**
     * 获取节点信息
     *
     * @param address
     * @return
     */
    @Override
    public NodeServiceInfo getNodeServiceInfo(String address) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(NodeWebService.class);
        NodeWebService service = factoryBean.create(NodeWebService.class);
        return service.getNodeInfo();
    }

    /**
     * 搜索测试
     *
     * @param keyword
     * @return
     */
    @Override
    public ResultInfoWithoutContent testSearch(String keyword, Integer type) {
        ResultInfoWithoutContent results = new ResultInfoWithoutContent();
//        if(type == 1){
        //加载word2vec.txt,并获得文档向量模型。
        DocVectorModel docVectorModel = null;
        try {
            docVectorModel = util.get_DocVectorModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据文档向量模型，把查询String keyword 转换为Vector
        Vector search_vec = util.doc2Vector(docVectorModel, keyword);
        if (type == 1) {//集中式
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest01ByMap(search_vec);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        } else if (type == 2) {//树状  （先寻找根节点/队长 ，然后层次遍历）
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest02ByMap(search_vec);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        } else {//树状、分布式  （广度优先算法）
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest03ByMap(search_vec);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }

    /**
     * 推荐测试
     *
     * @param keyword
     * @return
     */
    @Override
    public ResultInfoWithoutContent testRecommend(String keyword,Integer type) {
        ResultInfoWithoutContent results = new ResultInfoWithoutContent();
        if(type == 1){
            if(!new File("node_list.txt").exists()
                    || !new File("service_list.txt").exists()
                    || !new File("service_owning_node_list.txt").exists()
                    || !new File("estimate_score_matrix.txt").exists())
                prepareScoreInCentralization();
            List<String> nodeList = loadList("node_list.txt");
            List<String> serviceList = loadList("service_list.txt");
            List<String> serviceOwningNodeList = loadList("service_owning_node_list.txt");
            Double[][] estimateScoreMatrix = loadMatrix("estimate_score_matrix.txt", nodeList.size(), serviceList.size());
            long startTime = System.currentTimeMillis();
            results = executeRecommend(keyword, nodeList, serviceList, serviceOwningNodeList, estimateScoreMatrix);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }else if (type == 2){
            long startTime = System.currentTimeMillis();
            //results =  recommendMethodTest02ByMap(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }


    /**
     * 初始化之根据服务的vector，以洪范的方式为每个服务创建语义邻接表
     *
     * @param serviceList
     * @param limit
     * @param the
     * @param node_id
     */
    public void createNextlist(Map<Integer, NodeServiceListItem> serviceList, float limit, boolean the, int node_id) {
        if (serviceList != null && serviceList.values() != null) {

            //初始化attributes中的————temp_Nextlist:[{},{},~~~]
            for (NodeServiceListItem service_item : serviceList.values()) {
//                System.out.println(service_item.getId());
//                System.out.println(service_item.getAttributes().get("temp_Nextlist"));
                service_item.getAttributes().put("temp_Nextlist", "");

                if (the)
                    updateServiceAttributes(service_item.getAttributes(), service_item.getId());
                else
                    updateOtherServiceAttributes(service_item.getAttributes(), service_item.getId(), node_id);
            }

            //System.out.println("初始化attributes中的————temp_Nextlist:[{},{},~~~]");
            //用于存储所有服务的邻接表 service：service邻接表
            //service邻接表格式：ArrayList<Map<String,String>>
            //Map<String,String>———— "id":id,"Sim":sim
            Map<NodeServiceListItem, ArrayList<Map<String, String>>> L = new HashMap<>();
            for (NodeServiceListItem service_item : serviceList.values()) {
                ArrayList<Map<String, String>> item_Nextlist = new ArrayList<>();
                item_Nextlist.clear();
                L.put(service_item, item_Nextlist);
            }

            //计算Sim
            for (NodeServiceListItem service_item : serviceList.values()) {
                //System.out.println("计算" + service_item.getId() + "的Sim，寻找邻接顶点");
                int id = service_item.getId();
                float[] vec = util.String_2_floatList(service_item.getAttributes().get("vec"));
                for (NodeServiceListItem service_item_2 : serviceList.values()) {
                    int id_2 = service_item_2.getId();
                    float[] vec_2 = util.String_2_floatList(service_item_2.getAttributes().get("vec"));
                    if (id != id_2) {
                        float Sim = util.dot(vec, vec_2);
                        if (Sim > limit) {
                            //向service_item中添加邻接顶点（服务）
                            Map<String, String> next_service2 = new HashMap<>();
                            next_service2.put("id", Integer.toString(id_2));
                            next_service2.put("Sim", Float.toString(Sim));
                            L.get(service_item).add(next_service2);

                            //向service_item_2中添加邻接顶点（服务）
                            Map<String, String> next_service1 = new HashMap<>();
                            next_service1.put("id", Integer.toString(id));
                            next_service1.put("Sim", Float.toString(Sim));
                            L.get(service_item_2).add(next_service1);
                        }
                    }
                }
            }

            //对L中的所有邻接表排序删选前十个,添加到相应service的attributes中。
            for (NodeServiceListItem service_item : serviceList.values()) {
                //System.out.println("更新" + service_item.getId() + "的attributes");
                ArrayList<Map<String, String>> temp = L.get(service_item);
                temp.sort(new Comparator<Map<String, String>>() {
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        if (Float.valueOf(o1.get("Sim")) < Float.valueOf(o2.get("Sim")))
                            return 1;
                        else if (Float.valueOf(o1.get("Sim")) > Float.valueOf(o2.get("Sim")))
                            return -1;
                        else
                            return 0;
                    }
                });
                if (temp.size() < 10) {
                    System.out.println(temp);
                    System.out.println(service_item.getId());
                }
                List<Map<String, String>> new_list = temp.subList(0, Math.min(10, temp.size()));

                //1. 用temp类来保存邻接节点。
                List<Temp> tempList = new ArrayList<Temp>();
                for (int i = 0; i < new_list.size(); i++) {
                    Temp tempservicenode = new Temp();
                    tempservicenode.setId(i);
                    tempservicenode.setIndex(Integer.parseInt(new_list.get(i).get("id")));
                    tempservicenode.setSim(Double.valueOf(new_list.get(i).get("Sim")));
                    tempList.add(tempservicenode);
                }
                //输出是正确的，但是输入sql时，反斜杠会被吃掉。
                service_item.getAttributes().put("temp_Nextlist", JSON.toJSONString(tempList));
                //System.out.println(JSON.toJSONString(service_item.getAttributes()));

                //2.new_temp.toString()不会报错
                //service_item.getAttributes().put("temp_Nextlist", new_list.toString());
                if (the)
                    updateServiceAttributes(service_item.getAttributes(), service_item.getId());
                else
                    updateOtherServiceAttributes(service_item.getAttributes(), service_item.getId(), node_id);
            }
        }
    }


    public void change(Map<Integer, NodeServiceListItem> serviceList, boolean the, int node_id) {
        for (NodeServiceListItem ith : serviceList.values()) {

            String strvec = ith.getAttributes().get("vec");
            if (!strvec.startsWith("{")) {
                System.out.println("vec不对，不是Vector");
                System.out.println(ith.getId());
            }
            if (!ith.getAttributes().containsKey("temp_Nextlist")) {
                System.out.println("temp_Nextlist不对");
                System.out.println(ith.getId());
            }
//            List<Float> other_vec = JSON.parseArray(ith.getAttributes().get("vec"),Float.class);
//            float[] temp =new float[other_vec.size()];
//            for (int i = 0; i < other_vec.size(); i++) {
//                temp[i]=other_vec.get(i);
//            }


//            float[] temp =util.String_2_floatList(ith.getAttributes().get("vec"));
//            Vector vector=new Vector(temp);
//            ith.getAttributes().put("vec", JSON.toJSONString(vector));
//            if (the)
//                updateServiceAttributes(ith.getAttributes(), ith.getId());
//            else
//                updateOtherServiceAttributes(ith.getAttributes(), ith.getId(), node_id);
        }
    }

    public List<NodeServiceListItem> searchinnode(Map<Integer, NodeServiceListItem> serviceList, Vector search_vec,int numofedge) {
        //当前节点下服务的结果
        List<NodeServiceListItem> result = new ArrayList<>();
        //遍历的方式
//        Queue<NodeServiceListItem> result = new PriorityQueue<NodeServiceListItem>(10, new Comparator<NodeServiceListItem>() {
//            @Override
//            public int compare(NodeServiceListItem o1, NodeServiceListItem o2) {
//                if (Float.valueOf(o1.getAttributes().get("search_sim")) < Float.valueOf(o2.getAttributes().get("search_sim")))
//                    return 1;
//                else if (Float.valueOf(o1.getAttributes().get("search_sim")) > Float.valueOf(o2.getAttributes().get("search_sim")))
//                    return -1;
//                else
//                    return 0;
//            }
//        });
//        for (NodeServiceListItem ith : serviceList.values()) {
//            //JSON.parseArray(ith.getAttributes().get("temp_Nextlist"), Temp.class);
//
//            float sim = search_vec.dot(JSON.parseObject(ith.getAttributes().get("vec"),Vector.class));
//            ith.getAttributes().put("search_sim", Float.toString(sim));
//            result.add(ith);
//        }
//        return result;

        NodeServiceListItem thefirstaddtoqueue = null;
        //初始化visit为0，代表未访问
        for (NodeServiceListItem service_item : serviceList.values()) {
            service_item.getAttributes().put("visit", "0");
            thefirstaddtoqueue = service_item;
        }

        Queue<NodeServiceListItem> Q = new LinkedList<>();
        Q.add(thefirstaddtoqueue);
        thefirstaddtoqueue.getAttributes().put("visit", "1");
        int n = 0;
        while (!Q.isEmpty()) {
            NodeServiceListItem servicetemp = Q.remove();
            List<NodeServiceListItem> sim_temp_list = new ArrayList<>();
            if (servicetemp != null && servicetemp.getAttributes() != null && servicetemp.getAttributes().containsKey("temp_Nextlist") && servicetemp.getAttributes().get("temp_Nextlist") != null) {

                List<Temp> service_list = JSON.parseArray(servicetemp.getAttributes().get("temp_Nextlist"), Temp.class);
                for (int i = 0; i < service_list.size(); i++) {

                    NodeServiceListItem ith = serviceList.get(service_list.get(i).getIndex());
                    if (ith != null && ith.getAttributes() != null && ith.getAttributes().containsKey("visit") && !ith.getAttributes().get("visit").equals("1")) {
                        n++;
                        //System.out.println(ith.getId());
                        Vector other_vec = JSON.parseObject(ith.getAttributes().get("vec"), Vector.class);
                        float sim = search_vec.dot(other_vec);
                        ith.getAttributes().put("search_sim", Float.toString(sim));
                        sim_temp_list.add(ith);
                        ith.getAttributes().put("visit", "1");
                    }

                }
            }
            sim_temp_list.sort(new Comparator<NodeServiceListItem>() {
                @Override
                public int compare(NodeServiceListItem o1, NodeServiceListItem o2) {
                    if (Float.valueOf(o1.getAttributes().get("search_sim")) < Float.valueOf(o2.getAttributes().get("search_sim")))
                        return 1;
                    else if (Float.valueOf(o1.getAttributes().get("search_sim")) > Float.valueOf(o2.getAttributes().get("search_sim")))
                        return -1;
                    else
                        return 0;
                }
            });

            for (int i = 0; i < Math.min(numofedge, sim_temp_list.size()); i++) {
                Q.add(sim_temp_list.get(i));
                result.add(sim_temp_list.get(i));
            }
        }

        System.out.println("访问服务数量：   "+n);
        return result;
    }


    public List<NodeServiceListItem> searchinnodebyname(Map<String, NodeServiceListItem> serviceList, Vector search_vec,int numofedge) {
        //当前节点下服务的结果
        List<NodeServiceListItem> result = new ArrayList<>();
        //遍历的方式
//        Queue<NodeServiceListItem> result = new PriorityQueue<NodeServiceListItem>(10, new Comparator<NodeServiceListItem>() {
//            @Override
//            public int compare(NodeServiceListItem o1, NodeServiceListItem o2) {
//                if (Float.valueOf(o1.getAttributes().get("search_sim")) < Float.valueOf(o2.getAttributes().get("search_sim")))
//                    return 1;
//                else if (Float.valueOf(o1.getAttributes().get("search_sim")) > Float.valueOf(o2.getAttributes().get("search_sim")))
//                    return -1;
//                else
//                    return 0;
//            }
//        });
//        for (NodeServiceListItem ith : serviceList.values()) {
//            //JSON.parseArray(ith.getAttributes().get("temp_Nextlist"), Temp.class);
//
//            float sim = search_vec.dot(JSON.parseObject(ith.getAttributes().get("vec"),Vector.class));
//            ith.getAttributes().put("search_sim", Float.toString(sim));
//            result.add(ith);
//        }
//        return result;

        NodeServiceListItem thefirstaddtoqueue = null;
        //初始化visit为0，代表未访问
        for (NodeServiceListItem service_item : serviceList.values()) {
            if (service_item != null && service_item.getAttributes() != null) {
                service_item.getAttributes().put("visit", "0");
                thefirstaddtoqueue = service_item;
            }
        }
        Queue<NodeServiceListItem> Q = new LinkedList<>();
        Q.add(thefirstaddtoqueue);
        thefirstaddtoqueue.getAttributes().put("visit", "1");
        int n = 0;
        while (!Q.isEmpty()) {
            NodeServiceListItem servicetemp = Q.remove();
            List<NodeServiceListItem> sim_temp_list = new ArrayList<>();
            if (servicetemp != null && servicetemp.getAttributes() != null && servicetemp.getAttributes().containsKey("temp_Nextlist") && servicetemp.getAttributes().get("temp_Nextlist") != null) {

                List<Temp> service_list = JSON.parseArray(servicetemp.getAttributes().get("temp_Nextlist"), Temp.class);
                for (int i = 0; i < service_list.size(); i++) {

                    NodeServiceListItem ith = serviceList.get("service" + service_list.get(i).getIndex());
                    if (ith != null && ith.getAttributes() != null && ith.getAttributes().containsKey("visit") && !ith.getAttributes().get("visit").equals("1")) {
                        //System.out.println(ith.getId());
                        Vector other_vec = JSON.parseObject(ith.getAttributes().get("vec"), Vector.class);
                        float sim = search_vec.dot(other_vec);
                        ith.getAttributes().put("search_sim", Float.toString(sim));
                        sim_temp_list.add(ith);
                        ith.getAttributes().put("visit", "1");
                        n++;
                    }

                }
            }
            sim_temp_list.sort(new Comparator<NodeServiceListItem>() {
                @Override
                public int compare(NodeServiceListItem o1, NodeServiceListItem o2) {
                    if (Float.valueOf(o1.getAttributes().get("search_sim")) < Float.valueOf(o2.getAttributes().get("search_sim")))
                        return 1;
                    else if (Float.valueOf(o1.getAttributes().get("search_sim")) > Float.valueOf(o2.getAttributes().get("search_sim")))
                        return -1;
                    else
                        return 0;
                }
            });

            for (int i = 0; i < Math.min(numofedge, sim_temp_list.size()); i++) {
                Q.add(sim_temp_list.get(i));
                result.add(sim_temp_list.get(i));
            }
        }

        System.out.println("访问服务数量：   "+n);
        return result;
    }

    /**
     * 预处理，遍历的方式床架语义邻接表
     *
     * @param keyword
     * @return
     */
    public ResultInfoWithoutContent PreDo_vectoNextlist(String keyword, float limit) {
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        //首先，检查自己的服务列表有没有符合要求服务
        if (serviceInfoList != null && serviceInfoList.values() != null) {
            change(serviceInfoList, true, 0);
            //createNextlist(serviceInfoList, limit, true, 0);
        }
        //遍历关联节点
        if (nodeServiceInfo.getAssociatedNodeServiceInfos() != null) {
            for (AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()) {

                NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
                if (item.getServiceList() != null) {
                    Map<Integer, NodeServiceListItem> serviceList = new HashMap<>();
                    for (NodeServiceListItem service_item : item.getServiceList()) {
                        if (service_item != null && service_item.getAttributes() != null)
                            serviceList.put(service_item.getId(), service_item);
                    }
                    change(serviceList, false, nodeServiceInfo.getId());
                    //createNextlist(serviceList, limit, false, nodeServiceInfo.getId());
                }
            }
        }

        return resultInfoWithoutContent;
    }

    /**
     * 集中式
     *
     * @param search_vec
     * @return
     */
    public ResultInfoWithoutContent recommendMethodTest01ByMap(Vector search_vec) {
        //返回的结果
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        int n=1;
        //首先，检查自己的服务列表有没有符合要求服务
        if (serviceInfoList != null && serviceInfoList.values() != null) {
            //change(serviceInfoList,true,0);
            //searchinnode(serviceInfoList, search_vec);
            resultInfoWithoutContent.add(sortNodeServiceListItem(serviceInfoList, search_vec));
        }
        //遍历关联节点
        if (nodeServiceInfo.getAssociatedNodeServiceInfos() != null) {
            for (AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()) {

                NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
                if (item.getServiceList() != null) {
                    Map<Integer, NodeServiceListItem> serviceList = new HashMap<>();
                    for (NodeServiceListItem service_item : item.getServiceList()) {
                        serviceList.put(service_item.getId(), service_item);
                    }
                    //change(serviceList,false,nodeServiceInfo.getId());
                    //searchinnode(serviceList, search_vec);
                    n++;
                    resultInfoWithoutContent.add(sortNodeServiceListItem(serviceList, search_vec));
                }
            }
        }
        System.out.println("集中式:   "+n);
        return resultInfoWithoutContent;
    }

    public ResultInfoWithoutContent recommendMethodTest02ByMap(Vector search_vec) {
        int n=0;
        //返回的结果
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        //寻找根节点
        //parent 用来保存父亲节点的NodeServiceInfo。初始化为自己
        NodeServiceInfo parent = nodeServiceInfo;
        boolean hasparent = true;
        //周围节点中包含父亲结点类型，就继续执行
        while (hasparent) {
            for (AssociatedNodeServiceInfo temp : parent.getAssociatedNodeServiceInfos()) {
                if (temp.getAssociatedType().equals("parent")) {
                    parent = getNodeServiceInfoByNodeMap(temp.getServiceAddress()).getNodeServiceInfo();
                    hasparent = true;
                    break;
                } else
                    hasparent = false;
            }
        }
        Queue<NodeServiceInfo> Q = new LinkedList<>();
        Q.add(parent);
        while (!Q.isEmpty()) {
            NodeServiceInfo The = Q.remove();
            //出队时访问当前节点
            n++;
            NodeMapItem node = getNodeServiceInfoByNodeMap(The.getServiceAddress());
            if (node.getServiceList() != null) {
                Map<String, NodeServiceListItem> serviceList = new HashMap<>();
                for (NodeServiceListItem service_item : node.getServiceList()) {
                    if (service_item != null && service_item.getName() != null) {
                        serviceList.put(service_item.getName(), service_item);
                    }
                }
//               allnodes.put(item.getNodeServiceInfo().getName(), serviceList);
//               searchinnodebyname(serviceList,search_vec);
//               change(serviceList, false, nodeServiceInfo.getId());
//               searchinnode(serviceList, search_vec);
                resultInfoWithoutContent.add(sortNodeServiceListItembyname(serviceList, search_vec));
            }

            //把当前节点的孩子们入队
            if (The != null && The.getAssociatedNodeServiceInfos() != null) {
                for (AssociatedNodeServiceInfo item : The.getAssociatedNodeServiceInfos()) {
                    if (item != null && item.getAssociatedType() != null && item.getAssociatedType().equals("child")) {
                        Q.add(getNodeServiceInfoByNodeMap(item.getServiceAddress()).getNodeServiceInfo());
                    }
                }
            }
        }
        System.out.println("树状；    "+n);
        return resultInfoWithoutContent;
    }

    /**
     * 广度优先遍历
     *
     * @param search_vec
     * @return
     */
    public ResultInfoWithoutContent recommendMethodTest03ByMap(Vector search_vec) {
        //返回的结果
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        Map<String, Map<String, NodeServiceListItem>> allnodes = new HashMap<>();
        int n=1;
        Queue<NodeServiceInfo> Q = new LinkedList<>();
        //首先，检查自己的服务列表有没有符合要求服务
        if (serviceInfoList != null && serviceInfoList.values() != null) {
            Map<String, NodeServiceListItem> serviceList = new HashMap<>();
            for (NodeServiceListItem item : serviceInfoList.values()) {
                if (item != null && item.getName() != null) {
                    serviceList.put(item.getName(), item);
                }
            }
            allnodes.put(nodeServiceInfo.getName(), serviceList);
            //searchinnodebyname(serviceList,search_vec);
            //change(serviceInfoList,true,0);
            //searchinnode(serviceInfoList, search_vec);
            resultInfoWithoutContent.add(sortNodeServiceListItembyname(serviceList, search_vec));
        }
        //树状或者图装：采用广度优先的算法遍历邻接顶点
        Q.add(nodeServiceInfo);
        System.out.println(nodeServiceInfo);
        while (!Q.isEmpty()) {
            NodeServiceInfo head = Q.remove();
            if (head.getAssociatedNodeServiceInfos() != null) {
                for (AssociatedNodeServiceInfo nodeServiceInfo : head.getAssociatedNodeServiceInfos()) {
                    NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());

                    //System.out.println(item.getNodeServiceInfo());
                    if (nodeServiceInfo.getId() != head.getId() && !allnodes.containsKey(item.getNodeServiceInfo().getName())) {
                        Q.add(item.getNodeServiceInfo());
                        if (item.getServiceList() != null) {
                            Map<String, NodeServiceListItem> serviceList = new HashMap<>();
                            for (NodeServiceListItem service_item : item.getServiceList()) {
                                if (service_item != null && service_item.getName() != null) {
                                    serviceList.put(service_item.getName(), service_item);
                                }
                            }
                            n++;
                            allnodes.put(item.getNodeServiceInfo().getName(), serviceList);
//                            searchinnodebyname(serviceList,search_vec);
//                            change(serviceList, false, nodeServiceInfo.getId());
//                            searchinnode(serviceList, search_vec);
                            resultInfoWithoutContent.add(sortNodeServiceListItembyname(serviceList, search_vec));
                        }
                    }
                }
            }
        }
        System.out.println("树状或者分布式：   "+n);
        return resultInfoWithoutContent;
    }

    public void prepareScoreInCentralization() {

//        System.out.println(scoreSetString);

        // 读取节点和服务列表
        List<String> nodeList = new ArrayList<>();
        List<String> serviceList = new ArrayList<>();
        List<String> serviceOwningNodeList = new ArrayList<>();
        nodeList.add(nodeServiceInfo.getName());
        for (NodeServiceListItem nodeServiceListItem : new ArrayList<>(serviceInfoList.values())) {
            serviceList.add(nodeServiceListItem.getAttributes().get("ISBN"));
            serviceOwningNodeList.add(nodeServiceInfo.getName());
        }
        for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()) {
            NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
            nodeList.add(item.getNodeServiceInfo().getName());
            for (NodeServiceListItem nodeServiceListItem : item.getServiceList()) {
//                System.out.println(nodeServiceListItem.getId());
//                System.out.println(nodeServiceListItem.getName());
//                System.out.println(nodeServiceListItem.getAttributes());
                serviceList.add(nodeServiceListItem.getAttributes().get("ISBN"));
                serviceOwningNodeList.add(item.getNodeServiceInfo().getName());
            }
        }

        // 构造评分矩阵
        Double[][] scoreMatrix = new Double[nodeList.size()][serviceList.size()];
        String scoreSetString = nodeServiceInfo.getAttributes().get("rating");
        Map<String, Object> scoreSet = parseMap(scoreSetString);
        for (Map.Entry<String, Object> entry : scoreSet.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            int rowIndex = nodeList.indexOf(nodeServiceInfo.getName());
            int columnIndex = serviceList.indexOf(entry.getKey());
            scoreMatrix[rowIndex][columnIndex] = Double.valueOf((String)entry.getValue());
        }
        for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()) {
            NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
            int rowIndex = nodeList.indexOf(item.getNodeServiceInfo().getName());
            System.out.println(rowIndex);
            scoreSetString = item.getNodeServiceInfo().getAttributes().get("rating");
            scoreSet = parseMap(scoreSetString);
            for (Map.Entry<String, Object> entry : scoreSet.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                int columnIndex = serviceList.indexOf(entry.getKey());
                scoreMatrix[rowIndex][columnIndex] = Double.valueOf((String)entry.getValue());
            }
        }

        Map matrixFactorizationResult = MatrixFactorization.gradAscent(scoreMatrix, 10);
        Double[][] product = (Double[][]) matrixFactorizationResult.get("product");

        saveList(nodeList, "node_list.txt");
        saveList(serviceList, "service_list.txt");
        saveList(serviceOwningNodeList, "service_owning_node_list.txt");
        saveMatrix(scoreMatrix, "score_matrix.txt");
        saveMatrix(product, "estimate_score_matrix.txt");


        System.out.println("It's my turn to implement this algorithm");
    }


    public ResultInfoWithoutContent executeRecommend(String keyword,
                                                     List<String> nodeList,
                                                     List<String> serviceList,
                                                     List<String> serviceOwningNodeList,
                                                     Double[][] estimateScoreMatrix) {
        List<NodeServiceListItem> results = new ArrayList<>();

        int row = nodeList.indexOf(nodeServiceInfo.getName());
        List<Integer> objectIndex = minIndex(estimateScoreMatrix[row], 3);
        Map<String, String> object = new TreeMap<>();
        for (Integer element: objectIndex) {
            object.put(serviceOwningNodeList.get(element), serviceList.get(element));
        }
        for (Map.Entry<String, String> entry : object.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (nodeServiceInfo.getName().equals(entry.getKey())) {
                for (NodeServiceListItem nodeServiceListItem : serviceInfoList.values()) {
                    if (nodeServiceListItem.getAttributes().get("ISBN").equals(entry.getValue()))
                        results.add(nodeServiceListItem);
                }
            }
            else {
                AssociatedNodeServiceInfo mainNode = nodeServiceInfo.getAssociatedNodeServiceInfos().get(0);
                NodeMapItem item = getNodeServiceInfoByNodeMap(mainNode.getServiceAddress());
                for (AssociatedNodeServiceInfo associatedNodeServiceInfo:
                        item.getNodeServiceInfo().getAssociatedNodeServiceInfos()) {
                    item = getNodeServiceInfoByNodeMap(mainNode.getServiceAddress());
                    if (item.getNodeServiceInfo().getName().equals(entry.getKey())) {
                        for (NodeServiceListItem nodeServiceListItem : item.getServiceList()) {
                            if (nodeServiceListItem.getAttributes().get("ISBN").equals(entry.getValue()))
                                results.add(nodeServiceListItem);
                        }
                    }
                }
            }
        }
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        resultInfoWithoutContent.setResult(results);
        System.out.println("It's my turn to implement this algorithm");
        return resultInfoWithoutContent;
    }




    /**
     * 从node字典中获取node信息
     *
      * 从node字典中获取node信息
     * @param address
     * @return
     */
    @Override
    public NodeMapItem getNodeServiceInfoByNodeMap(String address) {
        return NodeMap.getNodeServiceInfo(address);
    }

    /**
     * 从字典获取节点信息
     *
     * @param address
     * @return
     */
    @Override
    public ServiceServiceInfo getServiceInfoByServiceMap(String address) {
        return ServiceMap.getServiceInfo(address);
    }


    /**
     * 测试方法：
     *
     * @param serviceList
     * @return
     */
    private ResultInfoWithoutContent sortNodeServiceListItem(Map<Integer, NodeServiceListItem> serviceList, Vector vector) {
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        long startTime = System.nanoTime();
        //这是我的搜索函数
        List<NodeServiceListItem> resultList = searchinnode(serviceList, vector,3);
        long endTime = System.nanoTime();
        resultInfoWithoutContent.setResult(resultList);
        resultInfoWithoutContent.setCostTime(endTime - startTime);

        return resultInfoWithoutContent;
    }

    private ResultInfoWithoutContent sortNodeServiceListItembyname(Map<String, NodeServiceListItem> serviceList, Vector vector) {
        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        long startTime = System.nanoTime();
        //这是我的搜索函数
        List<NodeServiceListItem> resultList = searchinnodebyname(serviceList, vector,3);
        long endTime = System.nanoTime();
        resultInfoWithoutContent.setResult(resultList);
        resultInfoWithoutContent.setCostTime(endTime - startTime);

        return resultInfoWithoutContent;
    }

    public static Map<String, Object> parseMap (String mapString) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(mapString, map.getClass());
        return map;
    }

    public static void saveList(List<String> list, String fileName) {
        try {
            File writeName = new File(fileName);
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for (String item : list) {
                    out.write(item);
                    out.newLine();
                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMatrix(Double[][] matrix, String fileName) {
        try {
            File writeName = new File(fileName);
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        out.write(String.valueOf(matrix[i][j]));
                        if (j == matrix[0].length - 1)
                            out.newLine();
                        else
                            out.write(",");
                    }
                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadList(String fileName) {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(fileName))));
            String line = "";
            while(true) {
                line = br.readLine();
                if(line == null)
                    break;
                result.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Double[][] loadMatrix(String fileName, int rowNumber, int columnNumber) {
        Double[][] result = new Double[rowNumber][columnNumber];
        int rowIndex = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(fileName))));
            String line = "";
            while(true) {
                line = br.readLine();
                if(line == null)
                    break;
                String[] elementArray = line.split(",");
                for (int i = 0; i < elementArray.length; i++) {
                    result[rowIndex][i] = Double.valueOf(elementArray[i]);
                }
                rowIndex ++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Integer> minIndex(Double[] array, int n) {
        List<Integer> result = new ArrayList<Integer>();
        HashMap map = new HashMap();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], i); // 将值和下标存入Map
        }
        // 排列
        List list = new ArrayList();
        LeastComparator myComparator = new LeastComparator();
        Arrays.sort(array, myComparator); // 升序排列
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        // 查找原始下标
        for (int i = 0; i < n; i++) {
            result.add((Integer)map.get(array[i]));
        }
        return result;
    }

    static class LeastComparator implements Comparator<Double> {
        public int compare(Double o1, Double o2) {
            return (int)(o2-o1);
        }
    }

}
