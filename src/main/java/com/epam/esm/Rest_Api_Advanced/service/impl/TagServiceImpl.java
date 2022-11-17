package com.epam.esm.Rest_Api_Advanced.service.impl;

import com.epam.esm.Rest_Api_Advanced.dto.TagDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import com.epam.esm.Rest_Api_Advanced.repository.TagRepository;
import com.epam.esm.Rest_Api_Advanced.repository.UserRepository;
import com.epam.esm.Rest_Api_Advanced.service.TagService;
import com.epam.esm.Rest_Api_Advanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Page<Tag> getAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("name"));
        return tagRepository.findAll(pageRequest);
    }

    @Override
    public Tag getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no tag found by this id", HttpStatus.NOT_FOUND));
        return tag;
    }

    @Override
    public Tag create(TagDto tagDto) {
        String name = tagDto.getName();
        if (tagRepository.existsByName(name)) {
            throw new LocalException("Tag with this name already exists!", HttpStatus.BAD_REQUEST);
        }
        
        Tag tag = new Tag();
        tag.setName(name);
        tagRepository.save(tag);

        return tagRepository.findFirstByName(tagDto.getName()).get();
    }

    @Override
    public void deleteById(Long id) {
        try {
            tagRepository.deleteById(id);
        } catch (Exception e){
            throw new LocalException("The tag with this id is either tied to some Gift Certificate " +
                    "or doesn't exist and cannot be deleted!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Tag getTheMostUsedTagOfTheWealthiestUser() {
        Long theWealthiestUsersId = userRepository.getTheWealthiestUsersId();
        List<Order> ordersById = userService.getOrdersById(theWealthiestUsersId, 0, 10).toList();
        Set<GiftCertificate> giftCertificates = ordersById.stream().map(Order::getGiftCertificate).collect(Collectors.toSet());
        List<Tag> tags = giftCertificates.stream().map(GiftCertificate::getTagList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return getTheMostFrequentElement(tags);
    }

    public Tag getTheMostFrequentElement(List<Tag> elements){
        Map<Tag, Long> temp = elements.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));


        return temp.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();
    }
}
