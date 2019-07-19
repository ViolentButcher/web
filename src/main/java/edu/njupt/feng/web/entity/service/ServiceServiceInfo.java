package edu.njupt.feng.web.entity.service;

import edu.njupt.feng.web.entity.common.Article;
import edu.njupt.feng.web.entity.database.ServiceInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ServiceServiceInfo implements Serializable {

    private Integer id;
    private String name;
    private Map<String,String> attributes;
    private Integer node;
    private Integer cluster;
    private String content;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public String toString() {
        return "ServiceServiceInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                ", node=" + node +
                ", cluster=" + cluster +
                ", content=" + content +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", serviceAddress='" + serviceAddress + '\'' +
                '}';
    }
}
