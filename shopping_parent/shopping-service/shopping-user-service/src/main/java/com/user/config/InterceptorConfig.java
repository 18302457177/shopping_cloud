package com.user.config;

import com.user.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login")
                .excludePathPatterns("/user/phoneLogin").excludePathPatterns("/**")
                .excludePathPatterns("/user/register").excludePathPatterns("/upload/img")
                .excludePathPatterns("/doc.html/**").excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/webjars/**").excludePathPatterns("/swagger-resources/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
