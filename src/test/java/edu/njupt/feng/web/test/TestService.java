package edu.njupt.feng.web.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njupt.feng.web.entity.common.Article;
import edu.njupt.feng.web.entity.database.NodeInfo;
import edu.njupt.feng.web.entity.database.ServiceInfo;
import edu.njupt.feng.web.mapper.TestMapper;
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

    public void createServices(){

        for (int j = 600 ; j<=700;j++){
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
        int i = 600000;
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
            serviceInfo.setNode(i/1000 + 601);
            serviceInfo.setCluster(6);
            serviceInfo.setCreateTime(new Date());
            serviceInfo.setModifyTime(new Date());
            serviceInfo.setContent(article.getSummary());
            try{
                testMapper.addService(serviceInfo);
            }catch (Exception e){

            }

            if(i-600000==100000){
                break;
            }
        }
    }

}
