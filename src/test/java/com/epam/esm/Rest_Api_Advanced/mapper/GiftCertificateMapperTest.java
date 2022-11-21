package com.epam.esm.Rest_Api_Advanced.mapper;

import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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