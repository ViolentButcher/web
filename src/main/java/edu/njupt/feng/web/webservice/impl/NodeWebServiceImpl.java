package edu.njupt.feng.web.webservice.impl;

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
import edu.njupt.feng.web.webservice.ServiceWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.io.*;
import java.util.*;

public class NodeWebServiceImpl implements NodeWebService {

    private NodeServiceInfo nodeServiceInfo;

    private Map<Integer,NodeServiceListItem> serviceInfoList = new HashMap<>();

    /**
     * 更新节点名称
     * @param name
     */
    @Override
    public void updateName(String name) {
        nodeServiceInfo.setName(name);
    }

    /**
     * 更新所属服务的名称
     * @param name
     * @param serviceID
     */
    @Override
    public void updateServiceName(String name,int serviceID) {
        serviceInfoList.get(serviceID).setName(name);
    }

    /**
     * 更新节点位置
     * @param position
     */
    @Override
    public void updatePosition(Position position) {
        nodeServiceInfo.setPosition(position);
    }

    /**
     * 添加服务
     * @param serviceInfo
     */
    @Override
    public void addService(ServiceInfo serviceInfo) {
        serviceInfoList.put(serviceInfo.getId(), Convert2ServiceInfo.serviceInfo2NodeServiceListItem(serviceInfo));
    }

    /**
     * 设置节点信息
     * @param nodeInfo
     */
    @Override
    public void setNodeInfo(NodeServiceInfo nodeInfo) {
        nodeServiceInfo = nodeInfo;
    }

    /**
     * 获取节点信息
     * @return
     */
    @Override
    public NodeServiceInfo getNodeInfo() {
        return nodeServiceInfo;
    }

    /**
     * 获取节点上面的服务列表
     * @return
     */
    @Override
    public List<NodeServiceListItem> getNodeServiceList() {
        return new ArrayList<>(serviceInfoList.values());
    }

    /**
     * 设置节点上的服务列表
     * @param serviceList
     */
    @Override
    public void setNodeServiceList(List<NodeServiceListItem> serviceList) {
        for(NodeServiceListItem serviceInfo : serviceList){
            serviceInfoList.put(serviceInfo.getId(),serviceInfo);
        }
    }


    /**
     * 更新节点属性信息（自身）
     * @param attributes
     */
    @Override
    public void updateNodeAttributes(Map<String, String> attributes) {
        nodeServiceInfo.setAttributes(attributes);

        //全局NodeMap的属性更新
        NodeMap.updateNodeAttributes(nodeServiceInfo.getServiceAddress(),attributes);

        ObjectMapper mapper = new ObjectMapper();
        try{
            MySQLUtil.updateNodeAttributes(mapper.writeValueAsString(attributes),nodeServiceInfo.getId());
        }catch (Exception e){

        }
    }

    /**
     * 更新服务属性
     * @param attributes
     * @param serviceID
     */
    @Override
    public void updateServiceAttributes(Map<String, String> attributes, Integer serviceID) {
        serviceInfoList.get(serviceID).setAttributes(attributes);
    }

    /**
     * 更新其它节点的属性
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
     * 访问其它节点，获取其服务列表信息
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
     * @param keyword
     * @return
     */
    @Override
    public ResultInfoWithoutContent testSearch(String keyword,Integer type) {
        ResultInfoWithoutContent results = new ResultInfoWithoutContent();
        if(type == 1){
            long startTime = System.currentTimeMillis();
            results = recommendMethodTest01ByWebservice(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }else if (type == 2){
            long startTime = System.currentTimeMillis();
            results =  recommendMethodTest02ByMap(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }

    /**
     * 推荐测试
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
            results =  recommendMethodTest02ByMap(keyword);
            long endTime = System.currentTimeMillis();
            System.out.println("************************  运行时间:" + (endTime - startTime) + "ms  ***************");
        }
        return results;
    }

    /**
     * 搜索方法测试，通过webservice
     * @param keyword
     * @return
     */
    public ResultInfoWithoutContent recommendMethodTest01ByWebservice(String keyword){

        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();

        //首先，检查自己的服务列表有没有符合要求服务
        if(serviceInfoList!= null && serviceInfoList.values() != null){
            resultInfoWithoutContent.add(sortNodeServiceListItem(new ArrayList<>(serviceInfoList.values()),keyword));
        }

        if ( nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
            //遍历关联节点
            for(AssociatedNodeServiceInfo associatedNode : nodeServiceInfo.getAssociatedNodeServiceInfos()){

                //获取关联节点的节点信息
                NodeServiceInfo associatedNodeInfo = getNodeServiceInfo(associatedNode.getServiceAddress());

                //获取关联节点的服务列表
                List<NodeServiceListItem> associatedNodeServicesList = getServiceList(associatedNode.getServiceAddress());

                if (associatedNodeServicesList!=null){
                    resultInfoWithoutContent.add(sortNodeServiceListItem(associatedNodeServicesList,keyword));
                }

            }
        }

        return resultInfoWithoutContent;
    }

        public ResultInfoWithoutContent recommendMethodTest02ByMap(String keyword){
            ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();

            //首先，检查自己的服务列表有没有符合要求服务
            if(serviceInfoList!= null && serviceInfoList.values() != null){
                resultInfoWithoutContent.add(sortNodeServiceListItem(new ArrayList<>(serviceInfoList.values()),keyword));
            }
            //遍历关联2节点
            if(nodeServiceInfo.getAssociatedNodeServiceInfos() != null){
                for(AssociatedNodeServiceInfo nodeServiceInfo : nodeServiceInfo.getAssociatedNodeServiceInfos()) {
                    NodeMapItem item = getNodeServiceInfoByNodeMap(nodeServiceInfo.getServiceAddress());
                    resultInfoWithoutContent.add(sortNodeServiceListItem(item.getServiceList(), keyword));
                }
            }
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
     * @param address
     * @return
     */
    @Override
    public NodeMapItem getNodeServiceInfoByNodeMap(String address) {
        return NodeMap.getNodeServiceInfo(address);
    }

    /**
     * 从字典获取节点信息
     * @param address
     * @return
     */
    @Override
    public ServiceServiceInfo getServiceInfoByServiceMap(String address) {
        return ServiceMap.getServiceInfo(address);
    }


    /**
     * 测试方法：
     * @param serviceList
     * @param keyword
     * @return
     */
    private ResultInfoWithoutContent sortNodeServiceListItem(List<NodeServiceListItem> serviceList,String keyword){

        ResultInfoWithoutContent resultInfoWithoutContent = new ResultInfoWithoutContent();
        List<NodeServiceListItem> results = new ArrayList<>();

        long startTime = System.nanoTime();
        if(serviceList != null){
            //首先，检查自己的服务列表有没有符合要求服务
            for(NodeServiceListItem item : serviceList){
                //检查服务的属性信息是否包含关键字
                if(item.getAttributes() != null && item.getAttributes().values() != null){
                    for(String attrValue : item.getAttributes().values()){
                        if (attrValue.contains(keyword)){
                            results.add(item);
                            break;
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        resultInfoWithoutContent.setResult(results);
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
