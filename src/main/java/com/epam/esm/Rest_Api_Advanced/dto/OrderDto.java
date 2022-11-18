package com.epam.esm.Rest_Api_Advanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends RepresentationModel<OrderDto> {

    @JsonProperty("Cost")
    Double cost;
    @JsonProperty("Creation time")
    LocalDateTime creationTimeStamp;
}
