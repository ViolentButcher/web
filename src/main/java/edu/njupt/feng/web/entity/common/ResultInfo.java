package edu.njupt.feng.web.entity.common;

import edu.njupt.feng.web.entity.service.ServiceServiceInfo;

import java.io.Serializable;
import java.util.List;

public class ResultInfo implements Serializable {

    private List<ServiceServiceInfo> result;

    private String costTime;

    public List<ServiceServiceInfo> getResult() {
        return result;
    }

    public void setResult(List<ServiceServiceInfo> result) {
        this.result = result;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }
}
