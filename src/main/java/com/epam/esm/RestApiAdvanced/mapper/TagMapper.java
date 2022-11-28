package com.epam.esm.RestApiAdvanced.mapper;

import com.epam.esm.RestApiAdvanced.dto.TagDto;
import com.epam.esm.RestApiAdvanced.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "giftCertificateList", ignore = true)
    TagDto toDto(Tag tag);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "giftCertificates", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    @Mapping(target = "last_update_date", ignore = true)
    List<TagDto> mapToDto(List<Tag> list);
}
