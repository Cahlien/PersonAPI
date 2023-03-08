package dev.crowell.personapi.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* dev.crowell.personapi.repos.PersonRepository.findById(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Request made for instant message with id: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* dev.crowell.personapi.repos.PersonRepository.findById(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Request fulfilled for instant message with id: {}", joinPoint.getArgs()[0]);
    }
}
