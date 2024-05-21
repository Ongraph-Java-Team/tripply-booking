package com.tripply.booking.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class TripplyConfig {

    @Value("${app.booking.base-url}")
    private String url;

    @Value("${spring.application.version}")
    private String version;

    /**
     * Configures the objectMapper object.
     *
     * @return A ObjectMapper object bean.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Configures the webClient object.
     *
     * @return A WebClient object bean.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Bean
    public OpenAPI defineOpenApi(){
        Server server = new Server();
        server.setUrl(url);
        server.setDescription("Development");

        Contact contact = new Contact();
        contact.setName("Tripply");
        contact.setEmail("tripply@ongraph.com");

        Info information = new Info()
                .title("Tripply - Hotel Booking System")
                .version(version)
                .description("This API exposes endpoints for booking service")
                .contact(contact);
        return new OpenAPI().info(information).servers(List.of(server));
    }

}
