package dev.crowell.personapi.repos;

import dev.crowell.personapi.dtos.PersonDto;
import dev.crowell.personapi.entities.Cognomen;
import dev.crowell.personapi.entities.PersonEntity;
import dev.crowell.personapi.entities.Praenomen;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RepositoryRestResource(collectionResourceRel = "people", path = "people", excerptProjection = PersonDto.class)
@Tag(name = "Person Repository", description = "This is the repository for people.")
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    @PreAuthorize("#id == authentication.principal.id OR true")
    @NonNull
    Optional<PersonEntity> findById(@NonNull Long id);

    @PreAuthorize("#id == authentication.principal.id OR true")
    @Query("SELECT c FROM Cognomen c WHERE c.value = ?1")
    Optional<Cognomen> findCognomen(String familyName);

    @Query("SELECT e FROM Praenomen e WHERE e.value IN :givenNamesValues")
    List<Optional<Praenomen>> findPraenomensIn(@Param("givenNamesValues") List<String> givenNamesValues);

    default Pair<List<Optional<Praenomen>>, Optional<Cognomen>> findNames(List<Praenomen> givenNames, Cognomen surname) {
        List<String> givenNamesValues = givenNames.stream().map(Praenomen::getValue).collect(Collectors.toList());
        List<Optional<Praenomen>> praenomens = findPraenomensIn(givenNamesValues);
        Optional<Cognomen> cognomen = findCognomen(surname.getValue());
        return Pair.of(praenomens, cognomen);
    }

}
