package com.msf.spring.cloud;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author wencheng
 * @since 2018/5/18
 */
@Slf4j
public class ServiceDiscovery {
    private static Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);
    private static IRule rule = new ZoneAvoidanceRule();
    private static IPing ping = new NIWSDiscoveryPing();
    private static ServerListFilter<DiscoveryEnabledServer> filter = new DefaultNIWSServerListFilter<>();
    private static ServerListUpdater updater;
    private static EurekaClient eurekaClient;
    private static Provider<EurekaClient> eurekaClientProvider;
    private static volatile Map<String, ILoadBalancer> lbContext = new HashMap<>();

    public static void init(Properties properties) {
        ConfigurationManager.loadProperties(properties);
        MyDataCenterInstanceConfig instanceConfig = new LocalInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);

        eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());

        eurekaClientProvider = () -> eurekaClient;
        updater = new EurekaNotificationServerListUpdater(eurekaClientProvider);
        applicationInfoManager.getInfo().setStatus(InstanceInfo.InstanceStatus.UP);
        log.info("初始化EurekaClient完成！！！！！");

    }

    public static void init(Properties properties, IRule rule) {
        ServiceDiscovery.rule = rule;
        init(properties);

    }

    public static String getServiceUrl(String serviceId) {
        ILoadBalancer loadBalancer = lbContext.get(serviceId);
        if (loadBalancer == null) {
            synchronized (lbContext) {
                loadBalancer = lbContext.get(serviceId);
                if (loadBalancer == null) {
                    loadBalancer = createLoadBalancer(serviceId);
                    lbContext.put(serviceId, loadBalancer);

                }
            }
        }
        Server server = loadBalancer.chooseServer(null);
        if (server == null) {
            logger.error("the serviceId {} no available server url!", serviceId);
            return null;
        }
        return "http://" + server.getHostPort();
    }

    private static ILoadBalancer createLoadBalancer(String serviceId) {
        IClientConfig clientConfig = new DefaultClientConfigImpl();
        clientConfig.loadDefaultValues();
        clientConfig.set(CommonClientConfigKey.DeploymentContextBasedVipAddresses, serviceId);

        ServerList<DiscoveryEnabledServer> serverList = new DiscoveryEnabledNIWSServerList(clientConfig, eurekaClientProvider);

        ZoneAwareLoadBalancer<DiscoveryEnabledServer> loadBalancer = LoadBalancerBuilder.<DiscoveryEnabledServer>newBuilder()
                .withClientConfig(clientConfig)
                .withRule(rule)
                .withPing(ping)
                .withServerListFilter(filter)
                .withDynamicServerList(serverList)
                .withServerListUpdater(updater)
                .buildDynamicServerListLoadBalancer();
        return loadBalancer;
    }


    public static void shutdown() {
        if (eurekaClient != null) {
            eurekaClient.shutdown();
        }
    }
}
