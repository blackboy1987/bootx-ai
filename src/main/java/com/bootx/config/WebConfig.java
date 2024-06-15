package com.bootx.config;

import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.interceptor.CorsInterceptor;
import com.bootx.security.CurrentUserHandlerInterceptor;
import com.bootx.security.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author black
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public CurrentUserHandlerInterceptor currentMemberHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Member.class);
        return currentUserHandlerInterceptor;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentAdminHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Admin.class);
        return currentUserHandlerInterceptor;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(currentAdminHandlerInterceptor())
                .addPathPatterns("/api/admin/**");

        registry.addInterceptor(currentMemberHandlerInterceptor())
                .addPathPatterns("/api/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver());
    }

}
