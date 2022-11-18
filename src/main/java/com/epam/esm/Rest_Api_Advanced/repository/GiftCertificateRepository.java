package com.epam.esm.Rest_Api_Advanced.repository;

import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    Optional<GiftCertificate> findFirstByNameLike(String name);

    Optional<GiftCertificate> findFirstByDescriptionLike(String description);

    boolean existsByName(String name);
}
