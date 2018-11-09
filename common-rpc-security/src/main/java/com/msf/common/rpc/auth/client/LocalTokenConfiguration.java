package com.msf.common.rpc.auth.client;

import org.springframework.beans.factory.annotation.Value;

/**
 * <pre>
 * 子类应该继承该类，并使用@Configuration声明为配置，且用@EnableConfigurationProperties绑定自定义的TokenProperties类，
 * 并声明BasicAuthRequestInterceptor，例如：
 *
 * {@link @Configuration}
 * {@link @EnableConfigurationProperties}(LeaseProductTokenProperties.class)
 * public class LeaseProductTokenConfiguration extends LocalTokenConfiguration {
 *
 *     {@link @Autowired}
 *     private LeaseProductTokenProperties leaseProductTokenProperties;
 *
 *     {@link @Bean}("leaseProductAuthRequestInterceptor")
 *     public BasicAuthRequestInterceptor authRequestInterceptor() {
 *         return new BasicAuthRequestInterceptor(application, leaseProductTokenProperties.getToken());
 *     }
 * }
 *
 * Client类需指明服务配置，例如：
 *
 * {@link @FeignClient}(name = "lease-product", configuration = LeaseProductTokenConfiguration.class)
 * public interface SkuClient extends SkuFacade {
 *
 * }
 * <pre/>
 */
public abstract class LocalTokenConfiguration {

    @Value("${spring.application.name}")
    protected String application;


    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
