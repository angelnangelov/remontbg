package com.angelangelov.remont_bg.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class GreetingInterceptor implements HandlerInterceptor{


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String greetings = "";
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();

        if(hour<12 && hour>5){
            greetings="Добро утро, ";
        }else  if(hour>12 && hour<18){
            greetings="Добър ден, ";
        }else {
            greetings="Добър вечер, ";
        }
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            if (handler instanceof HandlerMethod) {
                modelAndView
                        .addObject("greetings", greetings);
            }
        }

    }
}
