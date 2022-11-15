package Rest_Api_Advanced.repository;

import Rest_Api_Advanced.model.GiftCertificate;
import Rest_Api_Advanced.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    Optional<GiftCertificate> findFirstByNameLike(String name);

    Optional<GiftCertificate> findFirstByDescriptionLike(String description);

//    @Query("SELECT g FROM gift_certificates g where g.name = ")
    Page<GiftCertificate> queryDistinctByTagListIn(@Param("tags") List<Tag> tags, Pageable pageable);

    boolean existsByName(String name);
}
