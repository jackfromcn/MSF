package com.msf.fuxi.xdiamond.autoconfigure.test;

import com.msf.fuxi.xdiamond.client.annotation.AllKeyListener;
import com.msf.fuxi.xdiamond.client.annotation.OneKeyListener;
import com.msf.fuxi.xdiamond.client.event.ConfigEvent;
import com.msf.fuxi.xdiamond.util.annotation.MDiamondValue;
import org.springframework.stereotype.Service;

/**
 * @author liujianchao
 */
@Service
public class AnnotationListenerBean {

    @MDiamondValue(key = "scanAnnotation")
    private Boolean scan;

    @MDiamondValue(key = "scanAnnotation")
    private Boolean scanThen;

    @MDiamondValue(key = "applicationName", defaultValue = "demoApp")
    private String appName;

    @OneKeyListener(key = "scanAnnotation")
    public void testOneKeyListener(ConfigEvent event){
        System.err.println("OneKeyListener event :" + event);
    }

    @AllKeyListener
    public void testAllKeyListener(ConfigEvent event){
        System.err.println("AllKeyListener event :" + event);
    }

    public void writeOutScanValue(){
        System.out.println("san value is : " + scan + ", scanThen is : " + scanThen + ", appName is : " + appName);
    }
}
