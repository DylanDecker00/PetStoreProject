package petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "pet.store.entity") // the package containing your entities

public class PetStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }
}
