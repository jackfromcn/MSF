package com.util.msf.log.alarm;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:05
 * @Description:
 */
public class AlarmEvent {

    /**
     * 事件场景
     */
    private String scene;

    /**
     * 事件描述
     */
    private String desc;

    /**
     * 用户编号
     */
    private long userId;

    /**
     * 业务主键
     */
    private String tid;


    /**
     * 业务数据
     */
    private Object data;


    private AlarmEvent(String scene, String desc, String tid, long userId, Object data) {
        this.scene = scene;
        this.desc = desc;
        this.userId = userId;
        this.tid = tid;
        this.data = data;
    }

    /**
     * 构造告警事件
     *
     * @param scene  发生场景
     * @param desc   事件描述
     * @param tid   业务主键
     * @param userId 用户编号或者操作账号
     * @param data   业务数据
     * @return
     */
    public static AlarmEvent of(String scene, String desc, String tid, long userId, Object data) {
        return new AlarmEvent(scene, desc, tid, userId, data);
    }

    /**
     * 构造告警事件
     *
     * @param scene  发生场景
     * @param desc   事件描述
     * @param tid   业务主键
     * @param userId 用户编号
     * @return
     */
    public static AlarmEvent of(String scene, String desc, String tid, long userId) {
        return new AlarmEvent(scene, desc, tid, userId, null);
    }

    /**
     * 构造告警事件
     *
     * @param scene 发生场景
     * @param desc  事件描述
     * @param tid  业务主键
     * @param data  业务数据
     * @return
     */
    public static AlarmEvent of(String scene, String desc, String tid, Object data) {
        return new AlarmEvent(scene, desc, tid, 0L, data);
    }

    /**
     * 构造告警事件
     *
     * @param scene 发生场景
     * @param desc  事件描述
     * @param tid  业务主键
     * @return
     */
    public static AlarmEvent of(String scene, String desc, String tid) {
        return new AlarmEvent(scene, desc, tid, 0L, null);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "scene='" + scene + '\'' +
                ", desc='" + desc + '\'' +
                ", userId=" + userId +
                ", tid='" + tid + '\'' +
                ", data=" + data +
                '}';
    }
}
