package com.epam.esm.RestApiAdvanced.repository;

import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    Optional<GiftCertificate> findFirstByNameLike(String name);

    Optional<GiftCertificate> findFirstByDescriptionLike(String description);

    boolean existsByName(String name);
}
