package com.fichepolice.bo.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccompagnantDto {
    private Long id;
    private String nom;
    private String prenom;

    @Size(max = 64)
    private String numeroPiece;
}