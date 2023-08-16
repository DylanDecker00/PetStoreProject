package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.store.controller.model.ContributorData;
import pet.store.dao.ContributorRepository;
import pet.store.entity.Contributor;

@Service
public class ContributorService {

    @Autowired
    private ContributorRepository contributorRepository;

    public ContributorData saveContributor(ContributorData contributorData) {
        Contributor contributor = new Contributor();
        contributor.setId(contributorData.getId());
        contributor.setName(contributorData.getName());
        contributor.setRole(contributorData.getRole());
        // ... other fields ...

        Contributor savedContributor = contributorRepository.save(contributor);

        return new ContributorData(savedContributor);
    }

    // Add more CRUD methods as needed.
}
