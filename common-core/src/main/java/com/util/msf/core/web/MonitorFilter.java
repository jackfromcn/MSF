package com.util.msf.core.web;

import com.util.msf.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:43
 * @Description:
 */
@WebFilter
public class MonitorFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(MonitorFilter.class);

    private String[] pattern;

    public MonitorFilter setPattern(String pattern) {
        this.pattern = pattern.split(",");
        return this;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[monitor]-http接口调用统计启动.......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest resq = (HttpServletRequest) servletRequest;
        if (pattern != null && PatternMatchUtils.simpleMatch(pattern, resq.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            long cost = System.currentTimeMillis() - start;
            logger.info("[monitor]-request end, uri={}, cost={}ms, remoteIP={}", resq.getRequestURI(), cost, ServletUtils.IP(resq));
        } catch (Throwable e) {
            long cost = System.currentTimeMillis() - start;
            logger.error("[monitor]-occur exception, uri={}, cost={}ms, args={}, remoteIP={}", resq.getRequestURI(), cost, JsonUtils.toJson(resq.getParameterMap()), ServletUtils.IP(resq), e);
        }
        return;
    }

    @Override
    public void destroy() {

    }

}
