package com.msf.fuxi.xdiamond.domain;

import java.io.Serializable;
import java.util.Date;

public class Profile implements Serializable {

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.id
   * @mbggenerated
   */
  private Integer id;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.projectId
   * @mbggenerated
   */
  private Integer projectId;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.name
   * @mbggenerated
   */
  private String name;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.access
   * @mbggenerated
   */
  private Integer access;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.secretKey
   * @mbggenerated
   */
  private String secretKey;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.description
   * @mbggenerated
   */
  private String description;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.createTime
   * @mbggenerated
   */
  private Date createTime;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column profile.updateDate
   * @mbggenerated
   */
  private Date updateDate;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database table profile
   * @mbggenerated
   */
  private static final long serialVersionUID = 1L;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.id
   * @return  the value of profile.id
   * @mbggenerated
   */
  public Integer getId() {
    return id;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.id
   * @param id  the value for profile.id
   * @mbggenerated
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.projectId
   * @return  the value of profile.projectId
   * @mbggenerated
   */
  public Integer getProjectId() {
    return projectId;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.projectId
   * @param projectId  the value for profile.projectId
   * @mbggenerated
   */
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.name
   * @return  the value of profile.name
   * @mbggenerated
   */
  public String getName() {
    return name;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.name
   * @param name  the value for profile.name
   * @mbggenerated
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.access
   * @return  the value of profile.access
   * @mbggenerated
   */
  public Integer getAccess() {
    return access;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.access
   * @param access  the value for profile.access
   * @mbggenerated
   */
  public void setAccess(Integer access) {
    this.access = access;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.secretKey
   * @return  the value of profile.secretKey
   * @mbggenerated
   */
  public String getSecretKey() {
    return secretKey;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.secretKey
   * @param secretKey  the value for profile.secretKey
   * @mbggenerated
   */
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.description
   * @return  the value of profile.description
   * @mbggenerated
   */
  public String getDescription() {
    return description;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.description
   * @param description  the value for profile.description
   * @mbggenerated
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.createTime
   * @return  the value of profile.createTime
   * @mbggenerated
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.createTime
   * @param createTime  the value for profile.createTime
   * @mbggenerated
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column profile.updateDate
   * @return  the value of profile.updateDate
   * @mbggenerated
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column profile.updateDate
   * @param updateDate  the value for profile.updateDate
   * @mbggenerated
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}