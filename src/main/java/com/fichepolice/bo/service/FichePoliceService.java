package com.fichepolice.bo.service;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.dto.FichePoliceSearchCriteria;
import com.fichepolice.bo.entity.FichePolice;

import java.util.List;

public interface FichePoliceService {
    List<FichePoliceDto> findAll();
    FichePoliceDto findById(Long id);
    FichePoliceDto create(FichePoliceDto dto);
    FichePoliceDto update(Long id, FichePoliceDto dto);
    List<FichePoliceDto>searchFichePolice(FichePoliceSearchCriteria criteria);
    void delete(Long id);
}
