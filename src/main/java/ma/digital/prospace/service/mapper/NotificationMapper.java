package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Notification;
import ma.digital.prospace.service.dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "comptePro.id", target = "compteProId")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "compteProId", target = "comptePro.id")
    Notification toEntity(NotificationDTO notificationDTO);
}


