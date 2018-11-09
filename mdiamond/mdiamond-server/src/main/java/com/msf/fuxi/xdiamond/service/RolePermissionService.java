package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.persistence.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {
	
	@Autowired
	RolePermissionMapper rolePermissionMapper;
	
	public int selectCountByPermissionId(Integer permissionId) {
		return rolePermissionMapper.selectCountByPermissionId(permissionId);
	}

}
