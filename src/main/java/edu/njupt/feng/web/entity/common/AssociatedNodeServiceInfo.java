package edu.njupt.feng.web.entity.common;

import java.io.Serializable;


public class AssociatedNodeServiceInfo implements Serializable {

    private Integer id;

    private String associatedType;

    private String serviceAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssociatedType() {
        return associatedType;
    }

    public void setAssociatedType(String associatedType) {
        this.associatedType = associatedType;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public String toString() {
        return "AssociatedNodeServiceInfo{" +
                "id=" + id +
                ", associatedType='" + associatedType + '\'' +
                ", serviceAddress='" + serviceAddress + '\'' +
                '}';
    }
}
