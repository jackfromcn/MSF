package com.msf.spring.cloud.test;

import com.msf.spring.cloud.ServiceDiscovery;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author wencheng
 * @since 2018/5/18
 */
public class ServiceDiscoveryTest {


    @Test
    public void getServiceUrl() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("eureka-client.properties"));
        ServiceDiscovery.init(properties);
        System.out.println(ServiceDiscovery.getServiceUrl(ServiceId.CONFIG_CENTER.id()));
    }


}
