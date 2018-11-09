package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.msf.fuxi.xdiamond.domain.Permission;
import com.msf.fuxi.xdiamond.service.PermissionService;
import com.msf.fuxi.xdiamond.service.RolePermissionService;
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
public class PermissionController {

  @Autowired
  PermissionService permissionService;
  
  @Autowired
  RolePermissionService rolePermissionService;

  /**
   * GET /permissions -> get all permissions.
   */
  @Timed
  @RequestMapping(value = "/permissions", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResult> getAll() {
    List<Permission> permissions = permissionService.list();

    return RestResult.success().withResult("permissions", permissions).build();
  }

  @RequestMapping(value = "/permissions", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<RestResult> create(@Valid @RequestBody Permission permission) {
    permissionService.insert(permission);
    return RestResult.success().withResult("message", "创建permission成功").build();
  }

  @RequestMapping(value = "/permissions/{permissionId}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<RestResult> delete(@PathVariable Integer permissionId) {
	int count = rolePermissionService.selectCountByPermissionId(permissionId);
    if(count>0){
    	return RestResult.fail().withErrorMessage("删除permission失败，请先解除和角色绑定关系").build();
    }
	permissionService.delete(permissionId);
    return RestResult.success().withResult("message", "删除permissionId成功").build();
  }

  @RequestMapping(value = "/permissions", method = RequestMethod.PATCH)
  public Object update(@Valid @RequestBody Permission permission) {
    permissionService.patch(permission);
    return RestResult.success().withResult("message", "更新permission成功").build();
  }

}
