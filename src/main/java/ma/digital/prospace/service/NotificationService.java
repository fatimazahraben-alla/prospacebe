package ma.digital.prospace.service;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Notification;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.NotificationRepository;
import ma.digital.prospace.service.dto.NotificationDTO;
import ma.digital.prospace.service.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CompteProRepository compteProRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               CompteProRepository compteProRepository,
                               NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.compteProRepository = compteProRepository;
        this.notificationMapper = notificationMapper;
    }

    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);

        // Validate the associated ComptePro
        ComptePro comptePro = compteProRepository.findById(notificationDTO.getCompteProId())
                .orElseThrow(() -> new IllegalArgumentException("ComptePro not found with id: " + notificationDTO.getCompteProId()));

        notification.setComptePro(comptePro);
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(savedNotification);
    }

    public NotificationDTO updateNotification(NotificationDTO notificationDTO) {
        Notification existingNotification = notificationRepository.findById(notificationDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + notificationDTO.getId()));

        existingNotification.setTitle(notificationDTO.getTitle());
        existingNotification.setMessage(notificationDTO.getMessage());

        if (notificationDTO.getCompteProId() != null &&
                !existingNotification.getComptePro().getId().equals(notificationDTO.getCompteProId())) {
            ComptePro comptePro = compteProRepository.findById(notificationDTO.getCompteProId())
                    .orElseThrow(() -> new IllegalArgumentException("ComptePro not found with id: " + notificationDTO.getCompteProId()));
            existingNotification.setComptePro(comptePro);
        }

        Notification updatedNotification = notificationRepository.save(existingNotification);
        return notificationMapper.toDto(updatedNotification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> findAll() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public NotificationDTO findOne(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));
        return notificationMapper.toDto(notification);
    }
    public List<NotificationDTO> findNotificationsByCompteProId(String compteProId) {
        return notificationRepository.findByCompteProId(compteProId).stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}