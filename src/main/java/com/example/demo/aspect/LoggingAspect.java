package com.example.demo.aspect;

import com.example.demo.audit.models.AuditLog;
import com.example.demo.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final AuditLogRepository auditLogRepository;

    @Async
    @Around("execution(* com.example.demo.serviceImpl..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        var auditlog = AuditLog.builder()
                .methodName(joinPoint.getSignature().toString())
                .executionTime(elapsedTime)
                .build();

        auditLogRepository.save(auditlog);

        return result;
    }
}
