package com.fichepolice.bo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AccompagnantRequest(
        @NotBlank String nom,
        @NotBlank String prenom,
        @Past @NotNull LocalDate dateNaissance,
        String numeroPiece
) {}
