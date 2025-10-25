package com.fichepolice.bo.entity;

import com.fichepolice.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="fichePolice")
@Getter
@Setter
public class FichePolice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String venantDe;
    private String partantPour;
    @Past @NotNull
    private LocalDate dateNaissance;
    @NotBlank @Size(max = 120)
    private String lieuNaissance;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @ManyToOne
    private Pays nationalite;
    // -- Pièce d'identité
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypePiece typePiece;
    @NotBlank @Size(max = 64)
    private String numeroPiece;
    @NotNull
    private LocalDate dateDelivrancePiece;
    @NotBlank @Size(max = 120)
    private String lieuDelivrancePiece;
    private LocalDate dateExpirationPiece;
    // -- Coordonnées
    private String telephone;
    private String email;
    private String adresse;
    @Size(max = 150)
    private String profession;
    // -- Séjour
    @NotNull
    private LocalDate dateSortiePays;
    @NotNull
    private LocalDate dateArriveePays;
    @NotNull
    private LocalDate dateArrivee = LocalDate.now();
    @NotNull
    private LocalDate dateDepart;

    @Enumerated(EnumType.STRING)
    private MotifSejour motifSejour = MotifSejour.TOURISME;
    @Size(max = 20)
    private String numeroChambre;
    @Lob
    private byte[] documentScanne; // CNI / passeport
    @Lob
    private byte[] signatureClient;
    @ManyToOne
    private Hotel hotel;

    @OneToMany(mappedBy = "fichePolice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accompagnant> accompagnants;

    @Enumerated(EnumType.STRING)
    private StatutFiche statut = StatutFiche.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    private StatutTransmission statutTransmission = StatutTransmission.NON_TRANSMISE;

    public void addAccompagnant(Accompagnant a) {
        if (a == null) return;
        accompagnants.add(a);
        a.setFichePolice(this);
    }

    public void removeAccompagnant(Accompagnant a) {
        if (a == null) return;
        accompagnants.remove(a);
        a.setFichePolice(null);
    }
    private boolean deleted = false;
}
