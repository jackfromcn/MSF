package com.util.msf.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author wencheng
 * @ClassName SpELUtils
 * @Description SpEL表达式解析
 * @date 2017-10-10 下午5:24
 */
public class SpELUtils {
    /**
     * 解析SpEl表达式
     *
     * @param expression el表达式
     * @param method     方法
     * @param args       参数
     * @param classType  结果类型
     * @return T
     */
    public static <T> T parseExpression(String expression, Method method, Object[] args, Class<T> classType) {
        if (StringUtils.isBlank(expression)) {
            return null;
        }
        if (!expression.trim().startsWith("#") && !expression.trim().startsWith("$")) {
            return (T) expression;
        }
        //获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = discoverer.getParameterNames(method);
        if (ArrayUtils.isEmpty(paramNames)) {
            return (T) expression;
        }
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        //使用SPEL进行key的解析
        return new SpelExpressionParser().parseExpression(expression).getValue(context, classType);
    }
}
