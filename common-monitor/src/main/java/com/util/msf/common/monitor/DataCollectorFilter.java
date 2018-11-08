package com.util.msf.common.monitor;

import com.util.msf.core.web.ServletUtils;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author wencheng
 * @ClassName DataCollectorFilter
 * @Description 数据采集过滤器
 * @date 2017-11-02 上午11:11
 */
public class DataCollectorFilter implements Filter {
    private DataCollectorLabel label;

    private boolean toCollect;

    private String[] filterPattern;

    public DataCollectorFilter setToCollect(boolean toCollect) {
        this.toCollect = toCollect;
        return this;
    }

    public DataCollectorFilter setFilterPattern(String filterPattern) {
        this.filterPattern = filterPattern.split(",");
        return this;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest resq = (HttpServletRequest) request;
        if (!toCollect || (filterPattern != null && PatternMatchUtils.simpleMatch(filterPattern, resq.getRequestURI()))) {
            chain.doFilter(request, response);
            return;
        }

        String[] labelValues = {label.getApp(), label.getInstance(), label.getEnv(), "", resq.getRequestURI(), ServletUtils.IP(resq)};
        Gauge.Timer gaugeTimer = HTTP_REQUEST_COST_SECONDS.labels(labelValues).startTimer();
        Histogram.Timer histogramTimer = HTTP_REQUEST_DURATION_SECONDS.labels(labelValues).startTimer();
        try {
            chain.doFilter(request, response);
            //仅统计非异常调用耗时
            gaugeTimer.setDuration();
            histogramTimer.observeDuration();
        } catch (Throwable t) {
            HTTP_REQUEST_EXCEPTION_COUNT.labels(labelValues).inc();
            throw t;
        }
    }

    @Override
    public void destroy() {

    }

    public DataCollectorLabel getLabel() {
        return label;
    }

    public void setLabel(DataCollectorLabel label) {
        this.label = label;
    }

}
