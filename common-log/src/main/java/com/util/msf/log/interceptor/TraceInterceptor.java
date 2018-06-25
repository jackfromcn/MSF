package com.util.msf.log.interceptor;

import com.msf.core.common.helper.WebHelper;
import com.util.msf.log.constant.TraceKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:22
 * @Description: 日志链路追踪拦截器
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);

    @Autowired(required = false)
    private Tracer tracer;

    public TraceInterceptor() {
    }

    public TraceInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (tracer == null) {
            return true;
        }
        Span span = tracer.getCurrentSpan();
        if (span == null) {
            return true;
        }

        Long userId = WebHelper.getUserId();
        if (userId != null) {
            //防止覆盖
            if (StringUtils.isBlank(span.getBaggageItem(TraceKey.M_TRACE_UID))) {
                span.setBaggageItem(TraceKey.M_TRACE_UID, userId.toString());
                span.setBaggageItem(TraceKey.M_TRACE_SCENE, request.getRequestURI());
            }
        }
        for (Map.Entry<String, String> entry : span.baggageItems()) {
            MDC.put(entry.getKey(), entry.getValue());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (tracer == null) {
            return;
        }
        Span span = tracer.getCurrentSpan();
        if (span != null) {
            for (Map.Entry<String, String> entry : span.baggageItems()) {
                MDC.remove(entry.getKey());
            }
        }
    }
}
