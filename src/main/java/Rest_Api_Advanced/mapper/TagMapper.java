package Rest_Api_Advanced.mapper;

import Rest_Api_Advanced.dto.TagDto;
import Rest_Api_Advanced.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);

    List<TagDto> mapToDto(List<Tag> list);

    List<Tag> mapFromDto(List<TagDto> list);
}
