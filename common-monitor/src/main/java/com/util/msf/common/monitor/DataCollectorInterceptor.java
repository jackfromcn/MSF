package com.util.msf.common.monitor;

import com.util.msf.common.annotation.AopDataCollect;
import com.util.msf.common.enumns.DataCollectorTypeEnum;
import com.util.msf.common.utils.SpELUtils;
import com.util.msf.core.web.ServletUtils;
import com.util.msf.rpc.common.Result;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author wencheng
 * @ClassName DataCollectorInterceptor
 * @Description 数据采集拦截器
 * @date 2017-11-02 上午11:13
 */
public class DataCollectorInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorInterceptor.class);

    private DataCollectorLabel label;

    private boolean toCollect;


    public DataCollectorInterceptor setToCollect(boolean toCollect) {
        this.toCollect = toCollect;
        return this;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!toCollect) {
            return invocation.proceed();
        }

        AopDataCollect dataCollect = invocation.getMethod().getAnnotation(AopDataCollect.class);
        dataCollect = dataCollect == null ? invocation.getMethod().getDeclaringClass().getAnnotation(AopDataCollect.class) : dataCollect;
        if (dataCollect == null) {
            return invocation.proceed();
        }

        Object[] args = invocation.getArguments();
        Method method = invocation.getMethod();

        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        String methodName = className + "." + invocation.getMethod().getName();
        DataCollectorTypeEnum typeEnum = dataCollect.type();

        String identifyKey = getSpelValue(dataCollect.identifyKey(), method, args);
        Object result;
        switch (typeEnum) {
            case INNER_HTTP:
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
                String[] labelValues = new String[]{label.getApp(), label.getInstance(), label.getEnv(), identifyKey, request.getRequestURI(), ServletUtils.IP(request)};
                Gauge.Timer gaugeTimer = HTTP_REQUEST_COST_SECONDS.labels(labelValues).startTimer();
                Histogram.Timer histogramTimerTimer = HTTP_REQUEST_DURATION_SECONDS.labels(labelValues).startTimer();
                try {
                    result = invocation.proceed();
                    gaugeTimer.setDuration();
                    histogramTimerTimer.observeDuration();
                } catch (Throwable t) {
                    HTTP_REQUEST_EXCEPTION_COUNT.labels(labelValues).inc();
                    throw t;
                }
                break;
            case INNER_CODE:
                result = invocation.proceed();
                if (result instanceof Result) {
                    if (!((Result) result).isSucceed()) {
                        labelValues = new String[]{label.getApp(), label.getInstance(), label.getEnv(), identifyKey, ((Result) result).getCode() + "", className, methodName};
                        RESPONSE_CODE_COUNT.labels(labelValues).inc();
                    }
                }

                break;
            case EXTERNAL_HTTP:
            case EXTERNAL_RPC:
            case EXTERNAL_MQ_SEND:
            case EXTERNAL_MQ_RECEIVE:
                String typeName = typeEnum.name();
                String url = getSpelValue(dataCollect.url(), method, args);
                if (EXTERNAL_HTTP == typeEnum && StringUtils.isBlank(url)) {
                    LOGGER.error("{}类型采集参数url为空,methodName={}", typeEnum, methodName);
                    throw new IllegalAccessException(typeEnum.name() + "类型采集参数url不能为空");
                }
                String topic = getSpelValue(dataCollect.topic(), method, args);
                if (EXTERNAL_MQ_SEND == typeEnum || EXTERNAL_MQ_RECEIVE == typeEnum) {
                    if (StringUtils.isBlank(topic) || StringUtils.isBlank(identifyKey)) {
                        LOGGER.error("{}类型采集参数topic或identifyKey为空,methodName={}", typeEnum, methodName);
                        throw new IllegalAccessException(typeEnum.name() + "类型采集参数topic或identifyKey不能为空");
                    }
                }
                labelValues = new String[]{label.getApp(), label.getInstance(), label.getEnv(), identifyKey, typeName, url, className, methodName, topic};
                Gauge.Timer gaugeTimer2 = RPC_REQUEST_COST_SECONDS.labels(labelValues).startTimer();
                Histogram.Timer histogramTimer2 = RPC_REQUEST_DURATION_SECONDS.labels(labelValues).startTimer();
                try {
                    result = invocation.proceed();
                    gaugeTimer2.setDuration();
                    histogramTimer2.observeDuration();
                    if (EXTERNAL_MQ_SEND == typeEnum) {
                        RPC_MQ_SEND_RECEIVE_STA.labels(labelValues).inc();
                    } else if (EXTERNAL_MQ_RECEIVE == typeEnum) {
                        RPC_MQ_SEND_RECEIVE_STA.labels(labelValues).dec();
                    }
                } catch (Throwable t) {
                    RPC_REQUEST_FAIL_COUNT.labels(labelValues).inc();
                    throw t;
                }

                break;
            default:
                result = invocation.proceed();
                break;
        }

        return result;
    }

    private String getSpelValue(String expression, Method method, Object[] args) {
        String str = SpELUtils.parseExpression(expression, method, args, String.class);
        return str == null ? "" : str;
    }

    public DataCollectorLabel getLabel() {
        return label;
    }

    public void setLabel(DataCollectorLabel label) {
        this.label = label;
    }
}
