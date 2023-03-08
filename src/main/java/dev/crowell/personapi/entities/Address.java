package dev.crowell.personapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@RequiredArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;
    @OneToOne(mappedBy = "address")
    private PersonEntity personEntity;
}
