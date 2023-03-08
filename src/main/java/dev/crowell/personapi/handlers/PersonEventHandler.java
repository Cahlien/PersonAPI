package dev.crowell.personapi.handlers;

import dev.crowell.personapi.entities.PersonEntity;
import dev.crowell.personapi.entities.Praenomen;
import dev.crowell.personapi.repos.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.Objects;

@RepositoryEventHandler
@RequiredArgsConstructor
@Slf4j
public class PersonEventHandler {
    @Autowired
    private PersonRepository personRepository;

    @HandleBeforeCreate
    public void handleBeforeCreate(PersonEntity person) {
        log.warn("Received request to create this person: {}", person);

        var names = personRepository.findNames(person.getName().getGivenNames(), person.getName().getSurname());

        if(!names.getFirst().isEmpty()) {
            names.getFirst().forEach(
                    name -> {
                        if(name.isEmpty()) return;

                        name.ifPresent(praenomen -> person.getName().getGivenNames().stream()
                                .filter(givenName -> Objects.equals(givenName.getValue(), praenomen.getValue()))
                                .findFirst()
                                .ifPresent(givenName -> {
                                    log.info(String.format("Given name %s already exists; assigning praenomen.", name.get().getValue()));
                                    givenName.setId(praenomen.getId());
                                    givenName.setValue(praenomen.getValue());
                                }));
                    });
            } else {
                log.info("Given name does not exist; creating praenomen.");
        }

        if(names.getSecond().isPresent()) {
            log.info(String.format("Surname %s already exists; assigning cognomen.", names.getSecond().get().getValue()));
            person.getName().setSurname(names.getSecond().get());
        } else {
            log.info("Surname does not exist; creating cognomen.");
        }
    }

    @HandleAfterCreate
    public void handleAfterCreate(PersonEntity person) {
        log.warn("Add person to queue to let other services know the following instant person has been created: {}", person);
    }
}
