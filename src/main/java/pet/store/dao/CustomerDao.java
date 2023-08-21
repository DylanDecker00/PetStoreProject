package pet.store.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    List<Customer> findAllByPetStores(PetStore petStore);
    Optional<Customer> findByPetStoresAndId(PetStore petStore, Long id);
}

