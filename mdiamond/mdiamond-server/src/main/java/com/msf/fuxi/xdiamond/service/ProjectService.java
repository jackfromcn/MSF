package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.constants.Access;
import com.msf.fuxi.xdiamond.domain.Profile;
import com.msf.fuxi.xdiamond.domain.Project;
import com.msf.fuxi.xdiamond.domain.ProjectExample;
import com.msf.fuxi.xdiamond.persistence.ProjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {
  static private final Logger logger = LoggerFactory.getLogger(ProjectService.class);
  @Autowired
  DependencyService dependencyService;
  @Autowired
  ConfigService configService;

  @Autowired
  ProjectMapper projectMapper;
  @Autowired
  ProfileService profileService;

  static final String Base_Profile_Name = "base";

  public List<Project> list() {
    return projectMapper.selectByExample(new ProjectExample());
  }

  // TODO 增加相应的Role，还有Role和Permission相关的
  public int insert(Project project) {
    int result = projectMapper.insert(project);
    Profile profile = new Profile();
    profile.setProjectId(project.getId());
    profile.setName("base");
    profile.setAccess(Access.MASTER);
    profileService.insert(profile);
    profile.setName("test");
    profile.setAccess(Access.DEVELOPER);
    profileService.insert(profile);
    profile.setName("dev");
    profile.setAccess(Access.DEVELOPER);
    profileService.insert(profile);
    profile.setName("product");
    profile.setAccess(Access.MASTER);
    //为product这个profile生成SecretKey
    profile.setSecretKey(RandomStringUtils.randomAlphanumeric(16));
    profileService.insert(profile);
    return result;
  }

  public void patch(Project project) {
    projectMapper.updateByPrimaryKeySelective(project);
  }

  public void put(Project project) {
    projectMapper.updateByPrimaryKey(project);
  }

  public Project select(int id) {
    return projectMapper.selectByPrimaryKey(id);
  }

  public Project select(String groupId, String artifactId, String version) {
    ProjectExample example = new ProjectExample();
    example.createCriteria().andGroupIdEqualTo(groupId).andArtifactIdEqualTo(artifactId)
        .andVersionEqualTo(version);
    List<Project> list = projectMapper.selectByExample(example);
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }

  public List<Project> selectProjectByOwnerGroup(int ownerGroup) {
    ProjectExample example = new ProjectExample();
    example.createCriteria().andOwnerGroupEqualTo(ownerGroup);
    return projectMapper.selectByExample(example);
  }

  public List<Project> selectPublicProject() {
    ProjectExample example = new ProjectExample();
    example.createCriteria().andBPublicEqualTo(true);
    return projectMapper.selectByExample(example);
  }

  @Transactional
  public void delete(int id) {
    // TODO 删除所有的profile，config, dependency，还要检查这个项目有没有被其它的项目依赖到
    // 目前检查的逻辑在Controller里
    projectMapper.deleteByPrimaryKey(id);
  }
}
