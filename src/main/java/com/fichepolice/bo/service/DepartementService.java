package com.fichepolice.bo.service;

import com.fichepolice.bo.dto.DepartementDto;

import java.util.List;

public interface DepartementService {
    List<DepartementDto> findAll();
    DepartementDto findById(Long id);
    DepartementDto create(DepartementDto dto);
    DepartementDto update(Long id, DepartementDto dto);
    void delete(Long id);
}