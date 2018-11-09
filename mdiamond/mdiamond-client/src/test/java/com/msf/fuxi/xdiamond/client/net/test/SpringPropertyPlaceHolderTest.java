package com.msf.fuxi.xdiamond.client.net.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-property-placeholder.xml")
public class SpringPropertyPlaceHolderTest {

	@Autowired
	TestBean testBean;

	@Test
	public void test() throws InterruptedException {

		System.err.println(testBean.getName());
		TimeUnit.SECONDS.sleep(600);
	}
}
