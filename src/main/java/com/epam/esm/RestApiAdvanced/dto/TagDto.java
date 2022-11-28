package com.epam.esm.RestApiAdvanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto extends RepresentationModel<TagDto> {

    @JsonProperty("name")
    @NotBlank(message = "Provide a name for Tag!")
    private String name;

    @JsonProperty("giftCertificates")
    private List<GiftCertificateDto> giftCertificateList;
}
