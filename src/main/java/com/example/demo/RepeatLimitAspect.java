package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shixin
 * @date 2021/3/21
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class RepeatLimitAspect {
    public static final String POINT_CUT = "@annotation(com.example.demo.RepeatLimit)";
    public static final EvaluationContext EVALUATION_CONTEXT = new StandardEvaluationContext();
    final StringRedisTemplate redisTemplate;

    @Pointcut(POINT_CUT)
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        RepeatLimit repeatLimit = signature.getMethod().getAnnotation(RepeatLimit.class);
        String[] parameterNames = signature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        for (int i = 0; i < parameterNames.length; i++) {
            EVALUATION_CONTEXT.setVariable(parameterNames[i], args[i]);
        }
        String key = String.valueOf(spelExpressionParser.parseExpression(repeatLimit.key()).getValue(EVALUATION_CONTEXT));
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, key, repeatLimit.interval(), TimeUnit.SECONDS);
        if (Objects.nonNull(success) && !success) {
            throw new RepeatException("请勿重复提交");
        }

        try {
            return proceedingJoinPoint.proceed();
        } finally {
            redisTemplate.delete(key);
        }
    }
}
