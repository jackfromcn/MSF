package com.msf.fuxi.xdiamond.autoconfigure.test;

import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujianchao
 */
@Configuration
public class ConfigConfiguration {


    @Bean
    @ConfigurationProperties(MDiamondConfigProperties.MDIAMOND_PRIFIX)
    public MDiamondConfigProperties mDiamondConfigProperties(){
        return new MDiamondConfigProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConfigWrapper configWrapper(MDiamondConfigProperties mDiamondConfigProperties){
        return new ConfigWrapper(mDiamondConfigProperties);
    }
}
