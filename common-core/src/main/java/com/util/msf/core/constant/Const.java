package com.util.msf.core.constant;

import java.nio.charset.Charset;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:22
 * @Description:
 */
public interface Const {

    int PAGE_SIZE = 20;

    int PAGE_MAX_SIZE = 50;

    String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    String YYYYMMDDHH = "yyyy-MM-dd HH";

    String YYYYMMDD = "yyyy-MM-dd";

    String HHMMSS = "HH:mm:ss";

    String UTF8 = "UTF-8";

    Charset CHARSET = Charset.forName(UTF8);
}
