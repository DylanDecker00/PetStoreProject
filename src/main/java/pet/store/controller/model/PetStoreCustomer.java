package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    // ... (other fields, if any)

    public PetStoreCustomer(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();  // Assuming the Customer entity also has this field.
        // ... (set other fields, if any)
    }
}
