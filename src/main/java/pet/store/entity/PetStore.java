package pet.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "pet_store")  // Explicitly define table name, if required
public class PetStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Explicitly set ID generation strategy
    @Column(name = "pet_store_id")
    private Long id;

    @Column(name = "name")
    private String name;  // Changed from petStoreName to name to match DTO

    @Column(name = "location")
    private String location;  // Changed from petStoreAddress to location to match DTO

    @Column(name = "owner")
    private String owner;  // Added owner field to match DTO

    // ... If there are other fields like city, state, zip, phone etc., you can add them here...

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "pet_store_customer",
        joinColumns = @JoinColumn(name = "pet_store_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers = new HashSet<>();  // Initialized at declaration

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();  // Initialized at declaration

    // Manually adding explicitly named setters
    public void setPetStoreName(String name) {
        this.name = name;
    }

    public void setPetStoreAddress(String location) {
        this.location = location;
    }
}
