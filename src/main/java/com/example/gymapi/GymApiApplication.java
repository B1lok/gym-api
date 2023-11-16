package com.example.gymapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Gym Api",
                description = """
                        An API of CRM for fitness clubs.
                        Application implements subscription management and training schedules.
                        """
        )
)
public class GymApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApiApplication.class, args);
    }

}
