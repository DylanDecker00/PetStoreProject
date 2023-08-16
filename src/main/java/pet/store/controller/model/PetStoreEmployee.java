package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
    private Long id;
    private String name;
    private String position;
    // ... (other fields)

    public PetStoreEmployee(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.position = employee.getPosition();
        // ... (set other fields)
    }
}
