package com.util.msf.log.filter;

import com.alibaba.dubbo.rpc.*;
import com.msf.core.common.utils.JsonUtils;
import com.util.msf.log.config.ApplicationContextSupport;
import com.util.msf.log.constant.CommonConstant;
import com.util.msf.log.constant.TraceKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

import java.util.Map;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:19
 * @Description:
 */
public class TraceDubboConsumerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TraceDubboConsumerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Tracer tracer = ApplicationContextSupport.TraceInnerClass.tracer;
        if (tracer == null) {
            return invoker.invoke(invocation);
        }

        boolean isCreate = false;
        Span span = tracer.getCurrentSpan();
        if (span == null) {
            span = tracer.createSpan("customer span");
            isCreate = true;
        }
        if (StringUtils.isBlank(span.getBaggageItem(TraceKey.M_TRACE_SCENE))) {
            Class cl = invoker.getInterface();
            String className = cl.getSimpleName() + "." + invocation.getMethodName();
            span.setBaggageItem(TraceKey.M_TRACE_SCENE, className);
            MDC.put(TraceKey.M_TRACE_SCENE, className);
        }
        String spanJson = JsonUtils.toJson(tracer.getCurrentSpan());
        String baggageJson = JsonUtils.toJson(tracer.getCurrentSpan().getBaggage());
        RpcContext.getContext()
                .setAttachment(CommonConstant.SPAN_JSON, spanJson)
                .setAttachment(CommonConstant.BAGGAGE_JSON, baggageJson);
        try {
            return invoker.invoke(invocation);
        } finally {
            //如果是在consumer拦截器中创建的span，需要自己close掉
            if (isCreate) {
                for (Map.Entry<String, String> entry : span.baggageItems()) {
                    MDC.remove(entry.getKey());
                }
                tracer.close(span);
            }
        }
    }

}
