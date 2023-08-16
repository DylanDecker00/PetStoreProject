package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
    private Long id;
    private String name;
    private String address;
    // ... (other fields)

    public PetStoreCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        // ... (set other fields)
    }
}
