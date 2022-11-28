package com.epam.esm.RestApiAdvanced.service.impl;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.RestApiAdvanced.mapper.GiftCertificateMapper;
import com.epam.esm.RestApiAdvanced.mapper.TagMapper;
import com.epam.esm.RestApiAdvanced.repository.GiftCertificateRepository;
import com.epam.esm.RestApiAdvanced.repository.TagRepository;
import com.epam.esm.RestApiAdvanced.service.GiftCertificateService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

    @Override
    public PagedModel<GiftCertificateDto> getAll(int page, int size) {
        Page<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
        if (page > giftCertificates.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(giftCertificates, giftCertificateAssembler);
    }

    @Override
    public GiftCertificateDto getById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this id", HttpStatus.NOT_FOUND));
        return convertToDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto getByName(String name) {
        GiftCertificate giftCertificate = giftCertificateRepository.findFirstByNameLike(name)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this name", HttpStatus.NOT_FOUND));
        return convertToDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto getByDescription(String description) {
        GiftCertificate giftCertificate = giftCertificateRepository.findFirstByDescriptionLike(description)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this description", HttpStatus.NOT_FOUND));
        return convertToDto(giftCertificate);
    }

    @Override
    public PagedModel<GiftCertificateDto> getByTagName(String tagNames, int page, int size) {
        List<Tag> collectedList = getTags(tagNames);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<GiftCertificate> collected =
                giftCertificateRepository.findAll(
                        (Specification<GiftCertificate>) (root, query, cb) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            collectedList.forEach(tag -> predicates.add(cb.isMember(tag, root.get("tagList"))));
                            return cb.and(predicates.toArray(new Predicate[0]));
                        },
                        pageable);

        if (collected.isEmpty()) {
            throw new LocalException("no gift certificates tied to this tag", HttpStatus.NOT_FOUND);
        }

        if (page > collected.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(collected, giftCertificateAssembler);
    }

    @SneakyThrows
    @Override
    public GiftCertificateDto create(@Valid GiftCertificateDto giftCertificateDto) {
        checkNameAvailability(giftCertificateDto.getName());

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate = populateTheGiftCertificate(giftCertificate, giftCertificateDto);

        return convertToDto(giftCertificateRepository.save(giftCertificate));
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this id", HttpStatus.NOT_FOUND));

        if (!giftCertificate.getName().equals(giftCertificateDto.getName())) {
            checkNameAvailability(giftCertificateDto.getName());
        }

        giftCertificate = updateTheGIftCertificate(giftCertificate, giftCertificateDto);

        return convertToDto(giftCertificateRepository.save(giftCertificate));
    }

    @Override
    public void deleteById(Long id) {
        giftCertificateRepository.deleteById(id);
    }

    public GiftCertificate populateTheGiftCertificate(GiftCertificate giftCertificate,
                                                      GiftCertificateDto giftCertificateDto) {
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        List<Tag> tagList = populateTagList(new ArrayList<>(), giftCertificateDto.getTags());
        giftCertificate.setTagList(tagList);

        return giftCertificate;
    }

    public GiftCertificate updateTheGIftCertificate(GiftCertificate giftCertificate,
                                                    GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) giftCertificate.setName(giftCertificateDto.getName());
        if (giftCertificateDto.getDescription() != null)
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        if (giftCertificateDto.getDuration() != null) giftCertificate.setDuration(giftCertificateDto.getDuration());
        if (giftCertificateDto.getPrice() != null) giftCertificate.setPrice(giftCertificateDto.getPrice());
        if (giftCertificateDto.getTags() != null) {
            List<Tag> tagList = populateTagList(giftCertificate.getTagList(), giftCertificateDto.getTags());
            giftCertificate.setTagList(tagList);
        }

        return giftCertificate;
    }

    public void checkNameAvailability(String gcName) {
        if (giftCertificateRepository.existsByName(gcName)) {
            throw new LocalException("Gift Certificate with this name already exists!", HttpStatus.BAD_REQUEST);
        }
    }

    public List<Tag> populateTagList(List<Tag> tagList, List<TagDto> tagDtos) {
        if (tagDtos != null) {
            tagDtos.forEach(tagDto -> {
                Optional<Tag> firstByName = tagRepository.findFirstByName(tagDto.getName());
                if (firstByName.isPresent()) {
                    tagList.add(firstByName.get());
                } else {
                    Tag tag = new Tag();
                    tag.setName(tagDto.getName());
                    tagList.add(tag);
                }
            });
        }

        return tagList;
    }

    private List<Tag> getTags(String tagName) {
        String[] splitNames = tagName.split("&");
        return Arrays.stream(splitNames)
                .map(name -> {
                    Optional<Tag> firstByName = tagRepository.findFirstByName(name);
                    if (firstByName.isEmpty()) {
                        throw new LocalException("no tag with this name exists!", HttpStatus.NOT_FOUND);
                    }
                    return firstByName.get();
                })
                .collect(Collectors.toList());
    }

    /**
     * <p>
     * Converts Gift Certificate to DTO
     * </p>
     *
     * @param giftCertificate the object that needs to be converted
     * @return The DTO representation of provided Gift Certificate
     */
    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = giftCertificateMapper.toDto(giftCertificate);
        giftCertificateDto.setTags(tagMapper.mapToDto(giftCertificate.getTagList()));
        return giftCertificateDto;
    }
}
