package com.msf.common.rpc.auth.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @author iwang
 * @since 2018/3/30
 */
public class LocalAccessInteceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LocalAccessInteceptor.class);


    private String token;

    public LocalAccessInteceptor(String token) {
        this.token = token;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.trim().equals("")) {
            logger.error("服务调用授权校验，Authorization为空，uri={}", request.getRequestURI());
            return false;
        }
        String headValue = auth.split(" ")[1];
        if (headValue == null || headValue.trim().equals("")) {
            logger.error("服务调用授权校验，token为空，uri={}", request.getRequestURI());
            return false;
        }
        String token[] = new String(Base64.getDecoder().decode(headValue)).split(":");
        if (this.token.equals(token[1])) {
            return super.preHandle(request, response, handler);
        }
        logger.error("服务调用授权校验，token不正确，uri={}, client={}, token={}", request.getRequestURI(), token[0], token[1]);
        return false;
    }

}
