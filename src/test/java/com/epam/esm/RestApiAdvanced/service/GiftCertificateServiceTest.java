package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.mapper.GiftCertificateMapper;
import com.epam.esm.RestApiAdvanced.mapper.TagMapper;
import com.epam.esm.RestApiAdvanced.repository.GiftCertificateRepository;
import com.epam.esm.RestApiAdvanced.repository.TagRepository;
import com.epam.esm.RestApiAdvanced.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

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
        Page<GiftCertificate> page = new PageImpl<>(Arrays.asList(new GiftCertificate()));

        when(giftCertificateRepository.findAll(name)).thenReturn(page);

        giftCertificateService.getAll(0, 10);

        verify(giftCertificateRepository).findAll(name);
    }

    @Test
    void shouldGetById() {
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));
        when(giftCertificateMapper.toDto(any())).thenReturn(giftCertificateDto);
        when(tagMapper.mapToDto(any())).thenReturn(new ArrayList<>());

        GiftCertificateDto byId = giftCertificateService.getById(1L);

        verify(giftCertificateRepository).findById(1L);

        assertThat(byId).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getById(2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByName() {
        when(giftCertificateMapper.toDto(any())).thenReturn(giftCertificateDto);
        when(tagMapper.mapToDto(any())).thenReturn(new ArrayList<>());
        when(giftCertificateRepository.findFirstByNameLike(NAME)).thenReturn(java.util.Optional.ofNullable(giftCertificate));

        GiftCertificateDto byName = giftCertificateService.getByName(NAME);

        verify(giftCertificateRepository).findFirstByNameLike(NAME);

        assertThat(byName).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getByName("")).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByDescription() {
        when(giftCertificateRepository.findFirstByDescriptionLike(DESCRIPTION)).thenReturn(java.util.Optional.ofNullable(giftCertificate));
        when(giftCertificateMapper.toDto(any())).thenReturn(giftCertificateDto);
        when(tagMapper.mapToDto(any())).thenReturn(new ArrayList<>());

        GiftCertificateDto byDescription = giftCertificateService.getByDescription(DESCRIPTION);

        verify(giftCertificateRepository).findFirstByDescriptionLike(DESCRIPTION);

        assertThat(byDescription).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getByDescription("")).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldGetByTagName() {
        List<GiftCertificate> listToPass = Arrays.asList(new GiftCertificate());

        when(tagRepository.findFirstByName(NAME)).thenReturn(java.util.Optional.ofNullable(tag));
        when(giftCertificateRepository
                .findAll((Specification<GiftCertificate>) any(),
                        (Pageable) any()))
                .thenReturn(new PageImpl<>(listToPass));

        PagedModel<GiftCertificateDto> byTagName = giftCertificateService.getByTagName(NAME, 0, 10);

        verify(tagRepository).findFirstByName(NAME);
        verify(giftCertificateRepository).findAll((Specification<GiftCertificate>) any(), (Pageable) any());

        assertThatThrownBy(() -> giftCertificateService.getByTagName("", 0, 0)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreate() {
        when(giftCertificateRepository.save(any())).thenReturn(giftCertificate);
        when(giftCertificateRepository.existsByName(NAME)).thenReturn(false);
        when(giftCertificateMapper.toDto(any())).thenReturn(giftCertificateDto);
        when(tagMapper.mapToDto(any())).thenReturn(new ArrayList<>());

        giftCertificateService.create(giftCertificateDto);

        verify(giftCertificateRepository).existsByName(giftCertificateDto.getName());
        verify(giftCertificateRepository).save(any());
    }

    @Test
    void shouldUpdate() {
        when(giftCertificateRepository.save(any())).thenReturn(giftCertificate);
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));
        when(giftCertificateMapper.toDto(any())).thenReturn(giftCertificateDto);
        when(tagMapper.mapToDto(any())).thenReturn(new ArrayList<>());

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