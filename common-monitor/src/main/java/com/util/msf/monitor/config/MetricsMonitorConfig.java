package com.util.msf.monitor.config;

import com.util.msf.monitor.aop.MetricsMonitorAspect;
import com.util.msf.monitor.bean.MetricsMonitorLabel;
import com.util.msf.monitor.filter.MetricsMonitorFilter;
import com.util.msf.monitor.util.MetricsFactory;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.spring.autoconfigure.MeterRegistryConfigurer;
import io.micrometer.spring.web.servlet.WebMvcTags;
import io.micrometer.spring.web.servlet.WebMvcTagsProvider;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author wencheng
 * @ClassName MicrometerConfig
 * @Description MicrometerConfig
 * @date 2017-11-17 上午11:20
 */
@Configuration
@ConditionalOnProperty(value = "monitor.enabled", matchIfMissing = true)
@Data
public class MetricsMonitorConfig {

    private static final String APP = "app";
    private static final String ENV = "env";
    private static final String INSTANCE = "app_instance";

    @Bean
    public MetricsMonitorLabel metricsMonitorLabel() {
        return new MetricsMonitorLabel();
    }

    @Bean
    @ConditionalOnClass(MeterRegistry.class)
    public MetricsFactory metricsFactory(MeterRegistry meterRegistry) {
        return MetricsFactory.init(meterRegistry);
    }

    @Bean("metricsMonitorFilter")
    public FilterRegistrationBean metricsMonitorFilter() {
        MetricsMonitorFilter filter = new MetricsMonitorFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    @ConditionalOnClass(name = "org.aspectj.lang.ProceedingJoinPoint")
    @ConditionalOnProperty(value = "spring.aop.enabled", matchIfMissing = true)
    public MetricsMonitorAspect metricsMonitorAspect() {
        return new MetricsMonitorAspect();
    }


    @Bean
    MeterRegistryConfigurer configurer() {
        MetricsMonitorLabel metricsMonitorLabel = metricsMonitorLabel();
        return registry -> registry.config().commonTags(Arrays.asList(Tag.of(APP, metricsMonitorLabel.getApp()),
                Tag.of(ENV, metricsMonitorLabel.getEnv()), Tag.of(INSTANCE, metricsMonitorLabel.getInstance())));
    }


    @Bean
    @Primary
    WebMvcTagsProvider webMvcTagsProvider() {
        return new WebMvcTagsProvider() {
            @Override
            public Iterable<Tag> httpLongRequestTags(HttpServletRequest request, Object handler) {
                return Arrays.asList(WebMvcTags.method(request), WebMvcTags.uri(request, null));
            }

            @Override
            public Iterable<Tag> httpRequestTags(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
                return Arrays.asList(WebMvcTags.method(request), WebMvcTags.uri(request, response),
                        WebMvcTags.exception(ex), WebMvcTags.status(response));
            }
        };
    }


}
