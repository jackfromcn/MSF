package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.domain.Role;
import com.msf.fuxi.xdiamond.domain.User;
import com.msf.fuxi.xdiamond.domain.UserRole;
import com.msf.fuxi.xdiamond.domain.UserRoleExample;
import com.msf.fuxi.xdiamond.persistence.RoleMapper;
import com.msf.fuxi.xdiamond.persistence.UserMapper;
import com.msf.fuxi.xdiamond.persistence.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserRoleService {

  @Autowired
  UserRoleMapper userRoleMapper;

  @Autowired
  RoleMapper roleMapper;
  @Autowired
  UserMapper userMapper;

  public int insert(int roleId, int userId) {
    UserRole record = new UserRole();
    record.setRoleId(roleId);
    record.setUserId(userId);
    return userRoleMapper.insert(record);
  }

  public int delete(int roleId, int userId) {
    return userRoleMapper.deleteByPrimaryKey(userId, roleId);
  }

  public List<Role> getRoles(int userId) {
    List<Role> result = new LinkedList<Role>();
    UserRoleExample example = new UserRoleExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
    for (UserRole userRole : userRoleList) {
      Integer roleId = userRole.getRoleId();
      result.add(roleMapper.selectByPrimaryKey(roleId));
    }
    return result;
  }

  public List<User> getUsers(int roleId) {
    List<User> result = new LinkedList<User>();
    UserRoleExample example = new UserRoleExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
    for (UserRole userRole : userRoleList) {
      Integer userId = userRole.getUserId();
      result.add(userMapper.selectByPrimaryKey(userId));
    }
    return result;
  }

}
