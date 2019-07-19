package edu.njupt.feng.web.entity.service;

import edu.njupt.feng.web.entity.common.AssociatedNodeServiceInfo;
import edu.njupt.feng.web.entity.common.Position;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NodeServiceInfo implements Serializable {
    private Integer id;
    private String name;
    private Map<String,String> attributes;
    private Integer serviceNumber;
    private Position position;
    private Integer cluster;
    private List<AssociatedNodeServiceInfo> associatedNodeServiceInfos;
    private Integer level;
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

    public Integer getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(Integer serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public List<AssociatedNodeServiceInfo> getAssociatedNodeServiceInfos() {
        return associatedNodeServiceInfos;
    }

    public void setAssociatedNodeServiceInfos(List<AssociatedNodeServiceInfo> associatedNodeServiceInfos) {
        this.associatedNodeServiceInfos = associatedNodeServiceInfos;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
        return "NodeServiceInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                ", serviceNumber=" + serviceNumber +
                ", position=" + position +
                ", cluster=" + cluster +
                ", associatedNodeServiceInfos=" + associatedNodeServiceInfos +
                ", level=" + level +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", serviceAddress='" + serviceAddress + '\'' +
                '}';
    }
}
