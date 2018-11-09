package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.msf.fuxi.xdiamond.web.RestResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api")
public class SystemPropertyController {
  @Timed
  @RequestMapping(value = "/systemproperties", method = RequestMethod.GET)
  public ResponseEntity<RestResult> threaddump() {
    return RestResult.success().withResult("properties", System.getProperties()).build();
  }
}
