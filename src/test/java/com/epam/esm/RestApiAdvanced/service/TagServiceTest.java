package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.mapper.GiftCertificateMapper;
import com.epam.esm.RestApiAdvanced.mapper.TagMapper;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.repository.TagRepository;
import com.epam.esm.RestApiAdvanced.repository.UserRepository;
import com.epam.esm.RestApiAdvanced.service.impl.TagServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private Tag tag;
    private Tag tagToSave;
    private TagDto tagDto;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private PagedResourcesAssembler<Tag> pagedResourcesAssembler;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("tagName");

        tagDto = new TagDto();
        tagDto.setName("tagName");

        tagToSave = new Tag();
        tagToSave.setName(tagDto.getName());
    }

    @Test
    void shouldGetAll() {
        Page<Tag> tags = new PageImpl<>(Arrays.asList(new Tag()));

        when(tagRepository.findAll((Pageable) any())).thenReturn(tags);
        when(pagedResourcesAssembler.toModel(any(),
                (RepresentationModelAssembler<Tag, RepresentationModel<?>>) any()))
                .thenReturn(PagedModel.empty());

        tagService.getAll(0, 10);

        verify(tagRepository).findAll(PageRequest.of(0, 10, Sort.by("name")));
    }

    @Test
    void shouldGetByIdOrThrowLocalException() {
        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(tag));
        when(tagMapper.toDto(any())).thenReturn(new TagDto());
        when(giftCertificateMapper.mapToDto(any())).thenReturn(new ArrayList<>());

        tagService.getById(1L);

        verify(tagRepository).findById(1L);
        assertThatThrownBy(() -> tagService.getById(2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreate() {
        when(tagMapper.toDto(any())).thenReturn(tagDto);

        TagDto tagDto = tagService.create(this.tagDto);

        verify(tagRepository).existsByName(this.tagDto.getName());
        verify(tagRepository).save(any());

        Assertions.assertThat(tagDto).isNotNull();
    }

    @Test
    void shouldDeleteById() {
        tagService.deleteById(1L);

        verify(tagRepository).deleteById(1L);
    }

    @Test
    void shouldGetTheMostUsedTagOfTheWealthiestUser() {
        Order setUpOrder = new Order();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setTagList(Arrays.asList(new Tag()));
        setUpOrder.setGiftCertificate(giftCertificate);
        List<Order> orders = Arrays.asList(setUpOrder);

        when(userRepository.getTheWealthiestUsersId()).thenReturn(1L);
        when(orderRepository.findAllByUser_Id(any())).thenReturn(new ArrayList<>(orders));

        tagService.getTheMostUsedTagOfTheWealthiestUser();

        verify(userRepository).getTheWealthiestUsersId();
        verify(orderRepository).findAllByUser_Id(1L);
    }
}