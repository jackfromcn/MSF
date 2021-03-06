package com.msf.fuxi.xdiamond.persistence;

import com.msf.fuxi.xdiamond.domain.Permission;
import com.msf.fuxi.xdiamond.domain.PermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int countByExample(PermissionExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int deleteByExample(PermissionExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int deleteByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int insert(Permission record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int insertSelective(Permission record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  List<Permission> selectByExample(PermissionExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  Permission selectByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int updateByExampleSelective(@Param("record") Permission record,
                               @Param("example") PermissionExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int updateByExample(@Param("record") Permission record,
                      @Param("example") PermissionExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int updateByPrimaryKeySelective(Permission record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table permission
   * @mbggenerated
   */
  int updateByPrimaryKey(Permission record);
  /**
   * 通过 permissionStr查询权限
   * @return
   */
  Permission selectByPermissionStr(String permissionStr);
}