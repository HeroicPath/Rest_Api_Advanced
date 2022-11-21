package com.epam.esm.Rest_Api_Advanced.repository;

import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    private GiftCertificate giftCertificate;
    private final String NAME = "name";
    private final String DESCRIPTION = "description";
    private final String SOME_STRING = "some string";

    @AfterEach
    void tearDown() {
        giftCertificateRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(NAME);
        giftCertificate.setDescription(DESCRIPTION);
        giftCertificateRepository.save(giftCertificate);
    }

    @Test
    void shouldFindFirstByNameLike() {
        Optional<GiftCertificate> firstByNameLike = giftCertificateRepository.findFirstByNameLike(NAME);
        Optional<GiftCertificate> testWrong = giftCertificateRepository.findFirstByNameLike(SOME_STRING);

        Assertions.assertThat(firstByNameLike.isPresent()).isTrue();
        Assertions.assertThat(testWrong.isPresent()).isFalse();
    }

    @Test
    void shouldFindFirstByDescriptionLike() {
        Optional<GiftCertificate> firstByDescriptionLike = giftCertificateRepository.findFirstByDescriptionLike(DESCRIPTION);
        Optional<GiftCertificate> testWrong = giftCertificateRepository.findFirstByDescriptionLike(SOME_STRING);

        Assertions.assertThat(firstByDescriptionLike.isPresent()).isTrue();
        Assertions.assertThat(testWrong.isPresent()).isFalse();
    }

    @Test
    void shouldExistsByName() {
        boolean existsByName = giftCertificateRepository.existsByName(NAME);
        boolean testWrong = giftCertificateRepository.existsByName(SOME_STRING);

        Assertions.assertThat(existsByName).isTrue();
        Assertions.assertThat(testWrong).isFalse();
    }
}