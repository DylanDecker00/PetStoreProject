package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.store.entity.Contributor;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
    // Additional custom queries (if required) can be added here
}
