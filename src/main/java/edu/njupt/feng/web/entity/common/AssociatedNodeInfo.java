package edu.njupt.feng.web.entity.common;

import java.io.Serializable;

public class AssociatedNodeInfo implements Serializable {

    private Integer id;

    private String associatedType;

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
}
