package com.msf.fuxi.xdiamond.domain.ldap;

import com.google.common.collect.Lists;

import java.util.List;

public class LdapGroup {

  String cn;
  List<LdapUser> users = Lists.newLinkedList();

  public String getCn() {
    return cn;
  }

  public void setCn(String cn) {
    this.cn = cn;
  }

  public List<LdapUser> getUsers() {
    return users;
  }

  public void setUsers(List<LdapUser> users) {
    this.users = users;
  }



}
