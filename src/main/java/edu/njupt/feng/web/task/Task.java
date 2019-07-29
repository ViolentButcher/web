package edu.njupt.feng.web.task;

import edu.njupt.feng.web.management.NodeManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Async
public class Task {

    @Autowired
    private NodeManagement nodeManagement;

    public Future<String> startNode(Integer nodeID) throws InterruptedException{
        long begin = System.currentTimeMillis();

        nodeManagement.startNode(nodeID);

        long end = System.currentTimeMillis();
        System.out.println("node" + nodeID + "耗时="+(end-begin) + "-------");
        return new AsyncResult<String>(nodeID + "启动完成");
    }

}
