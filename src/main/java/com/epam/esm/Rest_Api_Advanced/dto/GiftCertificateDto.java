package com.epam.esm.Rest_Api_Advanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    @JsonProperty("name")
    @NotBlank(message = "You should provide a name for a new gift certificate")
    @Length(max = 30, message = "The name should not be longer than 30 characters")
    private String name;

    @JsonProperty("description")
    @Length(max = 150, message = "The description should not be longer than 150 characters")
    private String description;

    @JsonProperty("price")
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;

    @Min(value = 0, message = "Duration should not be less than 0")
    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("tags")
    private List<TagDto> tags;
}
