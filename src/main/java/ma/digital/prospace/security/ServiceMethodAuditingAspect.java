package ma.digital.prospace.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMethodAuditingAspect {

    @Autowired
    private AuditorAware<String> auditorAware;



    @Before("execution(* ma.digital.prospace.service.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String username = auditorAware.getCurrentAuditor().orElse("Unknown");
        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("Method called: " + methodName + " by user: " + username);
        // Here, you can also integrate with your logging framework or auditing storage
    }
}
