package dev.crowell.personapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Embeddable
@Data
@RequiredArgsConstructor
public class PersonalName {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "people_to_praenomens",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "praenomen_id"))
    private List<Praenomen> givenNames;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "surname_id")
    private Cognomen surname;
}
