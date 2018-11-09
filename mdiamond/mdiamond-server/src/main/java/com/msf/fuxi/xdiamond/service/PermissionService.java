package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.domain.Permission;
import com.msf.fuxi.xdiamond.domain.PermissionExample;
import com.msf.fuxi.xdiamond.persistence.PermissionMapper;
import com.msf.fuxi.xdiamond.persistence.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

  @Autowired
  PermissionMapper permissionMapper;
  @Autowired
  RolePermissionMapper rolePermissionMapper;

  public List<Permission> list() {
    PermissionExample permissionExample = new PermissionExample();
    return permissionMapper.selectByExample(permissionExample);
  }

  public void insert(Permission permission) {
    permissionMapper.insert(permission);
  }

  public void delete(int id) {
	permissionMapper.deleteByPrimaryKey(id);
  }

  public void patch(Permission permission) {
    permissionMapper.updateByPrimaryKeySelective(permission);
  }

}
