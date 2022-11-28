package com.epam.esm.RestApiAdvanced.controller;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
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
    public PagedModel<TagDto> getAll(@RequestParam @Valid int page,
                                     @RequestParam @Valid int size,
                                     UriComponentsBuilder uriComponentsBuilder,
                                     HttpServletResponse httpServletResponse) {
        return tagService.getAll(page, size);
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
    public TagDto getById(@PathVariable @Valid Long id) {
        return tagService.getById(id);
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
        return tagService.create(tag);
    }

    /**
     * <p>
     * Deletes the tag by its id
     * </p>
     *
     * @param id The id of the Tag that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
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
        return tagService.getTheMostUsedTagOfTheWealthiestUser();
    }
}
