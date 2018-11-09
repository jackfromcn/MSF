package com.msf.fuxi.xdiamond.client.net.test;

import com.msf.fuxi.xdiamond.client.annotation.OneKeyListener;
import com.msf.fuxi.xdiamond.client.event.ConfigEvent;
import org.springframework.stereotype.Service;

@Service
public class AnnotationListenerTestBean {
	
	@OneKeyListener(key = "testAnnotation")
	public void testOneKeyListener(ConfigEvent event){
		System.err.println("event :" + event);
	}

}
