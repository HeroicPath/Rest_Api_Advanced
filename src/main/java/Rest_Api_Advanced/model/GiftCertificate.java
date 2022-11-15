package Rest_Api_Advanced.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@DynamicUpdate
@Entity(name = "gift_certificates") @Data @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GiftCertificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "You should provide a name for a new gift certificate")
    @Length(max = 30, message = "The name should not be longer than 30 characters")
    String name;

    @Length(max = 150, message = "The description should not be longer than 150 characters")
    String description;

    @Min(value = 0, message = "Price should not be less than 0")
    Double price;

    @Min(value = 0, message = "Duration should not be less than 0")
    Integer duration;

    @Column(updatable = false)
    @CreatedDate
    LocalDateTime create_date;

    @LastModifiedDate
    LocalDateTime last_update_date;

    @Cascade({
            org.hibernate.annotations.CascadeType.ALL
    })
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) @JoinTable(name = "gift_certificates_tags")
    List<Tag> tagList;
}
