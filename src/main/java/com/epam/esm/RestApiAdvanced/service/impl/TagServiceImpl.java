package com.epam.esm.RestApiAdvanced.service.impl;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import com.epam.esm.RestApiAdvanced.hateoas.assembler.TagAssembler;
import com.epam.esm.RestApiAdvanced.mapper.GiftCertificateMapper;
import com.epam.esm.RestApiAdvanced.mapper.TagMapper;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.repository.TagRepository;
import com.epam.esm.RestApiAdvanced.repository.UserRepository;
import com.epam.esm.RestApiAdvanced.service.TagService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
    private final OrderRepository orderRepository;
    private final TagAssembler tagAssembler;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagMapper tagMapper;
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    public PagedModel<TagDto> getAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<Tag> tags = tagRepository.findAll(pageRequest);

        if (page > tags.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(tags, tagAssembler);
    }

    @Override
    public TagDto getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no tag found by this id", HttpStatus.NOT_FOUND));
        TagDto tagDto = tagMapper.toDto(tag);
        tagDto.setGiftCertificateList(giftCertificateMapper.mapToDto(tag.getGiftCertificates()));

        return tagDto;
    }

    @SneakyThrows
    @Override
    public TagDto create(TagDto tagDto) {
        String name = tagDto.getName();
        if (tagRepository.existsByName(name)) {
            throw new LocalException("Tag with this name already exists!", HttpStatus.BAD_REQUEST);
        }
        
        Tag tag = new Tag();
        tag.setName(name);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Override
    public void deleteById(Long id) {
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            throw new LocalException("The tag with this id is either tied to some Gift Certificate " +
                    "or doesn't exist and cannot be deleted!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public TagDto getTheMostUsedTagOfTheWealthiestUser() {
        Long theWealthiestUsersId = userRepository.getTheWealthiestUsersId();
        List<Order> ordersById = orderRepository.findAllByUser_Id(theWealthiestUsersId);
        Set<GiftCertificate> giftCertificates = ordersById.stream().map(Order::getGiftCertificate).collect(Collectors.toSet());
        List<Tag> tags = giftCertificates.stream().map(GiftCertificate::getTagList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Optional<Tag> theMostFrequentElement = getTheMostFrequentElement(tags);
        if (theMostFrequentElement.isEmpty()){
            throw new LocalException("no tags tied to the wealthiest user", HttpStatus.NOT_FOUND);
        } else {
            return tagMapper.toDto(theMostFrequentElement.get());
        }
    }

    public Optional<Tag> getTheMostFrequentElement(List<Tag> elements) {
        Map<Tag, Long> temp = elements.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));


        return temp.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
