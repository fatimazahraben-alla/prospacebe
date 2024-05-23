package ma.digital.prospace.web.rest;

import ma.digital.prospace.service.NotificationService;
import ma.digital.prospace.service.dto.NotificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final NotificationService notificationService;

    @Autowired
    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() != null) {
            return ResponseEntity.badRequest().body(null); // Prevent creation with existing ID
        }
        NotificationDTO result = notificationService.createNotification(notificationDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("/notifications")
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() == null) {
            return ResponseEntity.badRequest().body(null); // ID is required for update
        }
        NotificationDTO result = notificationService.updateNotification(notificationDTO);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
    /*@GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable UUID id) {
        NotificationDTO notificationDTO = notificationService.findOne(id);
        if (notificationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(notificationDTO);
    }*/
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.findAll();
        if (notifications.isEmpty()) {
            return ResponseEntity.ok().body(notifications);
        }
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/notifications/{compteProId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByCompteProId(@PathVariable String compteProId) {
        List<NotificationDTO> notifications = notificationService.findNotificationsByCompteProId(compteProId);
        return ResponseEntity.ok(notifications);
    }
    @PatchMapping("/notifications/{id}/mas-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable UUID id) {
        NotificationDTO notification = notificationService.findOne(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        notification.setRead(true);
        notificationService.updateNotification(notification);
        return ResponseEntity.ok().build();
    }

}