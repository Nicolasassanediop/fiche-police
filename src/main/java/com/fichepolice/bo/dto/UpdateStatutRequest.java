package com.fichepolice.bo.dto;

import com.fichepolice.enums.StatutFiche;
import jakarta.validation.constraints.NotNull;

public class UpdateStatutRequest {
    @NotNull
    private StatutFiche statut;

    public UpdateStatutRequest() {}

    public UpdateStatutRequest(StatutFiche statut) {
        this.statut = statut;
    }

    public StatutFiche getStatut() {
        return statut;
    }

    public void setStatut(StatutFiche statut) {
        this.statut = statut;
    }
}