package com.raxrot.todo;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Todo REST API with Security",
                description = "Spring Boot RESTful API with authentication and authorization (Admin & User roles). Provides CRUD operations.",
                version = "1.0",
                contact = @Contact(
                        name = "Vlad",
                        email = "dasistperfektos@gmail.com",
                        url = "https://github.com/RaxRot"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "GitHub Repository",
                url = "https://github.com/RaxRot"
        )
)

public class TodoManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoManagementApplication.class, args);
    }
}
