package ma.digital.prospace.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


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
     //   String userId = UserId();
        MDC.put("username", username);
        MDC.put("method", methodName);
        String ipAddress = getIpAddress();
        MDC.put("userId", "");
         //Ajoutez l'adresse IP au MDC
        MDC.put("ipAddress", ipAddress);
    }
    private String UserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Impossible de récupérer l'objet d'authentification à partir du contexte de sécurité.");
        }

        // Vérifie si l'objet Principal est un Jwt
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getSubject();
            return userId;
        }
        // Vérifie si l'objet Principal est un OidcUser
        else if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String userId = oidcUser.getSubject();
            return userId;
        } else {
            throw new IllegalStateException("Le type de l'objet Principal n'est ni Jwt ni OidcUser.");
        }
    }

    private String getIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getRemoteAddr();
    }
}
