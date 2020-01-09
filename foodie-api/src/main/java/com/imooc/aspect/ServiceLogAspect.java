package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceLogAspect {
    private Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordLogTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("====== 执行开始 {}.{} =====",joinPoint.getTarget(),joinPoint.getSignature().getName());

        long begin =System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end =System.currentTimeMillis();

        long excuteTime =end-begin;
        if (excuteTime>3000){
            logger.error("执行时长：{}毫秒=======",excuteTime);
        }else if (excuteTime>2000){
            logger.warn("执行时长：{}毫秒=======",excuteTime);
        }else {
            logger.info("执行时长：{}毫秒=======",excuteTime);

        }
        return proceed;
    }

}
