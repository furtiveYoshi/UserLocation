package eu.jitpay.test.task.userlocations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserLocationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserLocationsApplication.class, args);
    }

}
