package com.fichepolice.bo.repository;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.dto.FichePoliceSearchCriteria;

import java.util.List;

public interface FichePoliceRepositoryCustom {
    List<FichePoliceDto> searchFichePolice(FichePoliceSearchCriteria criteria);
}