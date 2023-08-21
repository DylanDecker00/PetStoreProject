package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Any custom query methods can be added here
}
