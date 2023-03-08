package dev.crowell.personapi.config;

import dev.crowell.personapi.handlers.PersonEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
    public RepositoryConfiguration() {
        super();
    }

    @Bean
    PersonEventHandler personEventHandler() {
        return new PersonEventHandler();
    }
}
