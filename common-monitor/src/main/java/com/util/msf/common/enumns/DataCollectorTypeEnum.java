package com.util.msf.common.enumns;

import io.prometheus.client.Collector;
import io.prometheus.client.Gauge;

/**
 * @author wencheng
 * @ClassName DataCollectorTypeEnum
 * @Description 数据采集类型枚举
 * @date 2017-11-03 上午12:04
 */
public enum DataCollectorTypeEnum {
    INNER_HTTP("内部http数据采集"),

    INNER_CODE("内部code数据采集"),

    EXTERNAL_HTTP("外部http数据采集"),

    EXTERNAL_RPC("外部rpc数据采集"),

    EXTERNAL_MQ_SEND("mq发送数据采集"),

    EXTERNAL_MQ_RECEIVE("mq接收数据采集"),


    ;


    private String desc;

    DataCollectorTypeEnum(String desc) {
        this.desc = desc;
    }

//
//
//    HTTP_REQUEST_COUNT_MILLISECONDS("", "", Gauge.build().namespace("")
//            .name("http_request_cost_milliseconds")
//            .help("http请求耗时(Gauge)")
//            .labelNames()
//            .register()){
//        @Override
//        public Collector getCollector() {
//            return HTTP_REQUEST_COST_MILLISECONDS;
//        }
//    };
//    public static String a = "a";
//    static final Gauge HTTP_REQUEST_COST_MILLISECONDS = Gauge.build().namespace("")
//            .name("http_request_cost_milliseconds")
//            .help("http请求耗时(Gauge)")
//            .labelNames("")
//            .register();
//
//    DataCollectorTypeEnum(String desc, String labelNames, Collector collector) {
//        this.desc = desc;
//        this.labelNames = labelNames;
//        this.collector = collector;
//    }
//
//    private String desc;
//
//    private String labelNames;
//
//    private Collector collector;
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public String getLabelNames() {
//        return labelNames;
//    }
//
//    public void setLabelNames(String labelNames) {
//        this.labelNames = labelNames;
//    }
//
//    public Collector getCollector() {
//        return collector;
//    }
//
//    public void setCollector(Collector collector) {
//        this.collector = collector;
//    }
//
//    public static void main(String[] args) {
//        DataCollectorTypeEnum a1 = DataCollectorTypeEnum.HTTP_REQUEST_COUNT_MILLISECONDS;
//        DataCollectorTypeEnum a2 = DataCollectorTypeEnum.HTTP_REQUEST_COUNT_MILLISECONDS;
//        System.out.println(a1 == a2);
//        System.out.println(a1.collector == a2.collector);
//        System.out.println(a1.getCollector() == a2.getCollector());
//    }
}
