package ma.digital.prospace.Aspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.*;
@Aspect
@Component
public class AuditAspect {
    @Before("@within(org.springframework.stereotype.Service) && @annotation(audit)")
    public void beforeAudit(Audit audit) {
        String operationName = audit.value();
        System.out.println("Audit trail: Operation " + operationName + " is being performed.");
        // Vous pouvez enregistrer cette information dans un système de journalisation ou une base de données
    }
}
