package com.fichepolice.bo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pays")
@Getter
@Setter
@NoArgsConstructor
public class Pays {
    @Id
    @Column(name = "code", nullable = false, updatable = false, unique = true)
    private String code;
    private String nom;
    @Column(nullable = false)
    private Boolean delete = false;
}