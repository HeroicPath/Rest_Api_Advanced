package com.epam.esm.RestApiAdvanced.controller;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.service.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * Controller for the Gift Certificate entity
 * </p>
 */
@RestController
@RequestMapping(value = "/gift-certificates", consumes = "application/json", produces = "application/json")
@AllArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * <p>
     * Returns the PagedModel representation of Gift Certificates
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Gift Certificates
     */
    @GetMapping(params = {"page", "size"})
    public PagedModel<GiftCertificateDto> getAll(@RequestParam @Valid int page,
                                                 @RequestParam @Valid int size) {
        return giftCertificateService.getAll(page, size);
    }

    /**
     * <p>
     * Returns the Gift Certificate by its id
     * </p>
     *
     * @param id The id of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/{id}")
    public GiftCertificateDto getById(@PathVariable @Valid Long id) {
        return giftCertificateService.getById(id);
    }

    /**
     * <p>
     * Returns the Gift Certificate by its id
     * </p>
     *
     * @param name The name of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/name/{name}")
    public GiftCertificateDto getByName(@PathVariable @Valid String name) {
        return giftCertificateService.getByName(name);
    }

    /**
     * <p>
     * Returns the Gift Certificate by its description
     * </p>
     *
     * @param description The description of the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/description/{description}")
    public GiftCertificateDto getByDescription(@PathVariable @Valid String description) {
        return giftCertificateService.getByDescription(description);
    }

    /**
     * <p>
     * Returns the Gift Certificates by the name of the tag/s that is/are connected to it
     * </p>
     *
     * @param tagName The name of the tag that is connected to the Gift Certificate that needs to be received
     * @return The representation of the requested Gift Certificate
     */
    @GetMapping(value = "/tagname/{tagName}",
                params = {"page", "size"})
    public PagedModel<GiftCertificateDto> getByTagName(@PathVariable @Valid String tagName,
                                                       @RequestParam @Valid int page,
                                                       @RequestParam @Valid int size) {
        return giftCertificateService.getByTagName(tagName, page, size);
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
        return giftCertificateService.create(giftCertificateDto);
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
    public GiftCertificateDto update(@PathVariable @Valid Long id, @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto, id);
    }

    /**
     * <p>
     * Deletes the Gift Certificate by its id
     * </p>
     *
     * @param id The id of the Gift Certificate that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok("Gift Certificate by id " + id + " was deleted!");
    }
}
