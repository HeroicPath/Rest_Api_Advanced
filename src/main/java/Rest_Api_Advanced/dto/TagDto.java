package Rest_Api_Advanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto {

    @JsonProperty("name")
    @NotBlank(message = "Provide a name for Tag!")
    private String name;

    @JsonProperty("giftCertificates")
    private List<GiftCertificateDto> giftCertificateList;
}
