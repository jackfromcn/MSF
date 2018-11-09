package com.msf.spring.cloud;

import com.google.inject.ProvidedBy;
import com.msf.spring.cloud.utils.NetUtils;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.MyDataCenterInstanceConfigProvider;

import javax.inject.Singleton;

/**
 * @author wencheng
 * @since 2018/6/6
 */
@Singleton
@ProvidedBy(MyDataCenterInstanceConfigProvider.class)
public class LocalInstanceConfig extends MyDataCenterInstanceConfig implements EurekaInstanceConfig {
    @Override
    public String getHostName(boolean refresh) {
        return NetUtils.getLocalHost();
    }
}
