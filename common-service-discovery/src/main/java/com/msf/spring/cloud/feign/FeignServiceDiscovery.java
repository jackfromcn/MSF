package com.msf.spring.cloud.feign;

import com.msf.spring.cloud.ServiceDiscovery;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author wencheng
 * @since 2018/5/25
 */
@Slf4j
public class FeignServiceDiscovery {
    private Feign.Builder builder;


    public FeignServiceDiscovery(Feign.Builder builder) {
        this.builder = builder;
    }

    public static FeignServiceDiscovery of(Feign.Builder builder) {
        return new FeignServiceDiscovery(builder);
    }


    public <T> T client(Class<T> apiType, Servicable service) {
        return client(apiType, service.id());
    }

    public <T> T client(Class<T> apiType, String serviceId) {

        String availableServerUrl = ServiceDiscovery.getServiceUrl(serviceId);

        if (StringUtils.isEmpty(availableServerUrl)) {
            log.error("服务发现异常，服务【{}】没有可用服务地址，如果使用该服务，请通知服务提供方将服务注册到注册中心！", serviceId);
            return null;
        }
        return builder.target(apiType, availableServerUrl);
    }
}
