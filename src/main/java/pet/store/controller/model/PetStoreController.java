package pet.store.controller.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.dao.CustomerRepository;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;
    
    @Autowired
    private PetStoreDao petStoreDao; 

    @Autowired
    private CustomerRepository customerRepository;

    @DeleteMapping("/{id}")
    public Map<String, String> deletePetStoreById(@PathVariable Long id) {
        Logger logger = LoggerFactory.getLogger(PetStoreController.class);
        logger.info("Request to delete pet store with ID: " + id);
        petStoreService.deletePetStoreById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Deletion successful for pet store with ID: " + id);
        return response;
    }
    

    @PostMapping
    public ResponseEntity<?> createPetStore(@RequestBody PetStoreData petStore) {
        log.info("Received request to create new pet stores");
        List<PetStoreData> petStoreDataList = new ArrayList<>();
        petStoreDataList.add(petStore);
        // Assuming a 'saveAll' method or similar exists in the service
        List<PetStoreData> savedDataList = petStoreService.saveAll(petStoreDataList);
        return new ResponseEntity<>(savedDataList, HttpStatus.CREATED);
    }

    @PutMapping("/{petStoreId}")
    @RequestMapping(value = "/api/petstore/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PetStoreData> updatePetStore(@PathVariable Long id, @RequestBody PetStoreData petStoreData) {
        // Find the existing pet store
        Optional<PetStore> existingPetStoreOpt = petStoreDao.findById(id);

        if (!existingPetStoreOpt.isPresent()) {
            // Handle error: Pet store not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PetStore existingPetStore = existingPetStoreOpt.get();

        // Update fields of the existing pet store
        existingPetStore.setName(petStoreData.getName());
        existingPetStore.setOwner(petStoreData.getOwner());
        existingPetStore.setLocation(petStoreData.getLocation());

        // Save the updated pet store
        petStoreDao.save(existingPetStore);

        return new ResponseEntity<>(new PetStoreData(existingPetStore), HttpStatus.OK);
    }
    
    @PostMapping("/{petStoreId}/addEmployee")
    public ResponseEntity<PetStoreEmployee> addEmployeeToPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
        try {
            PetStoreEmployee addedEmployee = petStoreService.saveEmployee(petStoreId, petStoreEmployee);
            return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // PetStore not found for the given ID
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle other exceptions gracefully
        }
    }

    @PutMapping("/{petStoreId}/employee")
    public ResponseEntity<PetStoreEmployee> updateEmployeeToPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
        log.info("Adding an employee to Pet Store with ID: {}", petStoreId);
        PetStoreEmployee savedEmployee = petStoreService.saveEmployee(petStoreId, petStoreEmployee);
        if (savedEmployee == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
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

    @PostMapping("/{petStoreId}/add_customer")
    public ResponseEntity<?> addExistingCustomerToPetStore(@PathVariable Long petStoreId, @RequestBody Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            petStoreService.addCustomerToPetStore(petStoreId, customerOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{petStoreId}/customer")
    public ResponseEntity<PetStoreCustomer> createCustomerForPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
        try {
            log.info("Creating a new customer for Pet Store with ID: {}", petStoreId);
            PetStoreCustomer savedCustomer = petStoreService.saveCustomer(petStoreId, petStoreCustomer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while creating customer for pet store: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{petStoreId}/customer")
    public ResponseEntity<List<PetStoreCustomer>> getCustomersByPetStoreId(@PathVariable Long petStoreId) {
        try {
            log.info("Fetching all customers for Pet Store with ID: {}", petStoreId);
            List<PetStoreCustomer> customers = petStoreService.getCustomersByPetStoreId(petStoreId);
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching customers for pet store: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{petStoreId}/customer/{customerId}")
    public ResponseEntity<PetStoreCustomer> getCustomerByPetStoreIdAndCustomerId(@PathVariable Long petStoreId, @PathVariable Long customerId) {
        try {
            log.info("Fetching customer with ID: {} for Pet Store with ID: {}", customerId, petStoreId);
            PetStoreCustomer customer = petStoreService.getCustomerByPetStoreIdAndCustomerId(petStoreId, customerId);
            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching customer for pet store: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
