package com.fichepolice.bo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FichePoliceSearchCriteria {
    private String prenom;
    private String nom;
    private String numeroPiece;
    private LocalDate dateArriveeFrom;
    private LocalDate dateArriveeTo;
    private LocalDate dateDepartFrom;
    private LocalDate dateDepartTo;
}