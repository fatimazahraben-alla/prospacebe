package ma.digital.prospace.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.Map;

@Service
public class AuditLogService {
    private static final Logger auditLogger = LoggerFactory.getLogger("ma.digital.prospace.audit");

    // Method to log audits with additional and result data
    public void logAudit(String actionType, String username, String userId, String ipAddress, String userAgent, Map<String, Object> additionalData, Object resultData) {
        String auditMessage = buildAuditLog(actionType, username, userId, ipAddress, userAgent, additionalData, resultData);
        auditLogger.info(auditMessage);
    }

    // Helper method to construct the JSON audit log string
    private String buildAuditLog(String actionType, String username, String userId, String ipAddress, String userAgent, Map<String, Object> additionalData, Object resultData) {
        ObjectMapper mapper = new ObjectMapper();
        String additionalDataJson = "{}";
        String resultDataJson = "{}";
        try {
            // Serialize both additional data and result data to JSON
            additionalDataJson = mapper.writeValueAsString(additionalData);
            resultDataJson = mapper.writeValueAsString(resultData);
        } catch (JsonProcessingException e) {
            auditLogger.error("Error processing data for audit log", e);
        }

        // Return formatted JSON string
        return String.format("{\"timestamp\": \"%s\", \"actionType\": \"%s\", \"username\": \"%s\", \"userId\": \"%s\", " +
                        "\"ipAddress\": \"%s\", \"userAgent\": \"%s\", \"data\": %s, \"result\": %s}",
                Instant.now(), actionType, username, userId, ipAddress, userAgent, additionalDataJson, resultDataJson);
    }
}



