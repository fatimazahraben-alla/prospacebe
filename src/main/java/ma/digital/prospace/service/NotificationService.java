package ma.digital.prospace.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.service.dto.NotificationMessage;
import ma.digital.prospace.service.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

@Autowired
    private final FirebaseMessaging firebaseMessaging;
    public NotificationService(FirebaseMessaging firebaseMessaging) {
       this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotificationByToken(NotificationMessage notificationMessage) {

        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();

        // Construction des données avec les valeurs de NotificationMessage
        Map<String, String> data = new HashMap<>();
        data.put("transactionID", notificationMessage.getTransactionID());
        data.put("fs", String.valueOf(notificationMessage.getFs()));
        data.put("compteID", String.valueOf(notificationMessage.getCompteID()));
        String entreprises = String.join(",", notificationMessage.getEntrepriseList());
        data.put("entrepriseList", entreprises);

        // Construction du message FCM
        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setToken(notificationMessage.getDeviceToken())
                .build();

        try {
            firebaseMessaging.send(message);
            return "Success Sending Notification";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error Sending Notification";
        }
    }
}


