package com.fichepolice.bo.dto;

import com.fichepolice.enums.MotifSejour;
import com.fichepolice.enums.Sexe;
import com.fichepolice.enums.TypePiece;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FichePoliceRequest(
        @NotBlank String nom,
        @NotBlank String prenom,
        @Past @NotNull LocalDate dateNaissance,
        @NotBlank @Size(max = 120) String lieuNaissance,
        @NotNull Sexe sexe,
        @NotNull Long nationaliteId,              // ref Pays
        @NotNull TypePiece typePiece,
        @NotBlank @Size(max = 64) String numeroPiece,
        @NotNull LocalDate dateDelivrancePiece,
        @NotBlank @Size(max = 120) String lieuDelivrancePiece,
        LocalDate dateExpirationPiece,
        String telephone,
        @Email String email,
        String adresse,
        Long prochaineDestinationId,              // renommé depuis paysResidenceId
        @Size(max = 150) String profession,
        @NotNull LocalDateTime dateSortiePays,
        @NotNull LocalDateTime dateArriveePays,
        @NotNull LocalDateTime dateArrivee,       // renommé depuis dateCheckin
        @NotNull LocalDateTime dateDepart,        // renommé depuis dateDepartPrevue
        MotifSejour motifSejour,
        @Size(max = 20) String numeroChambre,
        @NotBlank String hotelCode,               // pour retrouver Hotel
        List<AccompagnantRequest> accompagnants,
        @NotNull String documentBase64,           // scan CNI / passeport en Base64
        String signatureBase64,
        String venantDe,                          // ajouté si présent dans l'entité
        String partantPour                        // ajouté si présent dans l'entité
) {}
