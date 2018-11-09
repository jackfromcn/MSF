package com.msf.fuxi.xdiamond.autoconfigure.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liujianchao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = ConfigConfiguration.class)
public class MDiamondConfigPropertiesWiredTest {

    @Autowired
    private ConfigWrapper configWrapper;

    @Test
    public void ConfigWrapperShouldNotBeNull(){
        Assert.assertNotNull(configWrapper);
    }
}
