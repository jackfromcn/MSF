package com.msf.fuxi.xdiamond.autoconfigure.test;

import com.msf.fuxi.xdiamond.autoconfigure.MDiamondAutoConfiguration;
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
@SpringBootTest(classes = {MDiamondAutoConfiguration.class, AnnotationListenerBean.class})
public class AnnotationListenerTest {

    @Autowired
    private AnnotationListenerBean listenerBean;

    @Test
    public void listenerBeanShouldNotBeNull(){
        Assert.assertNotNull(listenerBean);
    }

    @Test
    public void validMDiamondValue(){
        try {
            for (int i = 0; i < 5; i++){
                listenerBean.writeOutScanValue();
                Thread.sleep(5 * 1000);
            }

            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
