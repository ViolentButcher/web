package edu.njupt.feng.web.configuration;

import edu.njupt.feng.web.management.ClusterManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitAppliactionRunner implements ApplicationRunner {

    @Autowired
    private ClusterManagement clusterManagement;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clusterManagement.init();
    }
}
