package com.example.onlineplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
@OpenAPIDefinition(info = @Info(title = "Online-Platform-Application",
        version = "v.Beta",
        description = "Разработка по методу API FIRST командой \"Dev-noobs\"",
        contact = @Contact(name = "Git repository", url = "https://github.com/MaysVerrecas/OnlinePlatform"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")),
        tags = @Tag(name = "Auth"))

@SpringBootApplication
public class OnlinePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePlatformApplication.class, args);
    }

}
