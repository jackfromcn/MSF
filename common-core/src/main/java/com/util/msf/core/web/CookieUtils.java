package com.util.msf.core.web;

import org.apache.commons.lang3.Validate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:41
 * @Description:
 * Cookie工具类，cookie的唯一性有name、path、domain组合确定，有一个不相同，则不是同一个cookie
 * domain应"."开头，例如".damameiyi.com"
 */
public class CookieUtils {

    public static final int MAX_AGE = 7 * 60 * 60 * 24;

    /**
     * 设置Cookie
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, MAX_AGE, null);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param maxAge   生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, maxAge, null);
    }

    /**
     * 设置指定域的cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param domain   域名
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain) {
        setCookie(response, name, value, MAX_AGE, domain);
    }


    /**
     * 设置指定域的cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param maxAge   生存时间（单位秒）
     * @param domain   域名
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }



    /**
     * 获得指定Cookie的值
     *
     * @param request 请求对象
     * @param name    名字
     * @return 值
     */
    public static Optional<String> getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                value = cookie.getValue();
                break;
            }
        }
        return Optional.ofNullable(value);
    }

    /**
     * 获得指定域的指定Cookie的值
     *
     * @param request 请求对象
     * @param name    名字
     * @return 值
     */
    public static Optional<String> getCookie(HttpServletRequest request, String name, String domain) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        Validate.notBlank(domain, "查询cookie[name=%s]时domain为空", name);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name) && domain.equals(cookie.getDomain())) {
                value = cookie.getValue();
                break;
            }
        }
        return Optional.ofNullable(value);
    }

    /**
     * 清除cookie
     *
     * @param response
     * @param name
     */
    public static void remove(HttpServletResponse response, String name) {
        remove(response, name, null);
    }

    /**
     * 清除指定域的cookie
     *
     * @param response
     * @param name
     */
    public static void remove(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }
}
