package com.epam.esm.Rest_Api_Advanced.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity(name = "tags") @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Tag implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tagList", fetch = FetchType.LAZY)
    List<GiftCertificate> giftCertificates;

    @CreatedDate @Column(updatable = false)
    LocalDateTime create_date;

    @LastModifiedDate
    LocalDateTime last_update_date;
}
