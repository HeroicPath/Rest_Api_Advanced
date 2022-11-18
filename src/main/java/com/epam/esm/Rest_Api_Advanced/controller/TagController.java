package com.epam.esm.Rest_Api_Advanced.controller;

import com.epam.esm.Rest_Api_Advanced.dto.TagDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.hateoas.assembler.TagAssembler;
import com.epam.esm.Rest_Api_Advanced.mapper.GiftCertificateMapper;
import com.epam.esm.Rest_Api_Advanced.mapper.TagMapper;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import com.epam.esm.Rest_Api_Advanced.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 * Controller for the Tag entity
 * </p>
 */
@RestController
@RequestMapping(value = "/tags", consumes = "application/json")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagAssembler tagAssembler;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;

    /**
     * <p>
     * Returns the PagedModel representation of Tags
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Tags
     */
    @GetMapping(params = {"page", "size"})
    public PagedModel<TagDto> getAll(@RequestParam int page,
                                     @RequestParam int size,
                                     UriComponentsBuilder uriComponentsBuilder,
                                     HttpServletResponse httpServletResponse) {
        Page<Tag> retrievedTags = tagService.getAll(page, size);
        if (page > retrievedTags.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(retrievedTags, tagAssembler);
    }

    /**
     * <p>
     * Returns the tag by its id
     * </p>
     *
     * @param id The id of the Tag that needs to be received
     * @return The representation of the requested Tag
     */
    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        Tag tag = tagService.getById(id);
        TagDto tagDto = tagMapper.toDto(tag);
        tagDto.setGiftCertificateList(giftCertificateMapper.mapToDto(tag.getGiftCertificates()));
        return tagDto;
    }

    /**
     * <p>
     * Creates the tag using given input
     * </p>
     *
     * @param tag The tag data that needs to be stored
     */
    @PostMapping
    public TagDto create(@RequestBody @Valid TagDto tag) {
        return tagMapper.toDto(tagService.create(tag));
    }

    /**
     * <p>
     * Deletes the tag by its id
     * </p>
     *
     * @param id The id of the Tag that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok("Tag by id " + id + " was deleted!");
    }

    /**
     * <p>
     * returns the most used tag of the User with the highest cost of orders
     * </p>
     * @return Dto representation of the most used tag of the User with the highest cost of orders
     */
    @GetMapping("/wealthiest")
    public TagDto getTheMostUsedTagOfTheWealthiestUser() {
        return tagMapper.toDto(tagService.getTheMostUsedTagOfTheWealthiestUser());
    }
}
