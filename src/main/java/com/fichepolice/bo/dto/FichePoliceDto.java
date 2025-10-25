package com.fichepolice.bo.dto;

import com.fichepolice.bo.entity.Accompagnant;
import com.fichepolice.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichePoliceDto {
    private Long id;

    private String nom;
    private String prenom;
    private String venantDe;
    private String partantPour;

    @Past
    @NotNull
    private LocalDate dateNaissance;

    @NotBlank
    @Size(max = 120)
    private String lieuNaissance;

    @NotNull
    private Sexe sexe;

    // relation -> code pays
    private String nationaliteCode;

    // Pièce d'identité
    @NotNull
    private TypePiece typePiece;

    @NotBlank
    @Size(max = 64)
    private String numeroPiece;

    @NotNull
    private LocalDate dateDelivrancePiece;

    @NotBlank
    @Size(max = 120)
    private String lieuDelivrancePiece;

    private LocalDate dateExpirationPiece;

    // Coordonnées
    private String telephone;
    private String email;
    private String adresse;

    @Size(max = 150)
    private String profession;

    // Séjour
    @NotNull
    private LocalDate dateSortiePays;

    @NotNull
    private LocalDate dateArriveePays;

    @NotNull
    private LocalDate dateArrivee;

    @NotNull
    private LocalDate dateDepart;

    private MotifSejour motifSejour = MotifSejour.TOURISME;

    @Size(max = 20)
    private String numeroChambre;

    // fichiers encodés en Base64
    private String documentScanne;
    private String signatureClient;

    // relation -> code hotel
    private String hotelCode;

    private List<AccompagnantDto> accompagnants;

    private StatutFiche statut = StatutFiche.EN_ATTENTE;
    private StatutTransmission statutTransmission = StatutTransmission.NON_TRANSMISE;
}