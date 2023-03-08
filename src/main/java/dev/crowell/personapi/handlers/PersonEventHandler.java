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
        log.warn("Received request to create this instant person: {}", person);

        var names = personRepository.findNames(person.getName().getGivenNames(), person.getName().getSurname());

        if(!names.getFirst().isEmpty()) {
            log.info("Given names already exist, so we will not create new ones.");
            names.getFirst().forEach(
                    name -> {
                        name.ifPresent(praenomen -> person.getName().getGivenNames().stream()
                                .filter(givenName -> Objects.equals(givenName.getValue(), praenomen.getValue()))
                                .findFirst()
                                .ifPresent(givenName -> {
                                    givenName.setId(praenomen.getId());
                                    givenName.setValue(praenomen.getValue());
                                }));
                    });
            } else {
                log.info("Given names do not exist, so we will create new ones.");
        }

        if(names.getSecond().isPresent()) {
            log.info("Surname already exists, so we will not create a new one.");
            person.getName().setSurname(names.getSecond().get());
        } else {
            log.info("Surname does not exist, so we will create a new one.");
        }

//        for (Praenomen givenName : person.getName().getGivenNames()) {
//            var name = personRepository.findGivenName(givenName.getValue());
//
//            if(name.isPresent()) {
//                log.info(String.format("Given name %s already exists, so we will not create a new one.", givenName.getValue()));
//                givenName.setId(name.get().getId());
//                givenName.setValue(name.get().getValue());
//            } else {
//                log.info(String.format("Given name %s does not exist, so we will create a new one.", givenName.getValue()));
//            }
//        }
//
//        var surname = personRepository.findCognomen(person.getName().getSurname().getValue());
//        if(surname.isPresent()) {
//            log.info(String.format("Surname %s already exists, so we will not create a new one.", surname.get().getValue()));
//            person.getName().getSurname().setId(surname.get().getId());
//            person.getName().getSurname().setValue(surname.get().getValue());
//            log.info(String.format("Surname %s does not exist, so we will create a new one.", person.getName().getSurname().getValue()));
//        }

    }

    @HandleAfterCreate
    public void handleAfterCreate(PersonEntity person) {
        log.warn("Add person to queue to let other services know the following instant person has been created: {}", person);
    }
}
