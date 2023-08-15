package pet.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "pet_store")  // Explicitly define table name, if required
public class PetStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Explicitly set ID generation strategy
    @Column(name = "pet_store_id")
    private Long id;

    @Column(name = "pet_store_name")
    private String petStoreName;

    @Column(name = "pet_store_address")
    private String petStoreAddress;

    // ... Add other fields for city, state, zip, phone ...

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "pet_store_customer",
        joinColumns = @JoinColumn(name = "pet_store_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;
}
