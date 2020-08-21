package cn.ffcs.buffet.common.config;

import cn.ffcs.buffet.common.interceptors.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @Description: Web配置，在addInterceptors 方法注册添加拦截器
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/20 20:33
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * 这个方法是用来配置静态资源的，比如html，js，css，等等
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下
        // registry.addResourceHandler("/upload/**").addResourceLocations("file:d:/upload/");
        //linux下
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/upload/");
    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/login", "/api/user/signUp", "/api/user/getCode", "/api/upload",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

}