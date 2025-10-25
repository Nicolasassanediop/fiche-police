package com.fichepolice.bo.dto;

import com.fichepolice.enums.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FichePoliceResponse(
        Long id,
        String nom,
        String prenom,
        LocalDate dateNaissance,
        String lieuNaissance,
        Sexe sexe,
        String nationalite,           // libellé pays
        TypePiece typePiece,
        String numeroPiece,
        LocalDate dateDelivrancePiece,
        String lieuDelivrancePiece,
        LocalDate dateExpirationPiece,
        String telephone,
        String email,
        String adresse,
        String paysResidence,         // libellé pays
        String profession,
        LocalDateTime dateArriveePays,
        LocalDateTime dateCheckin,
        LocalDateTime dateDepartPrevue,
        MotifSejour motifSejour,
        String numeroChambre,
        String hotelCode,
        StatutFiche statut,
        StatutTransmission statutTransmission,
        String validerPar,
        LocalDateTime validerdAt,
        List<String> accompagnants    // ex: "NOM Prenom (yyyy-mm-dd)"
) {}
