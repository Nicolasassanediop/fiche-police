package com.fichepolice.bo.dto;

import com.fichepolice.bo.entity.FichePolice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Accompagnant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numeroPiece;

    @ManyToOne
    private FichePolice fichePolice;
}
