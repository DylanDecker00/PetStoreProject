package pet.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "firstname") // Updated column name
    private String firstName;

    @Column(name = "lastname") // Updated column name
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "start_date")  // assuming the database table has a column named "start_date"
    private Date startDate;

    // Assuming you have a ManyToOne relationship with PetStore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_store_id")
    private PetStore petStore;

    // Lombok's @Data annotation will generate getters, setters, toString(), equals(), and hashCode() methods.
    // So, you don't need to explicitly provide them unless there are custom implementations you need.

    // If you need any custom methods or additional relationships, you can add them here.
}
