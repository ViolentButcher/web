package edu.njupt.feng.web.entity.database;



import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class ClusterInfo implements Serializable {

  private Integer id;
  private String name;
  private String attribute;
  private String configuration;
  private Integer nodeNumber;
  private java.util.Date createTime;
  private java.util.Date modifyTime;
  private Integer state;
  private Integer type;


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

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
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
}
