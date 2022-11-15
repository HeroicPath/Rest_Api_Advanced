package Rest_Api_Advanced.controller;

import Rest_Api_Advanced.dto.GiftCertificateDto;
import Rest_Api_Advanced.hateoas.event.PageRetrievedEvent;
import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.mapper.GiftCertificateMapper;
import Rest_Api_Advanced.mapper.TagMapper;
import Rest_Api_Advanced.model.GiftCertificate;
import Rest_Api_Advanced.service.GiftCertificateService;
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
    public Page<GiftCertificateDto> getAll(@RequestParam int page,
                                           @RequestParam int size,
                                           UriComponentsBuilder uriComponentsBuilder,
                                           HttpServletResponse httpServletResponse) {
        Page<GiftCertificateDto> giftCertificateDtos = giftCertificateService.getAll(page, size).map(this::convertToDto);
        if (page > giftCertificateDtos.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        applicationEventPublisher.publishEvent(
                new PageRetrievedEvent<>(
                        GiftCertificate.class,
                        uriComponentsBuilder,
                        httpServletResponse,
                        page,
                        giftCertificateDtos.getTotalPages(),
                        size));

        return giftCertificateDtos;
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
    public GiftCertificateDto getByName(@PathVariable String name) {
        return convertToDto(giftCertificateService.getByName(name));
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
    public Page<GiftCertificateDto> getByTagName(@PathVariable String tagName,
                                                 @RequestParam int page,
                                                 @RequestParam int size,
                                                 UriComponentsBuilder uriComponentsBuilder,
                                                 HttpServletResponse httpServletResponse) {
        Page<GiftCertificateDto> giftCertificateDtos = giftCertificateService.getByTagName(tagName, page, size).map(this::convertToDto);
        if (page > giftCertificateDtos.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        applicationEventPublisher.publishEvent(
                new PageRetrievedEvent<>(
                        GiftCertificate.class,
                        uriComponentsBuilder,
                        httpServletResponse,
                        page,
                        giftCertificateDtos.getTotalPages(),
                        size));

        return giftCertificateDtos;
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