package com.epam.esm.Rest_Api_Advanced.mapper;

import com.epam.esm.Rest_Api_Advanced.dto.OrderDto;
import com.epam.esm.Rest_Api_Advanced.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "creationTimeStamp", ignore = true)
    OrderDto toDto(User user);
}
