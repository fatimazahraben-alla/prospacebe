package ma.digital.prospace.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendSuccessNotification(String deviceToken, String message) {
        Notification notification = Notification.builder()
                .setTitle("Création de Compte Pro")
                .setBody(message)
                .build();

        Message messageToSend = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .putData("content", message)
                .putData("title", "Création de Compte Pro")
                .build();

        try {
            String response = firebaseMessaging.send(messageToSend);
            System.out.println("Notification sent successfully: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

