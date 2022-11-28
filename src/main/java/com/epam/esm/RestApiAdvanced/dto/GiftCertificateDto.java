package com.epam.esm.RestApiAdvanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    @JsonProperty("name")
    @NotBlank(message = "You should provide a name for a new gift certificate")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @Min(value = 0, message = "Duration should not be less than 0")
    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("tags")
    private List<TagDto> tags;
}
