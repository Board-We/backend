package com.boardwe.boardwe.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.boardwe.boardwe.controller.*Controller.*(..))")
    public Object controllerLogging(ProceedingJoinPoint jp) throws Throwable {

        logger.info("* 메서드 "+ jp.getSignature().getName() + " 시작");
        String parameters = Arrays.toString(jp.getArgs());
        if (parameters.length() < 1000) logger.info("* 파라미터: "+ parameters);

        Object result = jp.proceed();

        logger.info("* 메서드 "+ jp.getSignature().getName() + " 종료");
        String resultStr = result.toString();
        if (resultStr.length() < 1000) logger.info("* 결과: "+ resultStr);

        return result;
    }
}

