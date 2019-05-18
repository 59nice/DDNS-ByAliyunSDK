package cloud.thanos.ddns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 动态域名解析启动类
 *
 * @author leanderli
 * @see
 * @since 2019/5/17
 */
@SpringBootApplication
@EnableScheduling
public class DDNSApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDNSApplication.class, args);
    }


}
