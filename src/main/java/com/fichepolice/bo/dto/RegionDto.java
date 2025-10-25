package com.fichepolice.bo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto {
    private Long id;
    private String nom;
    private Boolean delete = false;
    private String paysCode;
}