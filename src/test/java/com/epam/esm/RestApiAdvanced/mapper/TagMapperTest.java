package com.epam.esm.RestApiAdvanced.mapper;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
    void shouldConvertToDto() {
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