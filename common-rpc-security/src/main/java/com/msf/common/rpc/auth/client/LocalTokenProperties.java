package com.msf.common.rpc.auth.client;

/**
 * <pre>
 * 子类应当继承该类，并使用@ConfigurationProperties(prefix = "${spring.application.name}")注解，并在yml文件中配置token的值,
 * token的key格式为${spring.application.name}.token，例如：
 *
 *  {@link @ConfigurationProperties}(prefix = "lease-product")
 *  public class LeaseProductTokenProperties extends LocalTokenProperties {
 *  }
 *
 *  application.yml文件定义token为
 *  lease-product.token=123456
 * <pre/>
 */
public class LocalTokenProperties {
    protected String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
