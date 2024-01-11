package lzj.jsu.labsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//实现接口，web拦截
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    //添加拦截器
    //所有路径进行拦截
    public void addInterceptors(InterceptorRegistry registry) {
        String[] staticPath = {"/static/**"};
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
                .excludePathPatterns(staticPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/static/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
