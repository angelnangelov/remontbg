package com.angelangelov.remont_bg.config;

import com.angelangelov.remont_bg.interceptor.GreetingInterceptor;
import com.angelangelov.remont_bg.interceptor.PageTitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class AppWebConfiguration implements WebMvcConfigurer {
    private final PageTitleInterceptor pageTitleInterceptor;
    private final GreetingInterceptor greetingInterceptor;

    @Autowired
    public AppWebConfiguration(PageTitleInterceptor pageTitleInterceptor, GreetingInterceptor greetingInterceptor) {
        this.pageTitleInterceptor = pageTitleInterceptor;
        this.greetingInterceptor = greetingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(pageTitleInterceptor);
        registry.addInterceptor(greetingInterceptor);
    }
}
