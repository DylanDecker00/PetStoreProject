package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    public PetStoreData savePetStore(PetStoreData petStoreData) {
        Long id = petStoreData.getId();

        if (id != null && !petStoreDao.existsById(id)) {
            throw new NoSuchElementException("No PetStore found with ID: " + id);
        }

        // Convert DTO to Entity
        PetStore petStoreEntity = new PetStore();
        petStoreEntity.setId(id);
        petStoreEntity.setName(petStoreData.getName());
        petStoreEntity.setLocation(petStoreData.getLocation());
        petStoreEntity.setOwner(petStoreData.getOwner());

        // Save Entity to the Database
        PetStore savedPetStore = petStoreDao.save(petStoreEntity);

        // Convert saved Entity back to DTO
        return new PetStoreData(savedPetStore);
    }

    public List<PetStoreData> savePetStores(List<PetStoreData> petStoreDataList) {
        return petStoreDataList.stream()
            .map(this::savePetStore)
            .collect(Collectors.toList());
    }

    public List<PetStoreData> getAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        return petStores.stream()
                        .map(PetStoreData::new)
                        .collect(Collectors.toList());
    }

    public PetStoreData getPetStoreById(Long petStoreId) {
        Optional<PetStore> foundPetStore = petStoreDao.findById(petStoreId);
        return foundPetStore.map(PetStoreData::new).orElse(null);
    }

    // New method to check if a PetStore with a given ID exists
    public boolean existsById(Long petStoreId) {
        return petStoreDao.existsById(petStoreId);
    }
}
