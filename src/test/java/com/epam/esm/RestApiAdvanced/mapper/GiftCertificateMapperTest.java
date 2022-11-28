package com.epam.esm.RestApiAdvanced.mapper;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class GiftCertificateMapperTest {

    private GiftCertificateMapper giftCertificateMapper;
    private GiftCertificate giftCertificate;
    private final String NAME = "name";

    @BeforeEach
    void setUp() {
        giftCertificateMapper = new GiftCertificateMapperImpl();
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(NAME);
    }

    @Test
    void shouldConvertToDto() {
        GiftCertificateDto giftCertificateDto = giftCertificateMapper.toDto(giftCertificate);

        Assertions.assertThat(giftCertificateDto.getName()).isEqualTo(giftCertificate.getName());
    }

    @Test
    void shouldMapToDto() {
        List<GiftCertificate> list = Arrays.asList(giftCertificate);

        List<GiftCertificateDto> giftCertificateDtos = giftCertificateMapper.mapToDto(list);
        GiftCertificateDto giftCertificateDto = giftCertificateDtos.get(0);

        Assertions.assertThat(giftCertificateDto.getName()).isEqualTo(giftCertificate.getName());
    }
}