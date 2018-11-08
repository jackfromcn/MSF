package com.util.msf.monitor.enums;

/**
 * @author wencheng
 * @ClassName MetricsMonitorEnum
 * @Description 数据采集类型枚举
 * @date 2017-11-03 上午12:04
 */
public enum MetricsMonitorEnum {
    COMMON("一般数据采集"),

    HTTP("外部http数据采集"),

    RPC("外部rpc数据采集"),

    KAFKA_PRODUCE("外部mq发送数据采集"),

    KAFKA_CONSUME("外部mq消费数据采集"),;


    private String desc;

    MetricsMonitorEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
