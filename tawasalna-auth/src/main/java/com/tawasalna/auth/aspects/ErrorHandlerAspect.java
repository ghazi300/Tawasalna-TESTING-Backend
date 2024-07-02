package com.tawasalna.auth.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ErrorHandlerAspect {

    @AfterThrowing(value = "execution(public * com.tawasalna.auth.businesslogic.*.*.*(..))", throwing = "ex")
    public void serviceMethodThrowingAdvice(JoinPoint joinPoint, Exception ex) {

        final String[] className = ex.getClass().getCanonicalName().split("\\.");

        log.info(
                "Method: [{}] Threw Exception [{}] with message: [{}].",
                joinPoint.getSignature().getName(),
                className[className.length - 1],
                ex.getMessage()
        );
    }

    @Around("execution(public * com.tawasalna.auth.controller.*.*(..)))")
    public Object methodRuntimeBenchmark(ProceedingJoinPoint pjp) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object obj = pjp.proceed();

        final long deltaTime = System.currentTimeMillis() - start;

        log.info(
                "Method [{}] took approx: {} ms to execute.",
                pjp.getSignature().getName(),
                deltaTime
        );

        return obj;
    }

}
