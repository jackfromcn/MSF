package com.msf.fuxi.xdiamond.persistence;

import com.msf.fuxi.xdiamond.domain.GroupRole;
import com.msf.fuxi.xdiamond.domain.GroupRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupRoleMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int countByExample(GroupRoleExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int deleteByExample(GroupRoleExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int deleteByPrimaryKey(@Param("groupId") Integer groupId, @Param("roleId") Integer roleId);
  
  int deleteByGroupId(Integer groupId);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int insert(GroupRole record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int insertSelective(GroupRole record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  List<GroupRole> selectByExample(GroupRoleExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int updateByExampleSelective(@Param("record") GroupRole record,
                               @Param("example") GroupRoleExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table group_roles
   * @mbggenerated
   */
  int updateByExample(@Param("record") GroupRole record, @Param("example") GroupRoleExample example);
  /**
   * 查询该角色绑定组的数量
   * @param roleId
   * @return
   */
  int selectCountByRole(@Param("roleId") Integer roleId);
  /**
   * 查询该组绑定的角色数量。
   * @param groupId
   * @return
   */
  int selectCountByGroup(@Param("groupId") Integer groupId);
}