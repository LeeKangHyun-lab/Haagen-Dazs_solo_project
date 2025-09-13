package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.interceptors.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${upload.url}")
    private String uploadUrl;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(myInterceptor);

        ir.excludePathPatterns("/error", "/roboats.txt", "/favicon.ico", "/assets/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(String.format("%s/**",uploadUrl))
                .addResourceLocations(String.format("file://%s/",uploadDir));
    }


}