package com.epam.esm.Rest_Api_Advanced.controller;

import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.mapper.GiftCertificateMapper;
import com.epam.esm.Rest_Api_Advanced.mapper.TagMapper;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.service.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * Controller for the Gift Certificate entity
 * </p>
 */
@RestController
@RequestMapping(value = "/giftcertificates", consumes = "application/json")
@AllArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

    /**
     * <p>
     * Returns the Page representation of Gift Certificates
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Gift Certificates
     */
    @GetMapping(params = {"page", "size"})
    public PagedModel<GiftCertificateDto> getAll(@RequestParam int page,
                                                 @RequestParam int size) {
        Page<GiftCertificate> giftCertificateDtos = giftCertificateService.getAll(page, size);
        if (page > giftCertificateDtos.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(giftCertificateDtos, giftCertificateAssembler);
    }

    /**
     * <p>
     * Returns the Gift Certificate by its id
     * </p>
     *
     * @param id The id of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public GiftCertificateDto getById(@PathVariable Long id) {
        return convertToDto(giftCertificateService.getById(id));
    }

    /**
     * <p>
     * Returns the Gift Certificate by its id
     * </p>
     *
     * @param name The name of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/name/{name}", produces = "application/json")
    public PagedModel<GiftCertificateDto> getByName(@PathVariable String name) {
        Page<GiftCertificate> byName = giftCertificateService.getByName(name);
        if (byName.getTotalPages() == 0) {
            throw new LocalException("no gift certificate found with this name", HttpStatus.BAD_REQUEST);
        }
        return pagedResourcesAssembler.toModel(byName, giftCertificateAssembler);
    }

    /**
     * <p>
     * Returns the Gift Certificate by its description
     * </p>
     *
     * @param description The description of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/description/{description}", produces = "application/json")
    public GiftCertificateDto getByDescription(@PathVariable String description) {
        return convertToDto(giftCertificateService.getByDescription(description));
    }

    /**
     * <p>
     * Returns the Gift Certificate by the name of the tag that is connected to it
     * </p>
     *
     * @param tagName The name of the tag that is connected to the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/tagname/{tagName}",
                produces = "application/json",
                params = {"page", "size"})
    public PagedModel<GiftCertificateDto> getByTagName(@PathVariable String tagName,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        Page<GiftCertificate> giftCertificateDtos = giftCertificateService.getByTagName(tagName, page, size);
        if (page > giftCertificateDtos.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(giftCertificateDtos, giftCertificateAssembler);
    }

    /**
     * <p>
     * Creates the Gift Certificate using given input
     * </p>
     *
     * @param giftCertificateDto The Gift Certificate data that needs to be stored
     */
    @PostMapping()
    public GiftCertificateDto create(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return convertToDto(giftCertificateService.create(giftCertificateDto));
    }

    /**
     * <p>
     * Updates the Gift Certificate using given input and the id of the existing Gift Certificate
     * </p>
     *
     * @param id                 The id of the Gift Certificate that needs to be updated
     * @param giftCertificateDto The representation of data that replaces specified gift certificate
     */
    @PutMapping("/{id}")
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return convertToDto(giftCertificateService.update(giftCertificateDto, id));
    }

    /**
     * <p>
     * Deletes the Gift Certificate by its id
     * </p>
     *
     * @param id The id of the Gift Certificate that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok("Gift Certificate by id " + id + " was deleted!");
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
