package com.msf.fuxi.xdiamond.util.config;

/**
 * @author liujianchao
 */
public class MDiamondConfigProperties {

    public static final String MDIAMOND_PRIFIX = "mdiamond";

    private String serverHost;

    private Integer serverPort;

    private String groupId;

    private String artifactId;

    private String version;

    private String profile;

    private String secretKey;

    // 启动时，是否打印获取到的配置信息
    private Boolean bPrintConfigWhenBoot = true;
//    // 获取到配置，是否同步到System Properties里
//    private Boolean bSyncToSystemProperties = true;

    // 指数退避的方式增加
    private Boolean bBackOffRetryInterval;
    // 失败重试的次数
    private Integer maxRetryTimes = Integer.MAX_VALUE;
    // 失败重试的时间间隔
    private Integer retryIntervalSeconds = 5;
    // 最大的重试时间间隔
    private Integer maxRetryIntervalSeconds = 2 * 60;

    /**
     * 是否扫描@AllKeyListener和@OneKeyListener注解
     * 默认false，不扫描
     */
    private boolean scanListener = false;

    /**
     * 是否扫描@MdiamondValue注解
     * 默认true
     */
    private boolean scanMdiamond = true;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Boolean getbPrintConfigWhenBoot() {
        return bPrintConfigWhenBoot;
    }

    public void setbPrintConfigWhenBoot(Boolean bPrintConfigWhenBoot) {
        this.bPrintConfigWhenBoot = bPrintConfigWhenBoot;
    }

    public Boolean getbBackOffRetryInterval() {
        return bBackOffRetryInterval;
    }

    public void setbBackOffRetryInterval(Boolean bBackOffRetryInterval) {
        this.bBackOffRetryInterval = bBackOffRetryInterval;
    }

    public Integer getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(Integer maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public Integer getRetryIntervalSeconds() {
        return retryIntervalSeconds;
    }

    public void setRetryIntervalSeconds(Integer retryIntervalSeconds) {
        this.retryIntervalSeconds = retryIntervalSeconds;
    }

    public Integer getMaxRetryIntervalSeconds() {
        return maxRetryIntervalSeconds;
    }

    public void setMaxRetryIntervalSeconds(Integer maxRetryIntervalSeconds) {
        this.maxRetryIntervalSeconds = maxRetryIntervalSeconds;
    }

    public boolean isScanListener() {
        return scanListener;
    }

    public void setScanListener(boolean scanListener) {
        this.scanListener = scanListener;
    }

    public boolean isScanMdiamond() {
        return scanMdiamond;
    }

    public void setScanMdiamond(boolean scanMdiamond) {
        this.scanMdiamond = scanMdiamond;
    }
}
