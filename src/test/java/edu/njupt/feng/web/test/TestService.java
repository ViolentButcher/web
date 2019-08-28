package edu.njupt.feng.web.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.Article;
import edu.njupt.feng.web.entity.database.ClusterInfo;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.mapper.TestMapper;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ClusterService clusterService;

    public void createServices(){

        for (int j = 701 ; j<=705;j++){
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setId(j);
            nodeInfo.setCluster(1);
            nodeInfo.setName("node" + j);
            nodeInfo.setLevel(1);
            nodeInfo.setCreateTime(new Date());
            nodeInfo.setModifyTime(new Date());
            nodeInfo.setServiceNumber(1000);
            try{
                testMapper.addNode(nodeInfo);
            }catch (Exception e){

            }

        }

        List<Article> articles = testMapper.getArticles();
        int i = 700000;
        for (Article article : articles){
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setName("service" + String.valueOf(i++));
            Map<String,String> attrs = new HashMap<>();
            attrs.put("author",article.getAuthor());
            attrs.put("title",article.getTitle());
            attrs.put("publish_time",article.getPublishTime());
            attrs.put("keyword",article.getWord());
            attrs.put("journal",article.getJournal());
            ObjectMapper mapper = new ObjectMapper();
            try {
                serviceInfo.setAttributes(mapper.writeValueAsString(attrs));
            }catch (Exception e){

            }
            serviceInfo.setNode(i/1000 + 1);
            serviceInfo.setCluster(7);
            serviceInfo.setCreateTime(new Date());
            serviceInfo.setModifyTime(new Date());
            serviceInfo.setContent(article.getSummary());
            try{
                testMapper.addService(serviceInfo);
            }catch (Exception e){

            }

            if(i-700000==5000){
                break;
            }
        }
    }

    public void createCluster(int clusterID,String clusterName,int nodeNumber){
        try{
            clusterService.deleteCluster(clusterID);
        }catch (Exception e){

        }

        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setId(clusterID);
        clusterInfo.setName(clusterName);
        clusterInfo.setNodeNumber(nodeNumber);
        clusterInfo.setCreateTime(new Date());
        clusterInfo.setModifyTime(new Date());

        testMapper.addCluster(clusterInfo);

        int countArticle = 0;

        for (int i=(clusterID-1)*100+1 ; i<=(clusterID-1)*100+nodeNumber;i++){
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setId(i);
            nodeInfo.setName("node" + i);
            nodeInfo.setServiceNumber(1000);
            nodeInfo.setLevel(1);
            nodeInfo.setCluster(clusterID);
            nodeInfo.setCreateTime(new Date());
            nodeInfo.setModifyTime(new Date());

            testMapper.addNode(nodeInfo);

            for (int j = (i-1)*1000+1; j<=(i-1)*1000+1000 ; j++){
                ServiceInfo serviceInfo = new ServiceInfo();

                Article article = testMapper.getArticleByID(++countArticle);

                while (article == null || article.getSummary() == null || article.getSummary().length() == 0){
                    article = testMapper.getArticleByID(++countArticle);
                }
                serviceInfo.setContent(article.getSummary());
                Map<String,String> attrs = new HashMap<>();
                attrs.put("author",article.getAuthor());
                attrs.put("title",article.getTitle());
                attrs.put("publish_time",article.getPublishTime());
                attrs.put("keyword",article.getWord());
                attrs.put("journal",article.getJournal());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    serviceInfo.setAttributes(mapper.writeValueAsString(attrs));
                }catch (Exception e){

                }

                serviceInfo.setId(j);
                serviceInfo.setName("service" + j);
                serviceInfo.setCluster(clusterID);
                serviceInfo.setNode(i);
                serviceInfo.setCreateTime(new Date());
                serviceInfo.setModifyTime(new Date());

                testMapper.addService(serviceInfo);
            }
        }
    }

}
