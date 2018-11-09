package com.msf.fuxi.xdiamond.service;

import com.msf.fuxi.xdiamond.domain.Group;
import com.msf.fuxi.xdiamond.domain.GroupExample;
import com.msf.fuxi.xdiamond.domain.UserGroup;
import com.msf.fuxi.xdiamond.persistence.GroupMapper;
import com.msf.fuxi.xdiamond.persistence.GroupRoleMapper;
import com.msf.fuxi.xdiamond.persistence.UserGroupMapper;
import com.msf.fuxi.xdiamond.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

  @Autowired
  UserMapper userMapper;
  @Autowired
  GroupMapper groupMapper;
  @Autowired
  UserGroupMapper userGroupMapper;
  @Autowired
  GroupRoleMapper groupRoleMapper;

  public List<Group> list() {
    return groupMapper.selectByExample(new GroupExample());
  }

  public int insert(Group group) {
    return groupMapper.insert(group);
  }

  public int delete(int id) {
    List<UserGroup> userGroups = userGroupMapper.selectByGroupId(id);
    if (userGroups != null && userGroups.size() > 0) {
      throw new IllegalArgumentException("该group下还有用户，无法删除！");
    }
    int result = groupMapper.deleteByPrimaryKey(id);
    groupRoleMapper.deleteByGroupId(id);
    return result;
  }

  public int patch(Group group) {
    return groupMapper.updateByPrimaryKeySelective(group);
  }

  public Group select(int id) {
    return groupMapper.selectByPrimaryKey(id);
  }

  public Group selectByName(String name) {
    GroupExample example = new GroupExample();
    example.createCriteria().andNameEqualTo(name);
    List<Group> list = groupMapper.selectByExample(example);
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

}
