package com.msf.core.common.math;

import com.msf.core.common.utils.N;

import java.math.BigDecimal;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:30
 * @Description: 提供平台默认的浮点数计算方法，四色五入，保留小数点后两位
 */
public class Arith {

    // 默认除法运算精度
    private static final int SCALE = 2;

    // 这个类不能实例化
    private Arith() {

    }

    /**
     * 构建保留小数点后两位的BigDecimal
     *
     * @param num 可以是任何非BigDecimal的Number子类
     * @param <T> Number子类
     * @return
     */
    public static <T extends Number> BigDecimal of(T num) {
        return num == null ? null : new BigDecimal(num.toString()).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 四色五入，保留小数点后两位
     *
     * @param num
     * @return
     */
    public static BigDecimal of(BigDecimal num) {
        return num == null ? null : num.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘法运算，四舍五入，保留小数点后两位
     *
     * @param v1
     * @param v2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal mul(BigDecimal v1, T v2) {
        BigDecimal v = v1.multiply(new BigDecimal(v2.toString()));
        if (v.scale() > SCALE) {
            v = v.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        }
        return v;
    }

    /**
     * 乘法运算，四舍五入，保留小数点后两位
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        BigDecimal v = v1.multiply(v2);
        if (v.scale() > SCALE) {
            v = v.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        }
        return v;
    }

    /**
     * 减法运算
     *
     * @param v1
     * @param v2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal sub(BigDecimal v1, T v2) {
        return v1.subtract(new BigDecimal(v2.toString())).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法运算，四舍五入，保留小数点后两位
     *
     * @param v1
     * @param v2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal div(BigDecimal v1, T v2) {
        return v1.divide(new BigDecimal(v2.toString()), SCALE, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 除法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2, SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后2位，以后的数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 判断v1是否小于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lt(Integer v1, int v2) {
        if (v1 == null) {
            return false;
        }
        return v1.intValue() < v2;
    }

    /**
     * 判断v1是否小于等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lte(Integer v1, int v2) {
        if (v1 == null) {
            return false;
        }
        return v1.intValue() <= v2;
    }

    /**
     * 判断v1是否大于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gt(Integer v1, int v2) {
        if (v1 == null) {
            return false;
        }
        return v1.intValue() == v2;
    }


    /**
     * 判断v1是否大于等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gte(Integer v1, int v2) {
        if (v1 == null) {
            return false;
        }
        return v1.intValue() >= v2;
    }

    /**
     * 判断v1是否等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean eq(Integer v1, int v2) {
        if (v1 == null) {
            return false;
        }
        return v1.intValue() == v2;
    }

    /**
     * 判断v1是否不等于v2，只有相等才返回false，其他情况返回true
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean ne(Integer v1, int v2) {
        if (v1 == null) {
            return true;
        }
        return v1.intValue() == v2;
    }

    /**
     * 判断v1是否不等于v2，只有相等才返回false，其他情况返回true
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean ne(BigDecimal v1, BigDecimal v2) {
        if (N.allNull(v1, v2)) {
            return true;
        }
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) != 0;
    }


    /**
     * 判断v1是否小于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lt(BigDecimal v1, BigDecimal v2) {
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) == -1;
    }

    /**
     * 判断v1是否小于等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lte(BigDecimal v1, BigDecimal v2) {
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) <= 0;
    }

    /**
     * 判断v1是否大于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gt(BigDecimal v1, BigDecimal v2) {
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) == 1;
    }


    /**
     * 判断v1是否大于等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gte(BigDecimal v1, BigDecimal v2) {
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) >= 0;
    }

    /**
     * 判断v1是否等于v2，只有满足条件才返回true，其他情况返回false
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean eq(BigDecimal v1, BigDecimal v2) {
        if (N.anyNull(v1, v2)) {
            return false;
        }
        return v1.compareTo(v2) == 0;
    }

}
