package com.project.sofeiandrei.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
  private String devUrl = "http://localhost:4000";

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Contact contact = new Contact();
    contact.setEmail("andreisofei2002@gmail.com");
    contact.setName("SofeiAndrei");
    contact.setUrl("https://www.sofeiandrei.com");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
            .title("Travel Costs Tracker API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to keep track of costs while traveling.").termsOfService("https://www.example.com/terms")
            .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
