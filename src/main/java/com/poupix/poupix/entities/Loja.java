package com.poupix.poupix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "lojas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String categoria;

    @Column(nullable = false)
    private Boolean favorito;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Loja loja)) return false;
        return Objects.equals(id, loja.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
