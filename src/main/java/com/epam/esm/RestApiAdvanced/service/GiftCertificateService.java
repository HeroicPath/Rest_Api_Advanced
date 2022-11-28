package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import org.springframework.hateoas.PagedModel;

/**
 * A service for CRD operations with GiftCertificate
 */
public interface GiftCertificateService {

    /**
     * <p>
     * returns the Page, comprised of Gift Certificates
     * </p>
     *
     * @param page page number
     * @param size page size
     * @return Page representation of Gift Certificates
     */
    PagedModel<GiftCertificateDto> getAll(int page, int size);

    /**
     * returns the GiftCertificate by id
     * @param id id of the requested entity
     * @return the requested entity
     */
    GiftCertificateDto getById(Long id);

    /**
     * returns the GiftCertificate by name
     * @param name Name field of the requested entity
     * @return the requested entity
     */
    GiftCertificateDto getByName(String name);

    /**
     * returns the GiftCertificate by description
     * @param description Description field of the requested entity
     * @return the requested entity
     */
    GiftCertificateDto getByDescription(String description);

    /**
     * returns the Page, comprised of Gift Certificates by tag names
     * @param tagNames a name or names of the Tag that is connected to the requested entities
     * @param page page number
     * @param size page size
     * @return Page representation of the requested entities
     */
    PagedModel<GiftCertificateDto> getByTagName(String tagNames, int page, int size);

    /**
     * creates new GiftCertificate with the given parameters and optionally creates new Tag connected to it
     * @param giftCertificateDto new GiftCertificate data
     * @return Dto representation of the newly created Gift Certificate
     */
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    /**
     * updates the GiftCertificate by id with the given parameters
     * @param giftCertificateDto new GiftCertificate data and optionally creates new Tag connected to it
     * @param id id of the to-be-updated entity
     * @return Dto representation of the newly updated Gift Certificate
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id);

    /**
     * deletes the specified GiftCertificate by id
     * @param id id of the GiftCertificate that needs to be deleted
     */
    void deleteById(Long id);
}
