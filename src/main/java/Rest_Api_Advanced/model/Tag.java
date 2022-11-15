package Rest_Api_Advanced.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity(name = "tags") @AllArgsConstructor @NoArgsConstructor
public class Tag implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tagList", fetch = FetchType.LAZY)
    List<GiftCertificate> giftCertificates;
}
