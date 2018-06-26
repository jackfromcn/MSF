package com.util.msf.core.constant;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:23
 * @Description: 删除标识
 */
public enum Deleted {
    YES(1),
    NON(0);
    private int value;

    Deleted(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public boolean isEqual(Integer value) {
        return value == null ? false : value.intValue() == this.value;
    }

}
