package platform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * Астекты для логирования времени выполнения и дэбага
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Точка входа для отслеживания
     */
    @Pointcut("execution(public * *(..)) && within(platform.service..*)")
    public void adsServicePointcut() {
    }

    /**
     * Точка входа для отслеживания
     */
    @Pointcut("execution(* platform.api.*.*(..))")
    public void adsExceptionPointcut() {
    }

    /**
     * @param proceedingJoinPoint Точка выполнения операций
     * @return Результат выполнения
     * @throws Throwable Любые исключения
     */
    @Around("adsServicePointcut()")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final StopWatch stopWatch = new StopWatch();
        //calculate method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        //Log method execution time
        log.info("Execution time of " + methodSignature.getDeclaringType().getSimpleName() // Class Name
                + "." + methodSignature.getName() + " " // Method Name
                + ":: " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }

    /**
     * @param joinPoint Точка выполнения операций
     * @return Результат выполнения
     * @throws Throwable Любые исключения
     */
    @Around("adsServicePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Method name = {}.{}() :: input  params = {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        log.debug("Method output data::{}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
        return result;
    }

    /**
     * @param joinPoint Точка выполнения операций
     * @param ex        Исключения
     */
    @AfterThrowing(value = "adsExceptionPointcut()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Exception ex) {

        log.error("Error message :: {}:: {} in {}.{}()",
                ex.getMessage(),
                Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
}
