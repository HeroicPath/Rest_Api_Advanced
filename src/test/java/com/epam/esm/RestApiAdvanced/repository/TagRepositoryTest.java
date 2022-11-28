package com.epam.esm.RestApiAdvanced.repository;

import com.epam.esm.RestApiAdvanced.entity.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;
    private Tag tag;
    private final String NAME = "name";
    private final String SOME_NAME = "some name";

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setName(NAME);
        tagRepository.save(tag);
    }

    @Test
    void shouldFindFirstByName() {
        Optional<Tag> new_name = tagRepository.findFirstByName(NAME);
        Optional<Tag> some_name = tagRepository.findFirstByName(SOME_NAME);

        Assertions.assertThat(new_name.isPresent()).isTrue();
        Assertions.assertThat(some_name.isPresent()).isFalse();
    }

    @Test
    void shouldExistsByName() {
        boolean new_name = tagRepository.existsByName(NAME);
        boolean some_name = tagRepository.existsByName(SOME_NAME);

        Assertions.assertThat(new_name).isTrue();
        Assertions.assertThat(some_name).isFalse();
    }
}