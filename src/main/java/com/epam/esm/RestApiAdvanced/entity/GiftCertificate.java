package com.epam.esm.RestApiAdvanced.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@DynamicUpdate
@Entity(name = "gift_certificates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GiftCertificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "You should provide a name for a new gift certificate")
    private String name;
    private String description;
    private Double price;
    private Integer duration;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @Cascade({
            org.hibernate.annotations.CascadeType.ALL
    })
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) @JoinTable(name = "gift_certificates_tags")
    private List<Tag> tagList;
}
