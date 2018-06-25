package com.util.msf.log.aop;

import com.util.msf.log.annotation.MTrace;
import com.util.msf.log.constant.TraceKey;
import com.util.msf.log.utils.SpelParser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:06
 * @Description: 日志链路追踪切面
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
public class TraceAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceAspect.class);

    @Autowired(required = false)
    private Tracer tracer;

    public TraceAspect() {
    }

    public TraceAspect(Tracer tracer) {
        this.tracer = tracer;
    }

    @Pointcut("@annotation(com.mljr.msf.log.annotation.MTrace)")
    public void tracePointCut() {
        logger.info("服务调用链路日志追溯切入");
    }

    @Around(value = "tracePointCut()")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        if (tracer == null) {
            return joinPoint.proceed();
        }
        Span span = tracer.getCurrentSpan();
        if (span == null) {
            return joinPoint.proceed();
        }
        //防止覆盖
        if (StringUtils.isNotBlank(span.getBaggageItem(TraceKey.M_TRACE_TID))) {
            return joinPoint.proceed();
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        MTrace mTrace = method.getAnnotation(MTrace.class);
        String expression = mTrace.value();
        if (StringUtils.isBlank(expression)) {
            return joinPoint.proceed();
        }
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return joinPoint.proceed();
        }
        String value = null;
        try {
            value = SpelParser.parseExpression(expression, method, args, String.class);
        } catch (Exception e) {
        }

        if (StringUtils.isNotBlank(value)) {
            span.setBaggageItem(TraceKey.M_TRACE_TID, value);
        }
        for (Map.Entry<String, String> entry : span.baggageItems()) {
            MDC.put(entry.getKey(), entry.getValue());
        }
        try {
            return joinPoint.proceed();
        } finally {
            for (Map.Entry<String, String> entry : span.baggageItems()) {
                MDC.remove(entry.getKey());
            }
        }
    }
}
