package com.util.msf.monitor.aop;

import com.util.msf.common.utils.SpELUtils;
import com.util.msf.monitor.annotation.MetricsMonitor;
import com.util.msf.monitor.enums.MetricsMonitorEnum;
import com.util.msf.monitor.util.MetricsFactory;
import com.util.msf.rpc.common.Result;
import io.micrometer.core.instrument.Counter;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * @author wencheng
 * @ClassName MetricsMonitorAspect
 * @Description 数据采集切面
 * @date 2018-01-08 上午11:13
 */
@Data
@Accessors(chain = true)
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MetricsMonitorAspect {

    /**
     * 返回码切点
     */
    @Pointcut("execution(public com.util.msf.rpc.common.Result com.spring.cloud..controller..*.*(..))" +
            " || execution(public com.util.msf.rpc.common.Result com.spring.cloud..facade..*.*(..))"
    )
    public void codePointCut() {
    }

    /**
     * 通用切点
     */
    @Pointcut("execution(public * com.spring.cloud..controller..*.*(..))" +
            " || execution(public * com.spring.cloud..facade..*.*(..))" +
            " || execution(public * com.spring.cloud..biz..*.*(..))" +
            " || execution(public * com.spring.cloud..service..*.*(..))" +
            " || execution(public * com.spring.cloud..schedule..*.*(..))" +
            " || execution(public * com.spring.cloud..task..*.*(..))" +
            " || execution(public * com.spring.cloud..client..*.*(..))" +
            " || execution(public * com.spring.cloud..mapper..*.*(..))" +
            " || execution(public * com.spring.cloud..dao..*.*(..))"
    )
    public void commonPointCut() {
    }

    @Around("codePointCut()")
    public Object monitorCode(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result != null && result instanceof Result) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            String className = method.getDeclaringClass().getSimpleName();
            String methodName = className + "." + method.getName();
            Counter counter = MetricsFactory.buildCodeCounter(((Result) result).getCode() + "", className, methodName);
            counter.increment();
            log.debug("数据采集,响应码统计(code_total),code={},counter={}", ((Result) result).getCode(), counter.count());
        }
        return result;
    }


    @Around("commonPointCut()")
    public Object monitorCommon(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginMillis = System.currentTimeMillis();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = className + "." + method.getName();
        MetricsMonitor metricsMonitor = method.getAnnotation(MetricsMonitor.class);
        metricsMonitor = metricsMonitor == null ? method.getDeclaringClass().getAnnotation(MetricsMonitor.class) : metricsMonitor;
        if (metricsMonitor == null) {
            metricsMonitor = commonMetricsMonitor();
        }

        MetricsMonitorEnum typeEnum = metricsMonitor.type();
        String typeName = typeEnum.name();
        String desc = metricsMonitor.desc();
        String url = getSpelValue(metricsMonitor.url(), method, args);
        String topic = getSpelValue(metricsMonitor.topic(), method, args);
        if ((MetricsMonitorEnum.KAFKA_CONSUME.equals(typeEnum) || MetricsMonitorEnum.KAFKA_PRODUCE.equals(typeEnum))
                && StringUtils.isBlank(topic)) {
            log.error("数据采集,{}类型采集参数topic为空,methodName={},topic={}", typeEnum, methodName, topic);
            throw new IllegalArgumentException(typeEnum.name() + "类型采集参数topic不能为空");
        }
        if (MetricsMonitorEnum.HTTP.equals(typeEnum) && StringUtils.isBlank(url)) {
            log.error("数据采集,{}类型采集参数url为空,methodName={},url={}", typeEnum, methodName, url);
            throw new IllegalArgumentException(typeEnum.name() + "类型采集参数url不能为空");
        }
        Object result;
        try {
            result = joinPoint.proceed();
            long costMillis = System.currentTimeMillis() - beginMillis;
            MetricsFactory.buildRequestTimer(typeName, desc, className, methodName, url, topic)
                    .record(costMillis, TimeUnit.MILLISECONDS);
            log.debug("数据采集,type={},methodName={},耗时={}", typeName, methodName, costMillis);
        } catch (Throwable t) {
            Counter counter = MetricsFactory.buildFailCounter(typeName, desc, className, methodName, url, topic);
            counter.increment();
            log.debug("数据采集,type={},methodName={},异常次数={}", typeName, methodName, counter.count());
            throw t;
        }
        return result;
    }

    private MetricsMonitor commonMetricsMonitor() {
        return new MetricsMonitor() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return MetricsMonitor.class;
            }

            @Override
            public MetricsMonitorEnum type() {
                return MetricsMonitorEnum.COMMON;
            }

            @Override
            public String url() {
                return StringUtils.EMPTY;
            }

            @Override
            public String desc() {
                return "一般数据采集";
            }

            @Override
            public String topic() {
                return StringUtils.EMPTY;
            }
        };
    }


    private String getSpelValue(String expression, Method method, Object[] args) {
        if (StringUtils.isBlank(expression)) {
            return StringUtils.EMPTY;
        }
        if (!expression.startsWith("#") && !expression.startsWith("$")) {
            return expression;
        }
        String str = SpELUtils.parseExpression(expression, method, args, String.class);
        return str == null ? StringUtils.EMPTY : str;
    }

}
