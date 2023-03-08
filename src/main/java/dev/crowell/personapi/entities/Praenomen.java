package dev.crowell.personapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "praenomens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "value")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Praenomen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    public Praenomen(String value) {
        this.value = value;
    }
}
