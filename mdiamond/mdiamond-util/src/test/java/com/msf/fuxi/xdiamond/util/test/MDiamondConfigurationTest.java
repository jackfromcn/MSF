package com.msf.fuxi.xdiamond.util.test;

import com.msf.fuxi.xdiamond.util.MDiamondConfigBeanPostProcessor;
import com.msf.fuxi.xdiamond.util.MDiamondConfiguration;
import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MDiamondConfiguration.class })
@TestPropertySource("classpath:mdiamond.properties")
public class MDiamondConfigurationTest {


    @Autowired
    private MDiamondConfigProperties mDiamondConfigProperties;

    @Autowired
    private MDiamondConfigBeanPostProcessor mDiamondConfigBeanPostProcessor;

    @Test
    public void mDiamondConfigPropertiesShouldNotBeNull(){
        Assert.assertNotNull(mDiamondConfigProperties);
    }

    public void mDiamondConfigBeanPostProcessorShoutNotBeNull(){
        Assert.assertNotNull(mDiamondConfigBeanPostProcessor);
    }
}
