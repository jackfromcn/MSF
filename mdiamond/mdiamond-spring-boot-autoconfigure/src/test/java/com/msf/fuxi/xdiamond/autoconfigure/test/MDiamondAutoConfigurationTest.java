package com.msf.fuxi.xdiamond.autoconfigure.test;

import com.msf.fuxi.xdiamond.autoconfigure.MDiamondAutoConfiguration;
import com.msf.fuxi.xdiamond.util.MDiamondConfigBeanPostProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lujianchao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = MDiamondAutoConfiguration.class)
public class MDiamondAutoConfigurationTest {


    @Autowired
    private MDiamondConfigBeanPostProcessor mDiamondConfigBeanPostProcessor;

    @Test
    public void xDiamondConfigBeanPostProcessorShouldNotBeNull(){
        Assert.assertNotNull(mDiamondConfigBeanPostProcessor);
    }
}
