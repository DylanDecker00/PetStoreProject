package pet.store.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;
    
    
    @Transactional
    public void deletePetStoreById(Long id) {
        PetStore petStoreToDelete = petStoreDao.findById(id).orElse(null);
        if (petStoreToDelete != null) {
            
            // Clear associations with customers
            List<Customer> associatedCustomers = customerDao.findAllByPetStores(petStoreToDelete);
            for (Customer customer : associatedCustomers) {
                customer.getPetStores().remove(petStoreToDelete);
            }
            customerDao.saveAll(associatedCustomers);
            petStoreDao.flush();

            // Now delete the pet store:
            petStoreDao.deleteById(id);
        } else {
            throw new EntityNotFoundException("No PetStore found with ID: " + id);
        }
    }

    @Transactional
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStoreEntity = new PetStore();
        petStoreEntity.setId(petStoreData.getId());
        petStoreEntity.setName(petStoreData.getName());
        petStoreEntity.setLocation(petStoreData.getLocation());
        petStoreEntity.setOwner(petStoreData.getOwner());
        PetStore savedPetStore = petStoreDao.save(petStoreEntity);
        return new PetStoreData(savedPetStore);
    }

    public List<PetStoreData> getAllPetStores() {
        return petStoreDao.findAll().stream()
                          .map(PetStoreData::new)
                          .collect(Collectors.toList());
    }

    public PetStoreData getPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId)
                          .map(PetStoreData::new)
                          .orElse(null);
    }

    public boolean existsById(Long petStoreId) {
        return petStoreDao.existsById(petStoreId);
    }

    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long employeeId) {
        return employeeDao.findById(employeeId)
                          .orElseThrow(() -> new NoSuchElementException("Employee not found"));
    }

    @Transactional
    public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                          .orElseThrow(() -> new NoSuchElementException("PetStore not found"));
        Employee employee = findOrCreateEmployee(petStoreEmployee.getId());
        copyEmployeeFields(employee, petStoreEmployee);
        employee.setPetStore(petStore);
        Employee savedEmployee = employeeDao.save(employee);
        return new PetStoreEmployee(savedEmployee);
    }

    @Transactional(readOnly = true)
    public Customer findCustomerById(Long customerId) {
        return customerDao.findById(customerId)
                          .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Transactional
    public PetStoreCustomer saveCustomer(PetStoreCustomer petStoreCustomer) {
        Customer customer = findOrCreateCustomer(petStoreCustomer.getId());
        copyCustomerFields(customer, petStoreCustomer);
        Customer savedCustomer = customerDao.save(customer);
        return new PetStoreCustomer(savedCustomer);
    }

    private Employee findOrCreateEmployee(Long employeeId) {
        return (employeeId == null) ? new Employee() : findEmployeeById(employeeId);
    }

    private Customer findOrCreateCustomer(Long customerId) {
        return (customerId == null) ? new Customer() : findCustomerById(customerId);
    }

    private void copyEmployeeFields(Employee target, PetStoreEmployee source) {
        target.setName(source.getName());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhone(source.getPhone());
        target.setPosition(source.getPosition());
        target.setStartDate(source.getStartDate());
    }

    private void copyCustomerFields(Customer target, PetStoreCustomer source) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhone(source.getPhone());
        target.setAddress(source.getAddress());
    }
    public List<PetStoreData> saveAll(List<PetStoreData> petStoreDataList) {
        List<PetStore> petStores = petStoreDataList.stream()
                                   .map(data -> {
                                       PetStore store = new PetStore();
                                       store.setId(data.getId());
                                       store.setName(data.getName());
                                       store.setLocation(data.getLocation());
                                       store.setOwner(data.getOwner());
                                       return store;
                                   })
                                   .collect(Collectors.toList());
        List<PetStore> savedStores = petStoreDao.saveAll(petStores);
        return savedStores.stream().map(PetStoreData::new).collect(Collectors.toList());
    }

    public List<PetStoreCustomer> getCustomersByPetStoreId(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                          .orElseThrow(() -> new NoSuchElementException("PetStore not found"));
        List<Customer> customers = customerDao.findAllByPetStores(petStore);
        return customers.stream()
                        .map(PetStoreCustomer::new)
                        .collect(Collectors.toList());
    }

    public PetStoreCustomer getCustomerByPetStoreIdAndCustomerId(Long petStoreId, Long customerId) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                          .orElseThrow(() -> new NoSuchElementException("PetStore not found"));
        Customer customer = customerDao.findByPetStoresAndId(petStore, customerId)
                           .orElseThrow(() -> new NoSuchElementException("Customer not found for given PetStore and Customer IDs"));
        return new PetStoreCustomer(customer);
    }

    @Transactional
    public void addCustomerToPetStore(Long petStoreId, Customer customer) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                          .orElseThrow(() -> new NoSuchElementException("PetStore not found"));
        customer.addPetStore(petStore);
        customerDao.save(customer);
    }

    @Transactional
    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                          .orElseThrow(() -> new NoSuchElementException("PetStore not found"));
        Customer customer = findOrCreateCustomer(petStoreCustomer.getId());
        copyCustomerFields(customer, petStoreCustomer);
        customer.addPetStore(petStore);
        Customer savedCustomer = customerDao.save(customer);
        return new PetStoreCustomer(savedCustomer);
    }
	
}
