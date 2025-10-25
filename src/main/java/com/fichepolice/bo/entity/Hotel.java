package com.fichepolice.bo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name="hotel")
@Getter
@Setter
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=32)
    private String code;

    @Column(nullable=false, length=160)
    private String nom;

    private String adresse;
    private String ville;
    private String pays;
    private String emailContact;
    private String telephone;
    @ManyToOne
    private Regions regions;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private byte[] qrCode;

    @PreUpdate void touch(){ this.updatedAt = Instant.now(); }
}
