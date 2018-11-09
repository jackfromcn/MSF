package com.msf.fuxi.xdiamond.autoconfigure.test;


import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;

/**
 * @author liujianchao
 */
public class ConfigWrapper {

    private MDiamondConfigProperties configProperties;

    public ConfigWrapper(MDiamondConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public MDiamondConfigProperties getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(MDiamondConfigProperties configProperties) {
        this.configProperties = configProperties;
    }
}
