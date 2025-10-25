package com.fichepolice.bo.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateFichePoliceRequest(
        @NotBlank String validerPar
) {}

