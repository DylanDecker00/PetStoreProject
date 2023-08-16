package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Contributor;

@Data
@NoArgsConstructor
public class ContributorData {

    private Long id;
    private String name;
    private String role;

    // ... any other required fields ...

    public ContributorData(Contributor contributor) {
        this.id = contributor.getId();
        this.name = contributor.getName();
        this.role = contributor.getRole();
        // ... map other fields ...
    }
}
