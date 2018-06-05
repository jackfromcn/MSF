package com.msf.core.common.worker;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:20
 * @Description: 用于后台异步处理请求，默认线程数为CPU可见核心数
 */
public class BackendWorker {
    private static Logger logger = LoggerFactory.getLogger(BackendWorker.class);

    /**
     * 默认核心线程池个数
     */
    private static int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 任务队列的容量
     */
    private static int CAPACITY = 10000;

    private static ThreadPoolExecutor threadPoolExecutor;


    static {
        init(Runtime.getRuntime().availableProcessors(), CAPACITY);
    }

    /**
     * 初始化异步任务处理，默认核心线程5个，线程名称格式：mljr-worker-%d
     *
     * @param poolSize 最大线程数
     * @param capcity  工作队列的容量
     */
    public static void init(int poolSize, int capcity) {
        init(CORE_SIZE, poolSize, new LinkedBlockingQueue<>(capcity));
    }

    /**
     * 初始化异步任务处理，线程名称格式：mljr-worker-%d
     *
     * @param coreSize 核心线程数
     * @param poolSize 最大线程数
     * @param capcity  工作队列的容量
     */
    public static void init(int coreSize, int poolSize, int capcity) {
        init(coreSize, poolSize, new LinkedBlockingQueue<>(capcity));
    }

    /**
     * 初始化异步任务处理，默认核心线程5个，线程名称格式：mljr-worker-%d
     *
     * @param poolSize  最大线程数，应当大于5
     * @param workQueue 工作队列
     */
    public static void init(int poolSize, BlockingQueue<Runnable> workQueue) {
        init(CORE_SIZE, poolSize, workQueue);
    }

    /**
     * 初始化异步任务处理，线程名称格式：mljr-worker-%d
     *
     * @param coreSize  核心线程数
     * @param poolSize  最大线程数
     * @param workQueue 工作队列
     */
    public static void init(int coreSize, int poolSize, BlockingQueue<Runnable> workQueue) {
        poolSize = Integer.max(coreSize, poolSize);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("mljr-worker-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(coreSize, poolSize, 10, TimeUnit.MINUTES, workQueue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("...............................初始化后台任务调度器，coreSize={}, maxSize={}", coreSize, poolSize);
    }


    /**
     * 使用外部自定义线程池
     *
     * @param executor 自定义线程池
     */
    public static void init(ThreadPoolExecutor executor) {
        BackendWorker.threadPoolExecutor = executor;
    }

    public static void shutdown() {
        if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.shutdownNow();
        }
    }

    public static void submit(final Runnable task) {
        if (task == null) {
            logger.warn("空任务");
            return;
        }
        threadPoolExecutor.execute(() -> {
            try {
                task.run();
                logger.info("任务处理中，activeCount={}, queueSize={}", threadPoolExecutor.getActiveCount(), threadPoolExecutor.getQueue().size());
            } catch (Exception e) {
                logger.error("occur exception", e);
            }
        });
    }
}
