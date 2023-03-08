package dev.crowell.personapi.dtos;

import dev.crowell.personapi.entities.Address;
import dev.crowell.personapi.entities.PersonEntity;
import dev.crowell.personapi.entities.PersonalName;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "person_dto", types = {PersonEntity.class})
public interface PersonDto {
    Address getAddress();
    PersonalName getName();
}
