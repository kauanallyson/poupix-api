package com.poupix.poupix.loja;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loja")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 100)
    private String categoria;

    @Column(nullable = false)
    private Boolean favorito = false;
}
