package com.util.msf.monitor.filter;

import com.util.msf.monitor.util.MetricsFactory;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wencheng
 * @ClassName MetricsMonitorFilter
 * @Description 数据采集过滤器
 * @date 2017-11-02 上午11:11
 */
@Data
@Accessors(chain = true)
@Slf4j
public class MetricsMonitorFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long oldMillis = System.currentTimeMillis();
        chain.doFilter(request, response);
        long costMillis = System.currentTimeMillis() - oldMillis;
        MetricsFactory.buildHttpCost(costMillis);
        log.debug("数据采集,http请求耗时(http.server.requests.cost.millis)={}", costMillis);
    }

    @Override
    public void destroy() {

    }


}
