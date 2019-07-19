package edu.njupt.feng.web.entity.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ClusterServiceInfo implements Serializable {

    private Integer id;
    private String name;
    private Map<String,String> attribute;
    private String configuration;
    private Integer nodeNumber;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
    private Integer state;
    private Integer type;
    private String serviceAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public String toString() {
        return "ClusterServiceInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attribute=" + attribute +
                ", configuration='" + configuration + '\'' +
                ", nodeNumber=" + nodeNumber +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", state=" + state +
                ", type=" + type +
                ", serviceAddress='" + serviceAddress + '\'' +
                '}';
    }
}
