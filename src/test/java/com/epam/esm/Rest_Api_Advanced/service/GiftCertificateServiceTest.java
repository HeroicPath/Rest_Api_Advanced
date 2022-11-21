package com.epam.esm.Rest_Api_Advanced.service;

import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import com.epam.esm.Rest_Api_Advanced.repository.GiftCertificateRepository;
import com.epam.esm.Rest_Api_Advanced.repository.TagRepository;
import com.epam.esm.Rest_Api_Advanced.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.print.attribute.standard.MediaSize;
import java.rmi.AlreadyBoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;
    private Tag tag;
    private final String NAME = "name";
    private final String DESCRIPTION = "description";

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate(
                1L, NAME, DESCRIPTION, 1.1, 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>()
        );

        giftCertificateDto = new GiftCertificateDto(
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                new ArrayList<>()
        );

        tag = new Tag();
        tag.setId(1L);
        tag.setName("tagName");
    }

    @Test
    void shouldGetAll() {
        PageRequest name = PageRequest.of(0, 10, Sort.by("name"));

        giftCertificateService.getAll(0, 10);

        verify(giftCertificateRepository).findAll(name);
    }

    @Test
    void shouldGetById() {
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));

        GiftCertificate byId = giftCertificateService.getById(1L);

        verify(giftCertificateRepository).findById(1L);

        assertThat(byId).isEqualTo(giftCertificate);
        assertThatThrownBy(() -> giftCertificateService.getById(2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByName() {
        when(giftCertificateRepository.findFirstByNameLike(NAME)).thenReturn(java.util.Optional.ofNullable(giftCertificate));

        GiftCertificate byName = giftCertificateService.getByName(NAME);

        verify(giftCertificateRepository).findFirstByNameLike(NAME);

        assertThat(byName).isEqualTo(giftCertificate);
        assertThatThrownBy(() -> giftCertificateService.getByName("")).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByDescription() {
        when(giftCertificateRepository.findFirstByDescriptionLike(DESCRIPTION)).thenReturn(java.util.Optional.ofNullable(giftCertificate));

        GiftCertificate byDescription = giftCertificateService.getByDescription(DESCRIPTION);

        verify(giftCertificateRepository).findFirstByDescriptionLike(DESCRIPTION);

        assertThat(byDescription).isEqualTo(giftCertificate);
        assertThatThrownBy(() -> giftCertificateService.getByDescription("")).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByTagName() {
        List<GiftCertificate> listToPass = Arrays.asList(new GiftCertificate());

        when(tagRepository.findFirstByName(NAME)).thenReturn(java.util.Optional.ofNullable(tag));
        when(giftCertificateRepository
                .findAll((Specification<GiftCertificate>) any(),
                        (Pageable) any()))
                .thenReturn((Page) new PageImpl<>(listToPass));

        Page<GiftCertificate> byTagName = giftCertificateService.getByTagName(NAME, 0, 10);

        verify(tagRepository).findFirstByName(NAME);
        verify(giftCertificateRepository).findAll((Specification<GiftCertificate>) any(), (Pageable) any());

        assertThat(byTagName).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getByTagName("", 0, 0)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreate() {
        when(giftCertificateRepository.existsByName(NAME)).thenReturn(false);

        giftCertificateService.create(giftCertificateDto);

        verify(giftCertificateRepository).existsByName(giftCertificateDto.getName());
        verify(giftCertificateRepository).save(any());
    }

    @Test
    void shouldUpdate() {
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));

        giftCertificateService.update(giftCertificateDto, 1L);
        giftCertificateDto.setName("newName");
        giftCertificateService.update(giftCertificateDto, 1L);

        verify(giftCertificateRepository, times(2)).save(any());
        verify(giftCertificateRepository).existsByName("newName");
        assertThatThrownBy(() -> giftCertificateService.update(giftCertificateDto, 2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldDeleteById() {
        giftCertificateService.deleteById(1L);

        verify(giftCertificateRepository).deleteById(1L);
    }
}