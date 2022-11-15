package Rest_Api_Advanced.controller;

import Rest_Api_Advanced.dto.TagDto;
import Rest_Api_Advanced.hateoas.event.PageRetrievedEvent;
import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.mapper.GiftCertificateMapper;
import Rest_Api_Advanced.mapper.TagMapper;
import Rest_Api_Advanced.model.Tag;
import Rest_Api_Advanced.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * <p>
     * Returns the Page representation of Tags
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Tags
     */
    @GetMapping(params = {"page", "size"})
    public Page<TagDto> getAll(@RequestParam int page,
                               @RequestParam int size,
                               UriComponentsBuilder uriComponentsBuilder,
                               HttpServletResponse httpServletResponse) {
        Page<TagDto> retrievedTags = tagService.getAll(page, size).map(tagMapper::toDto);
        if (page > retrievedTags.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        applicationEventPublisher.publishEvent(
                new PageRetrievedEvent<>(
                        Tag.class,
                        uriComponentsBuilder,
                        httpServletResponse,
                        page,
                        retrievedTags.getTotalPages(),
                        size));

        return retrievedTags;
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
