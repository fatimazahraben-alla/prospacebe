package ma.digital.prospace.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Example method to send a push notification
    public void sendPushNotification(String deviceToken, String message) {
        // Logic to send notification
        System.out.println("Sending push notification to " + deviceToken + " with message: " + message);
        // Implement actual push notification logic here, possibly using FCM or another service
    }
}
