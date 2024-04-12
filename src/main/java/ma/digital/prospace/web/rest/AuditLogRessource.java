package ma.digital.prospace.web.rest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audit")
public class AuditLogRessource {

    private static final String PATH_TO_ENTREPRISE_LOG = "logs/ENTREPRISE_LOG.log";
    private static final String PATH_TO_ESPACE_LOG = "logs/ESPACE_LOG.log";

    @GetMapping("/api/audit-logs")
    public List<String> getAuditLogs(@RequestParam String logType, @RequestParam(required = false) String filter) {
        String filePath = "";
        switch (logType.toLowerCase()) {
            case "entreprise":
                filePath = PATH_TO_ENTREPRISE_LOG;
                break;
            case "espace":
                filePath = PATH_TO_ESPACE_LOG;
                break;
            default:
                throw new IllegalArgumentException("Invalid log type provided.");
        }

        File logFile = new File(filePath);
        try {
            List<String> lines = FileUtils.readLines(logFile, StandardCharsets.UTF_8);
            if (filter != null && !filter.isEmpty()) {
                return lines.stream()
                        .filter(line -> line.contains(filter))
                        .collect(Collectors.toList());
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
