package edu.njupt.feng.web;

import edu.njupt.feng.web.mapper.NodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Autowired
    private NodeMapper nodeMapper;

    @Test
    public void contextLoads() {

        nodeMapper.updateAssoicatedNode("1,2",2);

    }

}
