package cloud.thanos.ddns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author leanderli
 * @see
 * @since 2019/5/17
 */
@Configuration
public class DefaultView implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:error.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
