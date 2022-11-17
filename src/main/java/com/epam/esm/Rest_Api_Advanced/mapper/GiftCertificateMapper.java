package com.epam.esm.Rest_Api_Advanced.mapper;

import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface GiftCertificateMapper {

    @Mapping(target = "tags", ignore = true)
    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> mapToDto(List<GiftCertificate> list);
}
