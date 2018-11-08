package com.util.msf.monitor;

//
//import io.micrometer.core.annotation.Timed;
//import io.micrometer.core.annotation.TimedSet;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author wencheng
// * @ClassName TestController
// * @Description TestController
// * @date 2017-11-13 下午6:06
// */
//@RestController
//public class TestController {
//
//    @GetMapping("/hi")
//    @Timed(value = "http_hi")
//    public String hi() {
//        play();
//        play1();
//        play2();
//        return "hi prometheus";
//    }
//
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello";
//    }
//
//    @GetMapping("/haha")
//    @TimedSet({
//            @Timed(value = "http_haha0"),
//            @Timed(value = "http_haha1", extraTags = {"a", "#{name}"}),
//            @Timed(value = "http_haha2", histogram = true),
//            @Timed(value = "http_haha3", longTask = true),
//            @Timed(value = "http_haha4", percentiles = {0.1, 0.5, 0.9}),
//    })
//    public String haha(@RequestParam("name") String name) {
//        return "haha";
//    }
//
//
//    @Timed(value = "play", extraTags = {"p0", "v1=0"})
//    public void play() {
//        System.out.println("TestController.play");
//        try {
//            TimeUnit.SECONDS.sleep(1L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Timed(value = "play1", extraTags = {"p1", "v1"})
//    private void play1() {
//        System.out.println("TestController.play1");
//        try {
//            TimeUnit.SECONDS.sleep(1L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void play2() {
//        System.out.println("TestController.play2");
//        try {
//            TimeUnit.SECONDS.sleep(1L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}


//package com.util.msf.monitor;
//
//import io.micrometer.core.instrument.*;
//import io.micrometer.spring.autoconfigure.MeterRegistryConfigurer;
//import io.micrometer.spring.autoconfigure.export.MetricsExporter;
//import io.micrometer.spring.web.servlet.WebMvcTags;
//import io.micrometer.spring.web.servlet.WebMvcTagsProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.time.Duration;
//import java.util.Arrays;
//
//import static com.util.msf.monitor.LeaseMonitorApplication.A.counter;
//import static com.util.msf.monitor.LeaseMonitorApplication.A.timer;
//
//
//@SpringBootApplication
//public class LeaseMonitorApplication {
//
//    private static MetricsExporter metricsExporter;
//
//
//    public MetricsExporter getMetricsExporter() {
//        return metricsExporter;
//    }
//
//    @Autowired
//    public void setMetricsExporter(MetricsExporter metricsExporter) {
//        this.metricsExporter = metricsExporter;
//    }
//
//    public static class A{
//        public static Counter counter = Counter
//                .builder("counter")
//                .baseUnit("beans") // optional
//                .description("a description of what this counter does") // optional
//                .tags("region", "test") // optional
//                .register(metricsExporter.registry());
//
//        public static Gauge gauge = Gauge.builder("aaa", 0.0, Double::valueOf)
//                .description("a description of what this gauge does") // optional
//                .tags("region", "test") // optional
//                .register(metricsExporter.registry());
//
//        public static Timer timer = Timer
//                .builder("my.timer")
//                .description("a description of what this timer does") // optional
//                .tags("region", "test") // optional
//                .register(metricsExporter.registry());
//    }
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(LeaseMonitorApplication.class, args);
//        MeterRegistry registry = metricsExporter.registry();
//        counter.increment();
//        timer.record(Duration.ofMillis(1));
////        new ClassLoaderMetrics().bindTo(registry);
////        new JvmMemoryMetrics().bindTo(registry);
////        new JvmGcMetrics().bindTo(registry);
////        new ProcessorMetrics().bindTo(registry);
////        new JvmThreadMetrics().bindTo(registry);
////
////        registry.counter("test_couner", Arrays.asList(Tag.of("tag1", "value1"), Tag.of("tag2", "value2"))).increment();
//
//    }
//
//
//    @Bean
//    MeterRegistryConfigurer configurer() {
//        return registry -> registry.config().commonTags(Arrays.asList(Tag.of("app", "value0"), Tag.of("env", "env0"), Tag.of("instance", "instance")));
//    }
//
//    @Bean
//    WebMvcTagsProvider webMvcTagsProvider() {
//        return new WebMvcTagsProvider() {
//            @Override
//            public Iterable<Tag> httpLongRequestTags(HttpServletRequest request, Object handler) {
//                return Arrays.asList(WebMvcTags.method(request), WebMvcTags.uri(request, null), Tag.of("remoteIp", "127.0.0.1"));
//            }
//
//            @Override
//            public Iterable<Tag> httpRequestTags(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
//                return Arrays.asList(WebMvcTags.method(request), WebMvcTags.uri(request, response),
//                        WebMvcTags.exception(ex), WebMvcTags.status(response), Tag.of("remoteIp", "127.0.0.1"));
//            }
//        };
//    }
//
//
////    @Bean
////    JvmMemoryMetrics jvmMemoryMetrics() {
////        return new JvmMemoryMetrics();
////    }
//
////    @Bean
////    LogbackMetrics logbackMetrics() {
////        return new LogbackMetrics();
////    }
//}
