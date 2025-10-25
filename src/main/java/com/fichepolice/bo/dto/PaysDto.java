package com.fichepolice.bo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaysDto {
    private String code;
    private String nom;
    private Boolean delete = false;
}
