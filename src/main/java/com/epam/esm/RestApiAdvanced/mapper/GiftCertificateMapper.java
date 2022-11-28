package com.epam.esm.RestApiAdvanced.mapper;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
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
