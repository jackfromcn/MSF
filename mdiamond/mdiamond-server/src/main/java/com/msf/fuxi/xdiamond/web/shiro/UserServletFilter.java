package com.msf.fuxi.xdiamond.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * set userName into slf4j MDC
 * 
 * @author hengyunabc
 *
 */
public class UserServletFilter implements Filter {

  private final static String USER_KEY = "userName";

  public void destroy() {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    boolean successfulRegistration = false;
    Subject subject = SecurityUtils.getSubject();

    if (subject != null) {
      Object principal = subject.getPrincipal();
      if (principal != null) {
        String username = principal.toString();
        successfulRegistration = registerUsername(username);
      }
    }

    try {
      chain.doFilter(request, response);
    } finally {
      if (successfulRegistration) {
        MDC.remove(USER_KEY);
      }
    }
  }

  public void init(FilterConfig arg0) throws ServletException {}

  /**
   * Register the user in the MDC under USER_KEY.
   * 
   * @param username
   * @return true id the user can be successfully registered
   */
  private boolean registerUsername(String username) {
    if (username != null && username.trim().length() > 0) {
      MDC.put(USER_KEY, username);
      return true;
    }
    return false;
  }

}
