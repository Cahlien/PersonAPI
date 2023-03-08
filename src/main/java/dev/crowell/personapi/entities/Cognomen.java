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
@Table(name = "cognomens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "value")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cognomen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    public Cognomen(String name) {
        this.value = name;
    }
}
