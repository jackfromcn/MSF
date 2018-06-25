package com.util.msf.log.filter;

import com.alibaba.dubbo.rpc.*;
import com.msf.core.common.utils.JsonUtils;
import com.util.msf.log.config.ApplicationContextSupport;
import com.util.msf.log.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

import java.util.Map;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:24
 * @Description:
 */
public class TraceDubboProviderFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TraceDubboProviderFilter.class);


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Tracer tracer = ApplicationContextSupport.TraceInnerClass.tracer;
        if (tracer == null) {
            return invoker.invoke(invocation);
        }

        Span currentSpan = null, fromSpan = null;
        String spanJson = RpcContext.getContext().getAttachment(CommonConstant.SPAN_JSON);
        String baggageJson = RpcContext.getContext().getAttachment(CommonConstant.BAGGAGE_JSON);

        if (StringUtils.isNotBlank(spanJson)) {
            fromSpan = JsonUtils.fromJson(spanJson, Span.class);
        }
        currentSpan = tracer.getCurrentSpan();
        if (currentSpan == null) {
            currentSpan = tracer.createSpan("customer span", fromSpan);
        }
        if (StringUtils.isNotBlank(baggageJson)) {
            Map<String, String> baggageMap = JsonUtils.fromJson(baggageJson, Map.class);
            if (baggageMap != null) {
                for (Map.Entry<String, String> entry : baggageMap.entrySet()) {
                    currentSpan.setBaggageItem(entry.getKey(), entry.getValue());
                    MDC.put(entry.getKey(), entry.getValue());
                }
            }
        }

        try {
            return invoker.invoke(invocation);
        } finally {
            if (currentSpan != null) {
                for (Map.Entry<String, String> entry : currentSpan.baggageItems()) {
                    MDC.remove(entry.getKey());
                }
                MDC.remove(Span.SPAN_ID_NAME);
                MDC.remove(Span.TRACE_ID_NAME);
                MDC.remove(Span.SPAN_EXPORT_NAME);
            }

            tracer.close(currentSpan);
        }
    }
}
