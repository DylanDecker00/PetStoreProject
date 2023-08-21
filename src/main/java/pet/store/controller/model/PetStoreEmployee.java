package pet.store.controller.model;

import java.util.Date;

import pet.store.entity.Employee;

public class PetStoreEmployee {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String name;
    private String position;
    private Date startDate;
    // ... any other fields ...

    // Constructors

    public PetStoreEmployee() {}

    public PetStoreEmployee(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.phone = employee.getPhone();
        this.name = employee.getName();
        this.position = employee.getPosition();
        this.startDate = employee.getStartDate();
        // ... assign other fields ...
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // ... additional getters and setters ...

    // Optionally, you may also want to override toString(), hashCode(), and equals() methods as per the requirements.
}
