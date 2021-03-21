package com.angelangelov.remont_bg.aop;

import com.angelangelov.remont_bg.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }


    @Pointcut("execution(* com.angelangelov.remont_bg.web.OfferController.singleOffer(..))")
    public void offersPointcut(){};

    @After("offersPointcut()")
    public void afterAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String offerId = (String) args[0];
        String action = joinPoint.getSignature().getName();
        logService.createLog(action,offerId);

        System.out.println();

    }
}
