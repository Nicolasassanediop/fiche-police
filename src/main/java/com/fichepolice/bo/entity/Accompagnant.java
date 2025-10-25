package com.fichepolice.bo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accompagnant")
@Getter
@Setter
public class Accompagnant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Size(max = 64)
    private String numeroPiece;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fiche_police_id")
    private FichePolice fichePolice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pays nationalite;
}