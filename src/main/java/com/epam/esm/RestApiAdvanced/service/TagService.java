package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import org.springframework.hateoas.PagedModel;

/**
 * A service for CRD operations with Tag
 */
public interface TagService {

    /**
     * <p>
     * returns the Page, comprised of Tags
     * </p>
     *
     * @param page page number
     * @param size page size
     * @return Page representation of Tags
     */
    PagedModel<TagDto> getAll(int page, int size);

    /**
     * returns the Tag by id
     * @param id id of the requested entity
     * @return the requested entity
     */
    TagDto getById(Long id);

    /**
     * creates new Tag with the given parameters
     * @param tagDto new Tag data
     * @return Dto representation of the newly created Tag
     */
    TagDto create(TagDto tagDto);

    /**
     * deletes the specified Tag by id
     * @param id id of the Tag that needs to be deleted
     */
    void deleteById(Long id);

    /**
     * <p>
     * returns the most used tag of the User with the highest cost of orders
     * </p>
     * @return Dto representation of the most used tag of the User with the highest cost of orders
     */
    TagDto getTheMostUsedTagOfTheWealthiestUser();
}
