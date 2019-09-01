package edu.njupt.feng.web.configuration;

import edu.njupt.feng.web.management.ClusterManagement;
import edu.njupt.feng.web.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *ApplicationRunner 实现类
 *  SpringBoot初始化后的执行操作
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    //cluster管理类
    @Autowired
    private ClusterManagement clusterManagement;

    @Autowired
    private ClusterService clusterService;

    /**
     * 完成初始化操作
     *      从数据库初始化、启动项目
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        clusterManagement.init();
    }
}
