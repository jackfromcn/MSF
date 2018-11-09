package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.domain.*;
import com.msf.fuxi.xdiamond.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class GroupRoleService {

  @Autowired
  GroupRoleMapper groupRoleMapper;

  @Autowired
  RoleMapper roleMapper;
  @Autowired
  GroupMapper groupMapper;
  @Autowired
  PermissionMapper permissionMapper;
  @Autowired
  RolePermissionMapper rolePermissionMapper;

  @Transactional
  public int insert(int roleId, int groupId) {
    GroupRole record = new GroupRole();
    record.setRoleId(roleId);
    record.setGroupId(groupId);
    int result = groupRoleMapper.insert(record);
    Permission per= permissionMapper.selectByPermissionStr("project:create:"+groupId);
    if(per == null){
    	//这里给角色绑定组后，自动创建权限
    Group group = groupMapper.selectByPrimaryKey(groupId);
    Permission permission = new Permission();
    permission.setPermissionStr("project:create:"+groupId);
    permission.setDescription(group.getName()+"-项目管理权限");
    permissionMapper.insert(permission);
    }
    Permission resultPer= permissionMapper.selectByPermissionStr("project:create:"+groupId);
    RolePermission hasRP = rolePermissionMapper.selectRolePermission(roleId,resultPer.getId());
    if(hasRP == null){
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(resultPer.getId());
        rolePermissionMapper.insert(rolePermission);
    }
    return result;
  }

  public int delete(int roleId, int groupId) {
    return groupRoleMapper.deleteByPrimaryKey(groupId, roleId);
  }

  public List<Role> getRoles(int groupId) {
    List<Role> result = new LinkedList<Role>();
    GroupRoleExample example = new GroupRoleExample();
    example.createCriteria().andGroupIdEqualTo(groupId);
    List<GroupRole> groupRoleList = groupRoleMapper.selectByExample(example);
    for (GroupRole groupRole : groupRoleList) {
      Integer roleId = groupRole.getRoleId();
      result.add(roleMapper.selectByPrimaryKey(roleId));
    }
    return result;
  }

  public List<Group> getGroups(int roleId) {
    List<Group> result = new LinkedList<Group>();
    GroupRoleExample example = new GroupRoleExample();
    example.createCriteria().andRoleIdEqualTo(roleId);
    List<GroupRole> groupRoleList = groupRoleMapper.selectByExample(example);
    for (GroupRole groupRole : groupRoleList) {
      Integer groupId = groupRole.getGroupId();
      result.add(groupMapper.selectByPrimaryKey(groupId));
    }
    return result;
  }
  
  public int selectCountByRole(int roleId){
	  return groupRoleMapper.selectCountByRole(roleId);
  }

  public int selectCountByGroup(int groupId){
	  return groupRoleMapper.selectCountByGroup(groupId);
  }
}
