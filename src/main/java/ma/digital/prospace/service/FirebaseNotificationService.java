package ma.digital.prospace.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseNotificationService {
    private final FirebaseMessaging firebaseMessaging;

    public FirebaseNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotification(String registrationToken, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(registrationToken)
                .build();

        firebaseMessaging.send(message);
    }

    public void sendNotificationWithData(String registrationToken, String title, String body, Map<String, String> data) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putAllData(data)
                .setToken(registrationToken)
                .build();

        firebaseMessaging.send(message);
    }
}
