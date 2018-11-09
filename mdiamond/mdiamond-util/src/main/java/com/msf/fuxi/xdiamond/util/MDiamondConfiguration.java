package com.msf.fuxi.xdiamond.util;

import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;
import com.msf.fuxi.xdiamond.util.config.MDiamondProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;


/**
 * @author liujianchao
 */
@Configuration
public class MDiamondConfiguration {

    @Autowired
    private Environment evn;

    @Bean
    public MDiamondConfigProperties mDiamondConfigProperties(){
        MDiamondConfigProperties configProperties = new MDiamondConfigProperties();
        configProperties.setServerHost(
                evn.getRequiredProperty(MDiamondProperties.PROP_SERVER_HOST));
        configProperties.setServerPort(
                Integer.parseInt(evn.getRequiredProperty(MDiamondProperties.PROP_SERVER_PORT)));

        configProperties.setGroupId(
                evn.getRequiredProperty(MDiamondProperties.PROP_GROUP_ID));
        configProperties.setArtifactId(
                evn.getRequiredProperty(MDiamondProperties.PROP_ARTIFACT_ID));
        configProperties.setVersion(
                evn.getRequiredProperty(MDiamondProperties.PROP_VERSION));
        configProperties.setProfile(
                evn.getRequiredProperty(MDiamondProperties.PROP_PROFILE));
        configProperties.setSecretKey(evn.getProperty(MDiamondProperties.PROP_SECRET_KEY));
        String  bPrintConfigWhenBoot = evn.getProperty(MDiamondProperties.PROP_B_PRINT_CONFIG_WHEN_BOOT);
        if (!StringUtils.isEmpty(bPrintConfigWhenBoot)){
            configProperties.setbPrintConfigWhenBoot(Boolean.parseBoolean(bPrintConfigWhenBoot));
        }
        String  bBackOffRetryInterval = evn.getProperty(MDiamondProperties.PROP_B_BACK_OFF_RETRY_INTERVAL);
        if (!StringUtils.isEmpty(bBackOffRetryInterval)){
            configProperties.setbBackOffRetryInterval(Boolean.parseBoolean(bBackOffRetryInterval));
        }
        String  maxRetryTimes = evn.getProperty(MDiamondProperties.PROP_MAX_RETRY_TIMES);
        if (!StringUtils.isEmpty(maxRetryTimes)){
            configProperties.setMaxRetryTimes(Integer.parseInt(maxRetryTimes));
        }
        String  retryIntervalSeconds = evn.getProperty(MDiamondProperties.PROP_RETRY_INTERVAL_SECONDS);
        if (!StringUtils.isEmpty(retryIntervalSeconds)){
            configProperties.setRetryIntervalSeconds(Integer.parseInt(retryIntervalSeconds));
        }
        String  maxRetryIntervalSeconds = evn.getProperty(MDiamondProperties.PROP_MAX_RETRY_INTERVAL_SECONDS);
        if (!StringUtils.isEmpty(maxRetryIntervalSeconds)){
            configProperties.setMaxRetryIntervalSeconds(Integer.parseInt(maxRetryIntervalSeconds));
        }
        String  scanListener = evn.getProperty(MDiamondProperties.PROP_SCAN_LISTENER);
        if (!StringUtils.isEmpty(scanListener)){
            configProperties.setScanListener(Boolean.parseBoolean(scanListener));
        }
        String  scanMdiamond = evn.getProperty(MDiamondProperties.PROP_SCAN_MDIAMOND);
        if (!StringUtils.isEmpty(scanMdiamond)){
            configProperties.setScanMdiamond(Boolean.parseBoolean(scanMdiamond));
        }

        return configProperties;
    }

    @Bean
    public MDiamondConfigBeanPostProcessor mDiamondConfigBeanPostProcessor(MDiamondConfigProperties mDiamondConfigProperties){
        return new MDiamondConfigBeanPostProcessor(mDiamondConfigProperties);
    }
}
