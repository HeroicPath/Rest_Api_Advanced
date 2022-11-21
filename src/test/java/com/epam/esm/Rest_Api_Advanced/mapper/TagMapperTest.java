package com.epam.esm.Rest_Api_Advanced.mapper;

import com.epam.esm.Rest_Api_Advanced.RestApiAdvancedApplication;
import com.epam.esm.Rest_Api_Advanced.dto.TagDto;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagMapperTest {

    private TagMapper tagMapper;
    private Tag tag;
    private final String NAME = "name";

    @BeforeEach
    void setUp() {
        tagMapper = new TagMapperImpl();

        tag = new Tag();
        tag.setName(NAME);
    }

    @Test
    void shouldConverttoDto() {
        TagDto tagDto = tagMapper.toDto(tag);

        Assertions.assertThat(tagDto.getName()).isEqualTo(tag.getName());
    }

    @Test
    void shouldMapToDto() {
        List<Tag> list = Arrays.asList(tag);
        List<TagDto> tagDtos = tagMapper.mapToDto(list);

        Assertions.assertThat(tagDtos.get(0).getName()).isEqualTo(tag.getName());
    }
}