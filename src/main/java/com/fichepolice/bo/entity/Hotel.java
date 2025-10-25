package com.fichepolice.bo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name="hotel")
@Getter
@Setter
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=32)
    private String codeHotel;
    @Column(nullable=false, length=160)
    private String nom;
    private String telephoneHotel;
    private String emailHotel;
    private String adresseHotel;
    private String nomGerant;
    private String prenomGerant;
    private String telephoneGerant;
    private String emailGerant;
    private LocalDate dateCreation;
    private Integer nombreChambres;
    private Integer nombreEtoiles;
    @Column(name = "deleted")
    private Boolean deleted = false;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String qrcode;
}
