package pet.store.controller.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pet.store.service.PetStoreService;

import java.util.List;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;

    @PostMapping
    public ResponseEntity<List<PetStoreData>> createPetStores(@RequestBody List<PetStoreData> petStoreDataList) {
        log.info("Received request to create new pet stores");
        List<PetStoreData> savedDataList = petStoreService.savePetStores(petStoreDataList);
        return new ResponseEntity<>(savedDataList, HttpStatus.CREATED);
    }

    @PutMapping("/{petStoreId}")
    public ResponseEntity<PetStoreData> updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        log.info("Updating Pet Store with ID: {}", petStoreId);
        petStoreData.setId(petStoreId);
        PetStoreData updatedData = petStoreService.savePetStore(petStoreData);
        return new ResponseEntity<>(updatedData, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PetStoreData>> getAllPetStores() {
        log.info("Fetching all pet stores.");
        List<PetStoreData> allPetStores = petStoreService.getAllPetStores();
        return new ResponseEntity<>(allPetStores, HttpStatus.OK);
    }

    @GetMapping("/{petStoreId}")
    public ResponseEntity<PetStoreData> getPetStoreById(@PathVariable Long petStoreId) {
        log.info("Fetching Pet Store with ID: {}", petStoreId);
        PetStoreData petStoreData = petStoreService.getPetStoreById(petStoreId);
        if (petStoreData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(petStoreData, HttpStatus.OK);
    }
}
