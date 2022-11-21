package com.epam.esm.Rest_Api_Advanced.service;

import com.epam.esm.Rest_Api_Advanced.dto.TagDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import com.epam.esm.Rest_Api_Advanced.repository.TagRepository;
import com.epam.esm.Rest_Api_Advanced.repository.UserRepository;
import com.epam.esm.Rest_Api_Advanced.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private Tag tag;
    private Tag tagToSave;
    private TagDto tagDto;


    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

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
        tagService.getAll(0, 10);

        verify(tagRepository).findAll(PageRequest.of(0, 10, Sort.by("name")));
    }

    @Test
    void shouldGetByIdOrThrowLocalException() {
        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(tag));

        tagService.getById(1L);

        verify(tagRepository).findById(1L);
        assertThatThrownBy(() -> tagService.getById(2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreate() {
        tagService.create(tagDto);

        verify(tagRepository).existsByName(tagDto.getName());
        verify(tagRepository).save(any());
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
        when(userService.getOrdersById(1L, 0, 10)).thenReturn(new PageImpl(orders));

        tagService.getTheMostUsedTagOfTheWealthiestUser();

        verify(userRepository).getTheWealthiestUsersId();
        verify(userService).getOrdersById(1L, 0, 10);
    }
}