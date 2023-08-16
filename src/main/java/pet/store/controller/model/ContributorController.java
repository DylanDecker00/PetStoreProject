package pet.store.controller.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.ContributorData;
import pet.store.service.ContributorService;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/contributors")
@Slf4j
public class ContributorController {

    @Autowired
    private ContributorService contributorService;

    @PostMapping
    public ResponseEntity<ContributorData> createContributor(@RequestBody ContributorData contributorData) {
        log.info("Received request to create a new contributor: {}", contributorData);
        ContributorData savedData = contributorService.saveContributor(contributorData);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    // Additional methods for GET, PUT, DELETE, etc., as needed.
}
