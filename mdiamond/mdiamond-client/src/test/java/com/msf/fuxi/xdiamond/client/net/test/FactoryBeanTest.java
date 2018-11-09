package com.msf.fuxi.xdiamond.client.net.test;

import org.springframework.beans.factory.FactoryBean;

import java.util.Properties;

public class FactoryBeanTest implements FactoryBean<Properties> {

	Properties properties = new Properties();

	@Override
	public Properties getObject() throws Exception {
		return properties;
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
