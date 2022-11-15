package Rest_Api_Advanced.mapper;

import Rest_Api_Advanced.dto.OrderDto;
import Rest_Api_Advanced.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> mapToDto(List<Order> orderList);
}
