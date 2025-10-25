package com.fichepolice.bo.service;

import com.fichepolice.bo.dto.FichePoliceDto;
import java.util.List;

public interface FichePoliceService {
    List<FichePoliceDto> findAll();
    FichePoliceDto findById(Long id);
    FichePoliceDto create(FichePoliceDto dto);
    FichePoliceDto update(Long id, FichePoliceDto dto);
    void delete(Long id);
}
