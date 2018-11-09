package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.msf.fuxi.xdiamond.domain.ldap.LdapGroup;
import com.msf.fuxi.xdiamond.service.LdapService;
import com.msf.fuxi.xdiamond.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("api")
public class LdapController {

  @Autowired
  LdapService ldapService;

  @RequestMapping(value = "/ldap/groups", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<RestResult> getAll() {
    List<LdapGroup> ldapGroups = ldapService.listGroups();
    return RestResult.success().withResult("ldapGroups", ldapGroups).build();
  }

  @RequestMapping(value = "/ldap/groups", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  @Timed
  public ResponseEntity<RestResult> addGroupAndUser(@Valid @RequestBody LdapGroup ldapGroup) {
    ldapService.addGroupAndUser(ldapGroup);
    return RestResult.success().withResult("message", "同步成功!").build();
  }

}
