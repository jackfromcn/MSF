package com.msf.common.rpc.auth.interceptor;

import com.util.msf.core.crypto.Cryptos;
import com.util.msf.core.helper.WebHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录会话拦截器，用于用户认证，解析登录会话
 *
 * @author iwang
 * @since 2017/12/6
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public LoginInterceptor() {
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = null;
        try {
            userId = StringUtils.isBlank(userId) ? null : Cryptos.aesDecrypt(userId);
            WebHelper.init(NumberUtils.createLong(userId));

        } catch (Exception e) {
            logger.error("登录上下文处理异常，userId={}, uri={}", userId, request.getRequestURI(), e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            logger.error("登录上下文处理异常，userId={}, uri={}", WebHelper.getUserId(), request.getRequestURI(), ex);
        }
        WebHelper.destroy();
    }
}
