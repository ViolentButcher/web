package edu.njupt.feng.web.entity.database;


public class ServiceInfo {

  private Integer id;
  private String name;
  private String attributes;
  private Integer node;
  private Integer cluster;
  private String content;
  private java.util.Date createTime;
  private java.util.Date modifyTime;

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


  public String getAttributes() {
    return attributes;
  }

  public void setAttributes(String attributes) {
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


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }


  public java.util.Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(java.util.Date modifyTime) {
    this.modifyTime = modifyTime;
  }

}
