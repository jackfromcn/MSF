package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.msf.fuxi.xdiamond.domain.Group;
import com.msf.fuxi.xdiamond.domain.Permission;
import com.msf.fuxi.xdiamond.domain.Role;
import com.msf.fuxi.xdiamond.domain.User;
import com.msf.fuxi.xdiamond.service.GroupRoleService;
import com.msf.fuxi.xdiamond.service.RoleService;
import com.msf.fuxi.xdiamond.service.UserRoleService;
import com.msf.fuxi.xdiamond.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("api")
@Transactional
public class RoleController {

  @Autowired
  RoleService roleService;
  @Autowired
  GroupRoleService groupRoleService;
  @Autowired
  UserRoleService userRoleService;

  /**
   * GET /roles -> get all roles.
   */
  @Timed
  @RequestMapping(value = "/roles", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResult> getAll() {
    List<Role> roles = roleService.list();

    return RestResult.success().withResult("roles", roles).build();
  }

  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<RestResult> create(@Valid @RequestBody Role role) {
    roleService.insert(role);
    return RestResult.success().withResult("message", "创建role成功").build();
  }

  @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<RestResult> delete(@PathVariable Integer roleId) {
	 int count = groupRoleService.selectCountByRole(roleId);
	 if(count>0){
		 return RestResult.fail().withErrorMessage("删除角色失败，请先解除绑定的组。").build();
	 }
    roleService.delete(roleId);
    return RestResult.success().withResult("message", "删除roleId成功").build();
  }

  @RequestMapping(value = "/roles", method = RequestMethod.PATCH)
  public Object update(@Valid @RequestBody Role role) {
    roleService.patch(role);
    return RestResult.success().withResult("message", "更新role成功").build();
  }

  // 权限Permission相关
  @Timed
  @RequestMapping(value = "/roles/{roleId}/permissions", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResult> getPermissions(@PathVariable Integer roleId) {
    List<Permission> permissions = roleService.getPermissions(roleId);

    return RestResult.success().withResult("permissions", permissions).withResult("roleId", roleId)
        .build();
  }

  @RequestMapping(value = "/roles/{roleId}/permissions/{permissionId}", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<RestResult> addPermission(@PathVariable Integer roleId,
                                                  @PathVariable Integer permissionId) {
    roleService.addPermission(roleId, permissionId);
    return RestResult.success().withResult("message", "增加权限成功").build();
  }

  @RequestMapping(value = "/roles/{roleId}/permissions/{permissionId}",
      method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<RestResult> deletePermission(@PathVariable Integer roleId,
                                                     @PathVariable Integer permissionId) {
    roleService.deletePermission(roleId, permissionId);
    return RestResult.success().withResult("message", "删除权限成功").build();
  }

  // 用户相关的
  @Timed
  @RequestMapping(value = "/roles/{roleId}/users", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResult> getUsers(@PathVariable Integer roleId) {
    List<User> users = userRoleService.getUsers(roleId);

    return RestResult.success().withResult("users", users).withResult("roleId", roleId).build();
  }

  @RequestMapping(value = "/roles/{roleId}/users/{userId}", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<RestResult> addUser(@PathVariable Integer roleId,
                                            @PathVariable Integer userId) {
    userRoleService.insert(roleId, userId);
    return RestResult.success().withResult("message", "增加用户成功").build();
  }

  @RequestMapping(value = "/roles/{roleId}/users/{userId}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<RestResult> deleteUser(@PathVariable Integer roleId,
                                               @PathVariable Integer userId) {
    userRoleService.delete(roleId, userId);
    return RestResult.success().withResult("message", "删除用户成功").build();
  }

  // 组相关的
  @RequestMapping(value = "/roles/{roleId}/groups", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResult> getGroups(@PathVariable Integer roleId) {
    List<Group> groups = groupRoleService.getGroups(roleId);

    return RestResult.success().withResult("groups", groups).withResult("roleId", roleId).build();
  }

  @RequestMapping(value = "/roles/{roleId}/groups/{groupId}", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<RestResult> addGroup(@PathVariable Integer roleId,
                                             @PathVariable Integer groupId) {
    groupRoleService.insert(roleId, groupId);
    return RestResult.success().withResult("message", "增加组成功").build();
  }

  @RequestMapping(value = "/roles/{roleId}/groups/{groupId}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<RestResult> deleteGroup(@PathVariable Integer roleId,
                                                @PathVariable Integer groupId) {
    groupRoleService.delete(roleId, groupId);
    return RestResult.success().withResult("message", "删除组成功").build();
  }
}
