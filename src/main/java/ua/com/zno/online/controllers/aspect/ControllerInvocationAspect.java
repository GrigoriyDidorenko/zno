package ua.com.zno.online.controllers.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by g.didorenko on 08.09.17.
 */

@Component
@Aspect
@Profile("dev")
public class ControllerInvocationAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerInvocationAspect.class);

    @Before("execution(* ua.com.zno.online.controllers..*(..))")
    public void logControllerInvocation(JoinPoint joinPoint) {
        String invokedMethodFullName = String.format("%s.%s(..)", joinPoint.getTarget().getClass().getName(), ((MethodSignature)joinPoint.getSignature()).getMethod().getName());
        LOG.debug("[Controller]: method invocation {} with args {}", invokedMethodFullName, joinPoint.getArgs());
    }
}
