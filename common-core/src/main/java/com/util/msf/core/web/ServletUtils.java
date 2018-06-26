package com.util.msf.core.web;

import com.google.common.base.Splitter;
import com.google.common.net.HttpHeaders;
import com.util.msf.core.constant.Const;
import com.util.msf.core.utils.N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:45
 * @Description:
 */
public class ServletUtils {

    private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * 设置客户端缓存过期时间 的Header.
     */
    public static void headExpired(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header, set a fix expires date.
        response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + expiresSeconds * 1000);
        // Http 1.1 header, set a time after now.
        response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置禁止客户端缓存的Header.
     */
    public static void headNocache(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader(HttpHeaders.EXPIRES, 1L);
        response.addHeader(HttpHeaders.PRAGMA, "no-cache");
        // Http 1.1 header
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
    }

    /**
     * 设置LastModified Header.
     */
    public static void headLastModified(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     */
    public static void headEtag(HttpServletResponse response, String etag) {
        response.setHeader(HttpHeaders.ETAG, etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * <p>
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param lastModified 内容的最后修改时间.
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
                                               long lastModified) {
        long ifModifiedSince = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * <p>
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag 内容的ETag.
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader(HttpHeaders.IF_NONE_MATCH);
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }
            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader(HttpHeaders.ETAG, etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static boolean headFileDownload(HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(), Const.UTF8);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error("下载文件名称定义出现异常", e);
        }
        return false;
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转化成Map形式. api约定如下: 1. 如果queryString格式错误, 将可以分辨的参数返回, 不可分辨的舍弃. 例子:
     * a=1&b&c=1 将被解析为 {"a": ["1"], "c":["1"]} 2. 如果queryString两个分隔符之间, 有多于一个=,
     * 则键值分割以第一个等号为准 例子: a=1&b==1&c=2 将被解析为{"a":["1"], "b":["=1"], "c":["1"]} 3.
     * 如果queryString为null或空字符串, 返回空的Map 4. 如果queryString中有两个key相同，
     * 则两个值会被放到同一个key下
     *
     * @param queryString 待解析字符串 key=val&key=val&key3=val
     * @return 解析后的结果
     */
    public static Map<String, String[]> getParameterMap(String queryString) {
        if (queryString == null || queryString.trim().equals("")) {
            return new HashMap<>();
        }
        Map<String, ArrayList<String>> listParams = new HashMap<String, ArrayList<String>>();
        Map<String, String[]> arrayParams = new HashMap<String, String[]>();
        //解析
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            //一对key-val
            String[] items = pair.split("=", 2);
            if (items.length == 2) {
                String key = items[0];
                String val = items[1];
                try {
                    val = URLDecoder.decode(val, "utf-8");
                } catch (Exception e) {
                    logger.error("解析parameterMap异常：{}", val, e);
                }
                ArrayList<String> vals = listParams.get(key);
                if (vals == null) {
                    vals = new ArrayList<>(1);
                }
                vals.add(val);
                listParams.put(key, vals);
            }
        }
        //格式转换
        Set<Map.Entry<String, ArrayList<String>>> entries = listParams.entrySet();
        for (Map.Entry<String, ArrayList<String>> entry : entries) {
            String key = entry.getKey();
            String[] val = entry.getValue().toArray(new String[0]);
            arrayParams.put(key, val);
        }
        return arrayParams;
    }

    /**
     * 将请求参数转化为kv形式
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        if (null != request) {
            Set<String> paramsKey = request.getParameterMap().keySet();
            for (String key : paramsKey) {
                params.put(key, request.getParameter(key));
            }
        }
        return params;
    }

    /**
     * 以key=value形式拼接http请求参数
     *
     * @param req
     * @return
     */
    public static String getQueryString(HttpServletRequest req) {
        StringBuilder queryString = new StringBuilder();
        if (req == null) {
            return "";
        }
        //获取
        Enumeration<String> params = req.getParameterNames();
        if (params == null) {
            return "";
        }
        String key, value;
        while (params.hasMoreElements()) {
            key = params.nextElement();
            value = req.getParameter(key);
            queryString.append(key).append("=").append(value);
            queryString.append("&");
        }
        if (queryString.length() < 1) {
            return queryString.toString();
        }
        return queryString.substring(0, queryString.length() - 1);
    }

    /**
     * 获取访问客户端的ip
     *
     * @param request
     * @return
     */
    public static String IP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = Splitter.on(",").splitToList(ip).stream().filter(s -> !s.trim().equals("127.0.0.1")).findFirst().get();
        }
        return ip;
    }

    /**
     * 拼接url
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String appendParam(String url, String key, String value) {
        if (N.anyNull(url, key, value)) {
            return url;
        }
        String newUrl = url;
        try {
            String pair = key + "=" + URLEncoder.encode(value, Const.UTF8);
            if (!url.contains("?")) {
                newUrl = newUrl + "?" + pair;
            } else {
                if (url.endsWith("&") || url.endsWith("?")) {
                    newUrl = newUrl + pair;
                } else {
                    newUrl = newUrl + "&" + pair;
                }
            }
        } catch (Exception e) {
            logger.error("url拼接异常，url={}, key={}, value={}", url, key, value, e);
        }
        return newUrl;
    }

    /**
     * 判断HTTP请求是否来至于微信
     *
     * @param request
     * @return
     */
    public static boolean isWechat(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent == null ? false : userAgent.toLowerCase().indexOf("micromessenger") > -1;
    }

    /**
     * 判断HTTP请求是否来至于支付宝
     *
     * @param request
     * @return
     */
    public static boolean isAlipay(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent == null ? false : userAgent.toLowerCase().indexOf("alipay") > -1;
    }
}
