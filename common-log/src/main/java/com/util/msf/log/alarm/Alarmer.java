package com.util.msf.log.alarm;

import com.util.msf.core.utils.JsonUtils;
import com.util.msf.core.worker.BackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:04
 * @Description:
 */
public class Alarmer {
    public final static Marker ALARM_MARKER = MarkerFactory.getMarker("ALARM");
    private static Logger logger = LoggerFactory.getLogger("Alarmer");
    private static Alarmer defaultAlarmer = new Alarmer();

    private LongAdder count = new LongAdder();
    private int limit = 10;

    /**
     * 使用默认的通知机制，每10个
     *
     * @return
     */
    public static Alarmer defaultAlarmer() {
        return defaultAlarmer;
    }

    /**
     * 设定触发机制，当达到limit数后在下次请求会触发，其中limit需和logback的CyclicBufferTracker配置的bufferSize相同，如果少了会按照logback配置的发送邮件，如果多了会被丢弃
     *
     * @param limit 打印该数量的日志后触发邮件，默认10条
     */
    public void init(int limit) {
        this.limit = limit;
    }

    private boolean isLimited() {
        return count.longValue() >= limit;
    }

    /**
     * 按照设定的机制运行指定的告警
     *
     * @param event 告警行为
     */
    public void send(Runnable event) {
        count.increment();
        if (isLimited()) {
            count.reset();
        }
        BackendWorker.submit(event);
    }

    /**
     * 发送邮件告警，遵循日志格式
     *
     * @param format
     * @param arguments
     */
    public void send(String format, Object... arguments) {
        count.increment();
        Runnable event;
        if (isLimited()) {
            event = () -> logger.error(ALARM_MARKER, format, arguments);
            count.reset();
        } else {
            event = () -> logger.error(format, arguments);
        }
        BackendWorker.submit(event);
    }

    /**
     * 发生邮件告警
     *
     * @param event
     * @see AlarmEvent
     */
    public static void send(AlarmEvent event) {
        if (event == null) {
            return;
        }
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String clazz = traceElement.getClassName().substring(traceElement.getClassName().lastIndexOf(".") + 1);
        String method = traceElement.getMethodName();
        int line = traceElement.getLineNumber();
        Runnable evt;
        if (event.getUserId() != 0 && event.getData() != null) {
            evt = () -> logger.error(ALARM_MARKER, "{}.{}[{}]->【{}】- {}，业务主键={}，用户编号={}，业务数据={}", clazz, method, line, event.getScene(), event.getDesc(), event.getTid(), event.getUserId(), JsonUtils.toJson(event.getData()));
        } else if (event.getUserId() != 0 && event.getData() == null) {
            evt = () -> logger.error(ALARM_MARKER, "{}.{}[{}]->【{}】- {}，业务主键={}，用户编号={}", clazz, method, line, event.getScene(), event.getDesc(), event.getTid(), event.getUserId());
        } else if (event.getUserId() == 0 && event.getData() != null) {
            evt = () -> logger.error(ALARM_MARKER, "{}.{}[{}]->【{}】- {}，业务主键={}，业务数据={}", clazz, method, line, event.getScene(), event.getDesc(), event.getTid(), JsonUtils.toJson(event.getData()));
        } else {
            evt = () -> logger.error(ALARM_MARKER, "{}.{}[{}]->【{}】- {}，业务主键={}", clazz, method, line, event.getScene(), event.getDesc(), event.getTid());
        }
        BackendWorker.submit(evt);
    }

    /**
     * 立即发送邮件告警
     *
     * @param format
     * @param arguments
     */
    public static void sendNow(String format, Object... arguments) {
        BackendWorker.submit(() -> logger.error(ALARM_MARKER, format, arguments));
    }

    @PreDestroy
    public void destroy() {
        if (count.intValue() != 0) {
            logger.error(ALARM_MARKER, "应用告警已全部发出");
        }
    }
}
