package com.fichepolice.enums;

public enum StatutFiche {
    EN_ATTENTE,    // fiche soumise par le client, en attente de validation
    VALIDEE,        // validée par le réceptionniste
    REFUSE         // rejetée par le réceptionniste (optionnel)
}
