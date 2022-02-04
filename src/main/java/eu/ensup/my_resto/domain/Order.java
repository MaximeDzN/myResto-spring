package eu.ensup.my_resto.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The type Order.
 */
@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@ToString
public class Order {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String status;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item")
    private Set<OrderItem> orderItems;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    /**
     * Pre persist.
     */
    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    /**
     * Pre update.
     */
    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

}
