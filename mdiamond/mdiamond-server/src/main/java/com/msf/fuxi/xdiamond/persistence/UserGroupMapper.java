package com.msf.fuxi.xdiamond.persistence;

import com.msf.fuxi.xdiamond.domain.UserGroup;
import com.msf.fuxi.xdiamond.domain.UserGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int countByExample(UserGroupExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int deleteByExample(UserGroupExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

  int deleteByUserId(@Param("userId") Integer userId);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int insert(UserGroup record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int insertSelective(UserGroup record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  List<UserGroup> selectByExample(UserGroupExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  UserGroup selectByPrimaryKey(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

  List<UserGroup> selectByUserId(@Param("userId") Integer userId);

  List<UserGroup> selectByGroupId(@Param("groupId") Integer groupId);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int updateByExampleSelective(@Param("record") UserGroup record,
                               @Param("example") UserGroupExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int updateByExample(@Param("record") UserGroup record, @Param("example") UserGroupExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int updateByPrimaryKeySelective(UserGroup record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table user_groups
   * @mbggenerated
   */
  int updateByPrimaryKey(UserGroup record);
}