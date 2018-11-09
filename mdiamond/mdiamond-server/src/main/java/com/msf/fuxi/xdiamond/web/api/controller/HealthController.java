package com.msf.fuxi.xdiamond.web.api.controller;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Maps;
import com.msf.fuxi.xdiamond.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping(value = "/api")
public class HealthController {

  @Autowired
  Properties packageProperties;

  Date startTime = new Date();

  /**
   * 获取到health信息
   * 
   * @return
   */
  @RequestMapping(value = "/health", method = RequestMethod.GET)
  @Timed
  public ResponseEntity<RestResult> authenticate() {
    Map<String, Object> process = Maps.newLinkedHashMap();
    process.put("startTime", startTime);
    process.put("jvmStartTime", ManagementFactory.getRuntimeMXBean().getStartTime());
    process.put("runningSeconds", (System.currentTimeMillis() - startTime.getTime()) / 1000);

    return RestResult.success().withResult("package", packageProperties)
        .withResult("process", process).build();
  }

  /**
   * 健康状态检查
   * @return
   */
  @RequestMapping(value = "/healthStatus", method = RequestMethod.GET)
  @Timed
  public ResponseEntity<String> healthStatus(){
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("service is ok!", HttpStatus.OK);
    return responseEntity;
  }
}
