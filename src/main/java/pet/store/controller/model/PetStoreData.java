package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PetStoreData {

    private Long id;
    private String name;
    private String location;
    private String owner;
    private Set<PetStoreCustomer> customers;
    private Set<PetStoreEmployee> employees;

    public PetStoreData(PetStore petStore) {
        this.id = petStore.getId();
        this.name = petStore.getName();
        this.location = petStore.getLocation();
        this.owner = petStore.getOwner();
        // Convert entity sets to DTO sets
        this.customers = petStore.getCustomers().stream()
                                 .map(PetStoreCustomer::new)
                                 .collect(Collectors.toSet());
        this.employees = petStore.getEmployees().stream()
                                 .map(PetStoreEmployee::new)
                                 .collect(Collectors.toSet());
    }

    // Manually adding requested methods
    public String getPetStoreName() {
        return this.name;
    }

    public String getPetStoreAddress() {
        return this.location;
    }
}
