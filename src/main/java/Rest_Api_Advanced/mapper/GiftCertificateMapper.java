package Rest_Api_Advanced.mapper;

import Rest_Api_Advanced.dto.GiftCertificateDto;
import Rest_Api_Advanced.model.GiftCertificate;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface GiftCertificateMapper {

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> mapToDto(List<GiftCertificate> list);
}
