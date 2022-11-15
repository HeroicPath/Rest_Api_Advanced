package Rest_Api_Advanced.service.impl;

import Rest_Api_Advanced.dto.GiftCertificateDto;
import Rest_Api_Advanced.dto.TagDto;
import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.model.GiftCertificate;
import Rest_Api_Advanced.model.Tag;
import Rest_Api_Advanced.repository.GiftCertificateRepository;
import Rest_Api_Advanced.repository.TagRepository;
import Rest_Api_Advanced.service.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    @Override
    public Page<GiftCertificate> getAll(int page, int size) {
        return giftCertificateRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public GiftCertificate getById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this id", HttpStatus.NOT_FOUND));
    }

    @Override
    public GiftCertificate getByName(String name) {
        return giftCertificateRepository.findFirstByNameLike(name)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this name", HttpStatus.NOT_FOUND));
    }

    @Override
    public GiftCertificate getByDescription(String description) {
        return giftCertificateRepository.findFirstByDescriptionLike(description)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this description", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<GiftCertificate> getByTagName(String tagNames, int page, int size) {
        List<Tag> collectedList = getTags(tagNames);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<GiftCertificate> giftCertificates =
                giftCertificateRepository.queryDistinctByTagListIn(
                        collectedList,
                        pageable);

        List<GiftCertificate> collect = giftCertificates
                .filter(giftCertificate -> giftCertificate
                        .getTagList()
                        .containsAll(collectedList))
                .get().collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new LocalException("no gift certificates tied to this tag", HttpStatus.NOT_FOUND);
        }

        return new PageImpl<>(collect, pageable, collect.size());
    }


    @Override
    public GiftCertificate create(@Valid GiftCertificateDto giftCertificateDto) {
        checkNameAvailability(giftCertificateDto.getName());

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate = populateTheGiftCertificate(giftCertificate, giftCertificateDto);
        giftCertificate.setCreate_date(LocalDateTime.now());
        giftCertificate.setLast_update_date(LocalDateTime.now());

        giftCertificateRepository.save(giftCertificate);

        return getByName(giftCertificateDto.getName());
    }

    @Override
    public GiftCertificate update(GiftCertificateDto giftCertificateDto, Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new LocalException("no gift certificate found by this id", HttpStatus.NOT_FOUND));

        if (!giftCertificate.getName().equals(giftCertificateDto.getName())) {
            checkNameAvailability(giftCertificateDto.getName());
        }

        giftCertificate = updateTheGIftCertificate(giftCertificate, giftCertificateDto);
        giftCertificate.setLast_update_date(LocalDateTime.now());

        return giftCertificateRepository.save(giftCertificate);
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
        if (giftCertificateDto.getTags() != null){
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
        tagDtos.forEach(tagDto -> {
            Optional<Tag> firstByName = tagRepository.findFirstByName(tagDto.getName());
            if (firstByName.isPresent()){
                tagList.add(firstByName.get());
            } else {
                Tag tag = new Tag();
                tag.setName(tagDto.getName());
                tagList.add(tag);
            }
        });

        return tagList;
    }

    private List<Tag> getTags(String tagName) {
        String[] splitNames = tagName.split("&");
        return Arrays.stream(splitNames)
                .map(name -> {
                    Optional<Tag> firstByName = tagRepository.findFirstByName(name);
                    if (!firstByName.isPresent()){
                        throw new LocalException("no tag with this name exists!", HttpStatus.NOT_FOUND);
                    }
                    return firstByName.get();
                })
                .collect(Collectors.toList());
    }
}
