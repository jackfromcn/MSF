package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.msf.fuxi.xdiamond.domain.Dependency;
import com.msf.fuxi.xdiamond.domain.Profile;
import com.msf.fuxi.xdiamond.domain.Project;
import com.msf.fuxi.xdiamond.service.*;
import com.msf.fuxi.xdiamond.web.RestResult;
import com.msf.fuxi.xdiamond.web.shiro.PermissionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("api/projects")
@Transactional
public class ProjectController {
  @Autowired
  MappingService mappingService;

  @Autowired
  ProjectService projectService;
  @Autowired
  DependencyService dependencyService;
  @Autowired
  ConfigService configService;
  @Autowired
  ProfileService profileService;

  @Timed
  @RequestMapping(value = "", method = RequestMethod.GET)
  public Object list() {
    List<Project> projects = projectService.list();
    // 过滤掉没有read权限的Project
    List<Project> result = Lists.newLinkedList();
    for (Project project : projects) {
      if (PermissionHelper.hasProjectRead(project.getId())) {
        result.add(project);
      }
    }

    return RestResult.success().withResult("projects", result).build();
  }

  @Timed
  @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
  public ResponseEntity<RestResult> get(@PathVariable Integer projectId) {
    PermissionHelper.checkProjectRead(projectId);
    Project project = projectService.select(projectId);
    return RestResult.success().withResult("project", project).build();
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Object create(@Valid @RequestBody Project project) {
    // 检查create权限
    PermissionHelper.checkGroupProjectCreate(project.getOwnerGroup());

    projectService.insert(project);
    return RestResult.success().withResult("message", "创建project成功").build();
  }

  @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Object delete(@PathVariable Integer projectId) {
    // 检查delete权限
    PermissionHelper.checkProjectDelete(projectId);
    // 检查是否有其它项目在依赖着这个项目
    List<Dependency> depList = dependencyService.selectByDependencyProjectId(projectId);
    if (!depList.isEmpty()) {
      return RestResult.fail().withErrorMessage("项目被依赖，不能删除！");
    }
    // 删除project相关的profile，删除profile里的Config
    List<Profile> profileList = profileService.list(projectId);
    for (Profile profile : profileList) {
      configService.deleteConfigByProfileId(profile.getId());
      profileService.delete(profile.getId());
    }
    projectService.delete(projectId);
    return RestResult.success().withResult("message", "删除project成功").build();
  }

  @RequestMapping(value = "/{projectId}", method = RequestMethod.PATCH)
  public Object update(@PathVariable Integer projectId, @Valid @RequestBody Project project) {
    // 修改Project时，检查是否有Project的write权限
    PermissionHelper.checkProjectWrite(projectId);
    if (project.getOwnerGroup() != null) {
      // 如果是把Project修改owner，检查是否有新group的权限
      PermissionHelper.checkGroupProjectCreate(project.getOwnerGroup());
    }

    // TODO 这里的 PathVariable 是否是必要的？
    projectService.patch(project);
    return RestResult.success().withResult("message", "更新project成功").build();
  }

}
