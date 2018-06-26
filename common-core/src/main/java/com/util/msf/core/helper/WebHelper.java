package com.util.msf.core.helper;

import com.util.msf.core.utils.M;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:29
 * @Description: 登录上下文的工具类，判断是否登录，获取登录用户ID等
 */
public class WebHelper {

    public static final String LOGIN_NAME = "loginName";
    public static final String USER_ID = "userId";

    private static final ThreadLocal<Map<String, Object>> loginContext = new ThreadLocal<>();
    private static String defaultLoginName = "anonymous";
    private static Long defaultUserId = 0L;

    private static Map<String, Object> emptySession = Collections.emptyMap();


    /**
     * 初始化登录会话
     *
     * @param loginName 登录名称
     * @param userId    用户编号
     */
    public static void init(String loginName, Long userId) {
        if (loginContext.get() == null) {
            loginContext.set(M.asMap(LOGIN_NAME, loginName, USER_ID, userId));
        } else {
            loginContext.get().put(LOGIN_NAME, loginName);
            loginContext.get().put(USER_ID, userId);
        }
    }

    /**
     * 初始化登录会话
     *
     * @param userId 用户编号
     */
    public static void init(Long userId) {
        if (loginContext.get() == null) {
            loginContext.set(M.asMap(USER_ID, userId));
        } else {
            loginContext.get().put(USER_ID, userId);
        }
    }


    public static void destroy() {
        loginContext.remove();
    }


    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        Long userId = getUserId();
        return userId != null && userId != 0L;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Long getUserId() {
        return (Long) Optional.ofNullable(loginContext.get()).orElse(emptySession).get(USER_ID);
    }

    /**
     * 获取登录名称
     *
     * @return
     */
    public static String getLoginName() {
        return (String) Optional.ofNullable(loginContext.get()).orElse(emptySession).get(LOGIN_NAME);
    }


    public static <T> void setBean(String key, T bean) {
        if (loginContext.get() == null) {
            loginContext.set(M.asMap(key, bean));
        } else {
            loginContext.get().put(key, bean);
        }
    }

    public static <T> T getBean(String key) {
        return (T) Optional.ofNullable(loginContext.get()).orElse(emptySession).get(key);
    }

}
