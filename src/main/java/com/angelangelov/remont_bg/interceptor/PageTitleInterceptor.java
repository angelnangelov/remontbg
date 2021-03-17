package com.angelangelov.remont_bg.interceptor;

import com.angelangelov.remont_bg.annotation.PageTitle;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PageTitleInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

        String title = "Ремонт.бг";
        String addToTitleStr = "";
        if (handler instanceof HandlerMethod) {
            PageTitle addToTitle = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);
            addToTitleStr = addToTitle != null ? " - " + addToTitle.name() : "";
        }
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        modelAndView.addObject("pageTitle", title + addToTitleStr);
    }

}