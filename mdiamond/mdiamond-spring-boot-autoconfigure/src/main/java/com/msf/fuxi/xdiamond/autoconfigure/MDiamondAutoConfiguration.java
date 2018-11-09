package com.msf.fuxi.xdiamond.autoconfigure;

import com.msf.fuxi.xdiamond.util.MDiamondConfigBeanPostProcessor;
import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujianchao
 */
@Configuration
@ConditionalOnProperty(value = "mdiamond.enabled", matchIfMissing = true)
public class MDiamondAutoConfiguration {

    @Bean
    @ConfigurationProperties(MDiamondConfigProperties.MDIAMOND_PRIFIX)
    public MDiamondConfigProperties mDiamondConfigProperties(){
        return new MDiamondConfigProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MDiamondConfigBeanPostProcessor mDiamondConfigBeanPostProcessor(MDiamondConfigProperties mDiamondConfigProperties){
        return new MDiamondConfigBeanPostProcessor(mDiamondConfigProperties);
    }
}
