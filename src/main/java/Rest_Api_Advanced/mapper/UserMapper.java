package Rest_Api_Advanced.mapper;

import Rest_Api_Advanced.dto.OrderDto;
import Rest_Api_Advanced.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    OrderDto toDto(User user);
}
