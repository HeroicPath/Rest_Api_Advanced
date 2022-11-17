package com.epam.esm.Rest_Api_Advanced.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "orders") @Data
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;
    Double cost;

    @ManyToOne
    GiftCertificate giftCertificate;

    @Column(updatable = false)
    @CreatedDate
    LocalDateTime create_date;

    @LastModifiedDate
    LocalDateTime last_update_date;
}
